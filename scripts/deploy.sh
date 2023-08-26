#!/bin/bash
BUILD_JAR=$(ls /home/ubuntu/app/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_LOG=/home/ubuntu/app/deploy.log
echo "> build 파일명: $JAR_NAME" >> $DEPLOY_LOG

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_LOG
CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "  > : $CURRENT_PID" >> $DEPLOY_LOG

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포"    >> $DEPLOY_LOG
nohup java -jar $BUILD_JAR >> /home/ubuntu/tomcat_exe.log 2>/home/ubuntu/app/deploy_err.log &