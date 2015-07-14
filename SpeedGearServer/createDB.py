#!/usr/bin/env python
# coding:utf-8
import sys, os, re, time
import logging
import utils

logging.basicConfig(
        filename = "speedgear.log",
        level=logging.INFO,
        format='%(levelname)s - - %(asctime)s %(message)s',
        datefmt='[%d/%b/%Y %H:%M:%S]' )

def main():
    db = utils.DataBase()
    logging.info('Create the database here!')
    db.initDB(True)
    db.close()

if __name__ == '__main__':
    main()
