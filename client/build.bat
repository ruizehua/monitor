@echo off
chcp 65001 >nul

echo === Go客户端跨平台编译 ===

:: 创建输出目录
if not exist build (
    mkdir build
)

:: 编译Windows 64位版本
echo 正在编译 Windows 64位版本...
set GOOS=windows
set GOARCH=amd64
go build -o build/monitor-client-windows-amd64.exe

:: 编译Linux 64位版本
echo 正在编译 Linux 64位版本...
set GOOS=linux
set GOARCH=amd64
go build -o build/monitor-client-linux-amd64

:: 编译Linux ARM64版本
echo 正在编译 Linux ARM64版本...
set GOOS=linux
set GOARCH=arm64
go build -o build/monitor-client-linux-arm64

echo.
echo 编译完成！输出文件位于 build\ 目录：
dir build\