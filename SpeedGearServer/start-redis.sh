#!/bin/sh

echo "Starting redis server on port 6397..."
./redis-3.0.2/src/redis-server --port 6397 >redis.log 2>&1 &
