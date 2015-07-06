#!/usr/bin/env python
# coding:utf-8

import sys, os, re, time
import logging
import redis
import speedGearUtils
import BaseHTTPServer
import urlparse
import StringIO
import json

logging.basicConfig(level=logging.INFO, format='%(levelname)s - - %(asctime)s %(message)s', datefmt='[%d/%b/%Y %H:%M:%S]')

class HttpServerRequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):

    def do_GET(self):
        logging.info("do_GET here.")

        parsed_path = urlparse.urlparse(self.path)
        logging.info("parsed_path is %s", parsed_path)
        query_string = urlparse.parse_qs(parsed_path.query)
        logging.info("query_string is %s", query_string)

        message = ""
        if not query_string.has_key("action"):
            message = "Please use the correct URI."
            self.send_response(404)
            self.end_headers()
            self.wfile.write(message)
            return

        action= query_string["action"][0].strip()
        if action == "getaccounts":
            logging.info("Receive action from %s, action = %s. ", self.client_address, action)
            message = self.handle_getaccounts()

        elif action == "addaccount":
            account_info = query_string["account"][0].strip()
            account_json = json.load(StringIO.StringIO(account_info))
            print account_json["username"], account_json["password"]
            message = self.handle_addaccount(account_json["username"], account_json["password"])

        self.send_response(200)
        self.end_headers()
        #self.wfile.write("error_code = " + error_code)
        self.wfile.write("\r\n".join(message))
        return

    def do_POST(self):
        logging.info("do_POST here.")

    def handle_getaccounts(self):
        logging.info("handle_getaccounts in.")
        redisDB = speedGearUtils.RedisDatabase(host='localhost', port=6397)
        return redisDB.redis_account_get()

    def handle_addaccount(self, username, password):
        logging.info("handle_addaccount in.")
        redisDB = speedGearUtils.RedisDatabase(host='localhost', port=6397)
        redisDB.redis_account_add(username, password)


class SpeedGearHttpServer:
    def __init__(self, *args, **kwargs):
        logging.info("SpeedGearHttpServer init here.")

    def start(self):
        httpd = BaseHTTPServer.HTTPServer(('',80), HttpServerRequestHandler)
        print 'Starting server, use <Ctrl-C> to stop'
        httpd.serve_forever()

def main():
    sgHttpServer = SpeedGearHttpServer()
    sgHttpServer.start()

if __name__ == '__main__':
    """ for test
    redisDB = speedGearUtils.RedisDatabase(host='localhost', port=6397)
    redisDB.redis_account_add("alice", "password1")
    redisDB.redis_account_add("bob", "password2")
    """

    main()
