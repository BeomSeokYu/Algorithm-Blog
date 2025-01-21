#!/bin/bash

# 실행할 포트 번호
PORT=9000

# JAR 파일 이름
JAR_FILE="build/libs/blog-1.0.jar"

# 로그 파일 이름
LOG_FILE="output.log"

# nohup을 사용하여 JAR 파일 실행
echo "Starting application on port $PORT..."
nohup java -jar -Dserver.port=$PORT $JAR_FILE > $LOG_FILE 2>&1 &

# 실행된 프로세스 ID를 PID 파일에 저장
echo $! > algo_blog_app.pid

echo "Application started with PID $(cat algo_blog_app.pid). Logs are being written to $LOG_FILE."