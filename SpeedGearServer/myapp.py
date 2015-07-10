#!/usr/bin/env python
# coding:utf-8
import sys, os, re, time
import logging
import redis
import urlparse
import json
from bottle import route, debug, run, request, response, template, view, static_file, abort, redirect, HTTPError, error, get, post
import utils


logging.basicConfig(level=logging.INFO, format='%(levelname)s - - %(asctime)s %(message)s', datefmt='[%d/%b/%Y %H:%M:%S]')
REMOTE_RESULT_TIMEOUT = 600
db = utils.DataBase()

def authAccount(user, password):
    ''' Return True if username and password are valid. '''
    ''' to-do if needed'''
    return true

def protected(check, realm="private", text="Access denied"):
    def decorator(func):
        def wrapper(*args, **kwargs):
            user, password = request.auth or (None, None)
            if user is None or not check(user, password):
                return HTTPError(401, text)
            return func(*args, **kwargs)
        return wrapper
    return decorator

@error(404)
def error404(error):
    return 'Nothing here. Sorry!'

@route('/index.html')
@view('index')
#@protected(authAccount)
def index():
    logging.info("User IP is: %s", request.remote_addr )
    print "User IP is :", request.remote_addr
    return dict()

@get('/')
@get('/greeting')
@get('/greeting/')
@get('/greeting/<name>')
def greet(name='Stranger'):
    return template('Hello {{name}}, Welcome to access SpeedGear!', name=name)

@route('/user', method=['GET', 'POST'])
def addUser():
    qs = urlparse.parse_qs(request.query_string)
    errMsg = "Successfull"

    #   /user?action=add&name=alice
    if qs.has_key('action') and 'add' in qs['action'] and qs.has_key('name'):
        username = qs["name"][0].strip().strip('"').strip("'")
        hasExist, errMsg =  db.addUser(username)
        if hasExist:
            response.status = 409
        return dict(errorMsg = errMsg )

    #   /user?action=listall
    elif qs.has_key('action') and 'listall' in qs['action']:
        allusers = db.listAllUsers()
        return dict(errorMsg = errMsg, users = allusers)

    else:
        response.status = 501
        errMsg = "wrong url format, please use '/user?action=add&name=alice'"
        return dict(errorMsg = errMsg )

def main():
    logging.info('Init the Database here!')
    db.initDB(True)

    logging.info('Restart and init web server here!')
    run(host='0.0.0.0', port=81, reloader=True)

if __name__ == '__main__':
    main()
