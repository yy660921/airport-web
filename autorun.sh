#!/bin/bash
### BEGIN INIT INFO
# Provides: transparentproxy
# Required-Start:    $local_fs $syslog $remote_fs dbus
# Required-Stop:     $local_fs $syslog $remote_fs
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start transparentproxy
### END INIT INFO
export JAVA_HOME=/home/taizy/jdk1.7
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

cd /home/taizy/code/deploy
PRO_NAME=airport-web
CMD=$1
CONF=back.conf
PORT=8090
function start(){
    echo "dsfsadfsa" >> /home/taizy/tt.tt
    COUNT="`ps aux |grep -w ${PRO_NAME}|grep -v 'grep'|wc -l`"
    if [  $COUNT -gt 0 ];then
        echo "${PRO_NAME} is already runing, stop it first";
    else
        echo "starting ${PRO_NAME}";
        java -Dspring.profiles.active=production -DProperties.File=${CONF} -jar jetty-runner.jar --port ${PORT} ${PRO_NAME}.war  >> backend.log 2>>backenderror.log &
        sleep 1s;
        COUNT="`ps aux |grep -w ${PRO_NAME}|grep -v 'grep'|wc -l`"
        if [  $COUNT -gt 0 ];then
            echo "${PRO_NAME} done!";
        else
            echo "failed!";
        fi
    fi
}
function stop(){
    COUNT="`ps aux |grep -w ${PRO_NAME}|grep -v 'grep'|wc -l`"
    if [  $COUNT -eq 1 ];then
        echo "stopping ${PRO_NAME}";
        ps -ef|grep ${PRO_NAME}|grep -v grep|awk '{print $2}'|xargs -n1 kill -9
        t_COUNT="`ps aux |grep -w ${PRO_NAME}|grep -v 'grep'|wc -l`"
            if [  $t_COUNT -eq 0 ];then
                echo "done!";
            else
                echo "failed.";
            fi
    elif [ $COUNT -eq 0 ];then
        echo "${PRO_NAME} is not started."
    else
        echo "more than one program running, Please contact the ADMIN."
    fi
}
function stopall(){
    ps -ef|grep ${PRO_NAME}|grep -v grep|awk '{print $2}'|xargs -n1 kill -9
}
function status(){
    COUNT="`ps aux |grep -w ${PRO_NAME}|grep -v 'grep'|wc -l`"
    if [  $COUNT -gt 0 ];then
        echo "${PRO_NAME} is running.";
    else 
        echo "nothing!"
    fi
}
case $CMD in
    "start")
        start;
        echo "start!";;
    "restart")
        stop;
        sleep 1s;
        start;;
    "stop")
        stop;;
    "stopall")
        stopall;;
    "status")
        status;;
* ) echo "only accept start,restart,stop,stopall,status" >&2;;
esac
