#!/bin/bash

# PID 파일 이름
PID_FILE="algo_blog_app.pid"

# PID 파일이 존재하는지 확인
if [ -f $PID_FILE ]; then
    # PID를 가져와서 해당 프로세스를 종료
    PID=$(cat $PID_FILE)
    echo "Stopping application with PID $PID..."
    kill $PID
    
    # PID 파일 삭제
    rm $PID_FILE
    
    echo "Application stopped."
else
    echo "No running application found."
fi
