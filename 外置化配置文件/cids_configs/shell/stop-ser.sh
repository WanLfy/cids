#!/bin/bash

#参数1:服务器路径
SER_HOME="$1"
#参数2:服务器端口号
SER_PORT="$2"

#服务器进程号
SER_PID=$(ps -ef | grep ${SER_HOME} | grep java | awk '{print $2}')
array_pid=(${SER_PID})
if [ ${#array_pid[*]} == 1 ];
then
    kill -9 ${SER_PID}
    echo "STOPPED"
else
    #监听端口号使用情况
    #netstat -p 需要在root权限下执行,否则不能查看运行在root之下的进程号
    PORT_INFO=$(netstat -antp|grep ${SER_PORT}|grep LISTEN|grep java|awk '{print $7}')
    PORT_PID=${PORT_INFO%/*}
    for pid in ${array_pid[@]}
    do
        if [ ${pid} == ${PORT_PID} ];
        then
            kill -9 ${pid}
            echo "STOPPED"
            break
        fi
    done
fi
