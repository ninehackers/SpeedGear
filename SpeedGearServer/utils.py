#!/usr/bin/env python
# coding:utf-8

import sys, os, re, time
import logging
import redis
import sqlite3
import json
import datetime

logging.basicConfig(
        filename = "speedgear.log",
        level=logging.INFO,
        format='%(levelname)s - - %(asctime)s %(message)s',
        datefmt='[%d/%b/%Y %H:%M:%S]' )

class DataBase(object):
    def __init__(self):
        self.conn = sqlite3.connect(database='data.db')

    def initDB(self, removeExistedData = False):
        logging.info("DataBase: initDB in.")
        cursor = self.conn.cursor()
        if removeExistedData:   cursor.execute('drop table if exists users')
        if removeExistedData:   cursor.execute('drop table if exists locations')

        try:
            cursor.execute('''CREATE TABLE users
                            (id integer primary key autoincrement, username text); ''')

            cursor.execute('''CREATE TABLE locations(id integer primary key autoincrement,
                            user_id integer not null, longitude text not null,
                            dimension text not null, create_time datetime not null
                            ) ;''')
            cursor.close()
            self.conn.commit()
        except Exception:
            logging.exception("create db table failed.")
            return False
        return True

    def addUser(self, username):
        logging.info("DataBase: addUser in.")
        cursor = self.conn.cursor()
        try:
            cursor.execute(''' select username from users where username='%s'; ''' % username)
            if cursor.fetchone() is not None:
                logging.exception("user existed for %s." % username)
                return True, "user existed for %s." % username

            cursor.execute(''' INSERT INTO users(username) VALUES ('%s'); ''' % username)
            cursor.close()
            self.conn.commit()
        except Exception:
            logging.exception("add user failed for %s." % username)
            return False, ("add user failed for: %s, please try again" % username)
        return False, ("add user success for: %s" % username)

    def listAllUsers(self):
        logging.info("DataBase: listAllUsers in.")
        cursor = self.conn.cursor()
        allUsers = []
        try:
            cursor.execute(''' select username from users ''')
            for eachitem in cursor.fetchall():
                allUsers.append(eachitem[0])
            cursor.close()
            self.conn.commit()
            return allUsers
        except Exception:
            logging.exception("listall user failed.")
            return allUsers

    def getLatestLocation(self, username):
        logging.info("DataBase: getLatestLocation in.")
        cursor = self.conn.cursor()
        try:
            cursor.execute(''' select id from users where username='%s'; ''' % username)
            userids = cursor.fetchone()
            if userids is None:
                return "user '%s' does not exist" % username, None
            else:
                userid = userids[0]
                cursor.execute(''' select longitude, dimension from locations where user_id=%d order by datetime(create_time) desc limit 1; ''' % userid)
                results = cursor.fetchone()
                if results is None:
                    return "no location existes for '%s'." % username, None
                else:
                    longitude, dimension = results[0], results[1]
                    location = {'longitude': longitude, 'dimension': dimension}
                    #print json.dumps(location)
                    return "Successfull", json.dumps(location)
            cursor.close()
            self.conn.commit()
        except Exception:
            logging.exception("getlatestlocation failed for %s." % username)
            return  ("getlatestlocation failed for: %s, please try again" % username), None
        return  ("getlatestlocation success for: %s" % username), None

    def addLatestLocation(self, username, longitude, dimension):
        logging.info("DataBase: addLatestLocation in.")
        cursor = self.conn.cursor()
        try:
            cursor.execute("select id from users where username='%s'; " % username)
            userids = cursor.fetchone()
            if userids is None:
                return 409, "user '%s' does not exist" % username
            else:
                userid = userids[0]
                cursor.execute("insert into locations(user_id,longitude,dimension,create_time) values (?, ?, ?, ?)", (userid,longitude,dimension,datetime.datetime.now()))
            cursor.close()
            self.conn.commit()
        except Exception:
            logging.exception("addLatestLocation failed for %s." % username)
            return 500, ("addLatestLocation failed for: %s, please try again" % username)
        return 200, ("addLatestLocation success for: %s" % username)

    def close(self):
        self.conn.close()


if __name__ == '__main__':
    pass

