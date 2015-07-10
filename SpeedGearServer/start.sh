#!/bin/sh

run_dir=`dirname $0`
cd $run_dir

#init val
host=
port=81
script_name=app

logfolder = log
applog = 

############### start the redis server first ###############

echo "Starting redis server on port 6397..."
./redis-server/redis-server --port 6397 >redis.log 2>&1 &

do_start() {
    #init dir or file
    mkdir -p uws
    mkdir -p log

    touch $touch_file
    export PYTHON_EGG_CACHE=/tmp/.python-eggs/

    #exec
    uwsgi -s $host:$port -w $script_name:$app_name -p $worker_num -d $log_file --pidfile $pid_file --touch-reload $touch_file -L &
}

do_stop() {
    kill -INT `cat $pid_file`
}

do_reload() {
    touch $touch_file
}

case "$1" in
    start)
        do_start
        ;;
    stop)
        do_stop
        ;;
    reload)
        do_reload
        ;;
    *)
        echo "Usage: $0 start|stop|reload" >&2
        exit 3
        ;;
esac