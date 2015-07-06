#!/usr/bin/env python
# coding:utf-8

import sys, os, re, time
import logging
import redis

logging.basicConfig(level=logging.INFO, format='%(levelname)s - - %(asctime)s %(message)s', datefmt='[%d/%b/%Y %H:%M:%S]')


error_codes_to_messages = {
   0: 'successful',
   1: 'DB server is not available.',

   2: 'get accounts failed',
   3: 'no accounts exists',

   4: 'add accounts failed',
}

error_messages_to_codes = dict(
    (v,k) for k,v in error_codes_to_messages.iteritems()
)


"""
class DataBase(object):

    def __init__(self):
        self.conn = MySQLdb.connect(host='localhost',user='root', passwd='mac8.6', db='article')

    def exec_sql(self, sql):
        cursor = self.conn.cursor()
        result = cursor.execute(sql,param)
        cursor.close()
        return result

    def close(self):
        self.conn.close()
"""

class RedisDatabase(object):
    RDB_USER            = 'accounts'
    RDB_GPSLOCATION     = 'gps_location'

    def __init__(self, *args, **kwargs):
        self.rdb = redis.Redis(*args, **kwargs)

    def is_available(self):
        try:
            response = rs.client_list()
            return true
        except redis.ConnectionError:
            return false

    def redis_account_add(self, username, password):
        self.rdb.hset(self.RDB_USER, username, password)

    def redis_account_get(self):
        users = self.rdb.hkeys(self.RDB_USER)
        return users


#db = DataBase()
redisDB = RedisDatabase(host='localhost', port=6397)

if __name__ == '__main__':
    #main()
    redisDB.redis_account_add("alice", "pass1")
    redisDB.redis_account_add("bob", "pass2")

    print redisDB.redis_account_get()

