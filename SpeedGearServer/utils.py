#!/usr/bin/env python
# coding:utf-8

import sys, os, re, time
import logging
import redis
import sqlite3

logging.basicConfig(level=logging.INFO, format='%(levelname)s - - %(asctime)s %(message)s', datefmt='[%d/%b/%Y %H:%M:%S]')

class DataBase(object):
    def __init__(self):
        self.conn = sqlite3.connect(database='data.db')

    def initDB(self, removeExistedData = False):
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
        cursor = self.conn.cursor()
        try:
            cursor.execute(''' select username from users where username='%s'; ''' % username)
            if cursor.fetchone() is not None:
                logging.exception("user existed for %s." % username)
                return True, "user existed for %s." % username

            cursor.execute(''' INSERT INTO users(username) VALUES ('%s'); ''' % username)
            cursor.close()
        except Exception:
            logging.exception("add user failed for %s." % username)
            return False, ("add user failed for: %s, please try again" % username)
        return False, ("add user success for: %s" % username)

    def listAllUsers(self):
        cursor = self.conn.cursor()
        allUsers = []
        try:
            cursor.execute(''' select username from users ''')
            for eachitem in  cursor.fetchall():
                allUsers.append(eachitem[0])
            cursor.close()
            return allUsers
        except Exception:
            logging.exception("listall user failed.")
            return allUsers

    def close(self):
        self.conn.close()


if __name__ == '__main__':
    pass

