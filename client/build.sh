#!/bin/bash

# Go客户端跨平台编译脚本

echo "=== Go客户端跨平台编译 ==="

# 创建输出目录
mkdir -p build

# 编译Windows 64位版本
echo "正在编译 Windows 64位版本..."
GOOS=windows GOARCH=amd64 go build -o build/monitor-client-windows-amd64.exe

# 编译Linux 64位版本
echo "正在编译 Linux 64位版本..."
GOOS=linux GOARCH=amd64 go build -o build/monitor-client-linux-amd64

# 编译Linux ARM64版本
echo "正在编译 Linux ARM64版本..."
GOOS=linux GOARCH=arm64 go build -o build/monitor-client-linux-arm64

echo ""
echo "编译完成！输出文件位于 build/ 目录："
ls -la build/