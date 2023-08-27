#!/bin/bash
BUILD_JAR=$(ls /home/ubuntu/app-blog/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_LOG=/home/ubuntu/app-blog/log/deploy.log
DEPLOY_ERR_LOG=/home/ubuntu/app-blog/log/deploy_err.log
EXEC_LOG=/home/ubuntu/app-blog/log/exec.log

echo "> 로그 폴더 생성" > $DEPLOY_LOG
cd /home/ubuntu/app-blog
mkdir log

echo "> build 파일명: $JAR_NAME" >> $DEPLOY_LOG

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_LOG
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포 (exec.log에 스프링 실행 로그가 없다면 deploy_err.log을 확인하세요)" >> $DEPLOY_LOG
nohup java -jar $BUILD_JAR >> $EXEC_LOG 2> $DEPLOY_ERR_LOG &