#!/bin/sh

echo "Starting redis server on port 6397..."
./redis-server/redis-server --port 6397 >redis.log 2>&1 &
