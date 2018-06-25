#!/bin/bash
#export JAVA_HOME=/home/ali/jdk-1.8.0
#参数1:服务器路径
SER_HOME="$1"
cd ${SER_HOME}/bin
nohup ./standalone.sh > nohup.out 2>&1 &
