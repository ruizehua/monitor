# 部署文档

## 概述

本文档描述了监控系统的部署流程。

## 环境要求

### 服务端
- Java 17+
- Maven 3.9.11+

### 客户端
- Go 1.21+

## 服务端部署

### 编译
```bash
cd backend
mvn clean package -DskipTests
```

### 运行
```bash
mvn spring-boot:run
```

### 配置
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:sqlite:./data/example_db.sqlite

monitor:
  default-report-interval: 30
```

## 客户端部署

### 编译
```bash
cd client
go build -o monitor-client
```

### 运行
```bash
./monitor-client -name=my-client -ip=192.168.1.100
```

## Docker部署

```bash
docker-compose up -d
```

## 验证

```bash
curl http://localhost:8080/api/client
```
