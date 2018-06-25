#!/bin/bash

#参数1:服务器路径
SER_HOME="$1"
#参数2:服务器端口号
SER_PORT="$2"

#服务器进程号
SER_PID=$(ps -ef|grep ${SER_HOME}|grep java|awk '{print $2}')
array_pid=(${SER_PID})

if [ ${array_pid} ];
then
    #获取项目部署信息
    WAR_PATH=$(find ${SER_HOME}/standalone/deployments -name '*.war.*')
    #echo "path:"${WAR_PATH}
    array_path=(${WAR_PATH})
    #echo "len:"${#array_path[*]}
    if [ ${#array_path[*]} == 2 ];
    then
        echo "STARTUP"
    elif [ ${#array_path[*]} == 1 ];
    then
        WAR_STATUS=${array_path[0]##*war.}
        if [ ${WAR_STATUS} == "failed" ];
        then
            echo "FAILED"
        elif [ ${WAR_STATUS} == "deployed" ];
        then
            echo "STARTED"
            ##监听端口号使用情况
            #PORT_INFO=$(netstat -antp|grep ${SER_PORT}|grep LISTEN|grep java|awk '{print $7}')
            #PORT_PID=${PORT_INFO%/*}
            ##echo "port_pid:"${PORT_PID}
            ##echo "ser_pid:"${SER_PID}
            #for pid in ${array_pid[*]}
            #do
            #    if [ ${pid} == ${PORT_PID} ];
            #    then
            #        echo "STARTED"
            #        break
            #    fi
            #done
        fi
    fi
else
    #服务器为启动失败
    echo "ERROR"
fi
