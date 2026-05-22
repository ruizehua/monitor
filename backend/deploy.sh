#!/bin/bash

echo "=============================================="
echo "          监控系统 Docker 自动部署脚本"
echo "=============================================="
echo ""

# 停止并删除现有容器
echo "[1/3] 停止并删除现有容器..."
docker-compose down
if [ $? -ne 0 ]; then
    echo "警告：容器可能未运行或不存在，继续执行..."
fi

# 重新构建镜像并启动容器
echo ""
echo "[2/3] 重新构建Docker镜像..."
docker-compose build

if [ $? -ne 0 ]; then
    echo "错误：Docker镜像构建失败！"
    exit 1
fi

echo ""
echo "[3/3] 启动Docker容器..."
docker-compose up -d

if [ $? -ne 0 ]; then
    echo "错误：容器启动失败！"
    exit 1
fi

echo ""
echo "=============================================="
echo "              部署完成！"
echo "=============================================="
echo ""
echo "服务地址: http://localhost:8080"
echo "前端页面: http://localhost:8080/html/index.html"
echo "API接口:  http://localhost:8080/api"
echo ""
echo "查看容器状态: docker-compose ps"
echo "查看容器日志: docker-compose logs -f"
echo ""