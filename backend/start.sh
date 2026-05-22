#!/bin/bash

echo "=============================================="
echo "          Starting Monitor System"
echo "=============================================="

echo "[1/3] Starting Java Server..."
java -jar /app/app.jar &
JAVA_PID=$!

echo "Java Server started with PID: $JAVA_PID"

echo "[2/3] Waiting for Java Server to be ready..."
sleep 10

echo "[3/3] Starting Monitor Client..."
cd /app
ls -la /app/monitor-client
if [ -f /app/monitor-client ]; then
    echo "monitor-client exists"
    /app/monitor-client -name monitor-client -ip 127.0.0.1 -server http://localhost:8080 -interval 10 &
    CLIENT_PID=$!
    echo "Monitor Client started with PID: $CLIENT_PID"
else
    echo "ERROR: monitor-client not found!"
fi

echo ""
echo "=============================================="
echo "          Monitor System Started"
echo "=============================================="
echo "Java Server PID: $JAVA_PID"
echo "Monitor Client PID: $CLIENT_PID"
echo "Service Address: http://localhost:8080"
echo ""

wait $JAVA_PID