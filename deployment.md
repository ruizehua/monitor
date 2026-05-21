# 部署文档

## 概述

本文档描述了监控系统的部署流程和配置说明。

## 环境要求

### 服务端
| 依赖 | 版本 | 说明 |
|------|------|------|
| Java | 21+ | LTS版本，推荐使用JDK 21 |
| Maven | 3.9.11+ | 项目已集成Maven Wrapper |
| 操作系统 | Windows/Linux | 支持跨平台部署 |

### 客户端
| 依赖 | 版本 | 说明 |
|------|------|------|
| Go | 1.21+ | 需要Go语言编译环境 |

## 服务端部署

### 开发环境运行

**方式一：使用Maven Wrapper**
```bash
cd backend
./mvnw spring-boot:run
```

**方式二：使用本地Maven**
```bash
cd backend
mvn spring-boot:run
```

### 生产环境部署

#### 编译打包
```bash
cd backend
./mvnw clean package -DskipTests
```

#### 运行打包后的Jar
```bash
java -jar target/monitor-server-1.0.0-SNAPSHOT.jar
```

#### 后台运行(Linux)
```bash
nohup java -jar target/monitor-server-1.0.0-SNAPSHOT.jar > app.log 2>&1 &
```

### 配置说明

#### application.yml 配置
```yaml
server:
  port: 8080

spring:
  application:
    name: monitor-server
  datasource:
    url: jdbc:sqlite:./data/monitor.sqlite
    driver-class-name: org.sqlite.JDBC
  jpa:
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    hibernate:
      ddl-auto: update
    show-sql: false

logging:
  level:
    com.nari.monitor: INFO
    org.hibernate.SQL: WARN

monitor:
  default-report-interval: 30
  ip-whitelist:
    - 127.0.0.1
    - 192.168.1.0/24
```

#### 配置项说明

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server.port | 服务端口 | 8080 |
| spring.datasource.url | SQLite数据库路径 | jdbc:sqlite:./data/monitor.sqlite |
| monitor.default-report-interval | 默认上报间隔(秒) | 30 |
| monitor.ip-whitelist | IP白名单列表 | 空(允许所有) |

## 客户端部署

### 编译
```bash
cd client
go build -o monitor-client main.go
```

### 运行
```bash
# 基本运行
./monitor-client -name=my-client -server=http://localhost:8080

# 指定上报间隔
./monitor-client -name=my-client -server=http://localhost:8080 -interval=30
```

### 参数说明

| 参数 | 说明 | 必填 |
|------|------|------|
| -name | 客户端名称 | 是 |
| -server | 服务端地址 | 是 |
| -interval | 上报间隔(秒) | 否(默认30) |

## Docker部署

### Dockerfile示例
```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/monitor-server-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

### 构建镜像
```bash
cd backend
docker build -t monitor-server:latest .
```

### 运行容器
```bash
docker run -d -p 8080:8080 -v ./data:/app/data monitor-server:latest
```

## 验证

### 服务端健康检查
```bash
curl http://localhost:8080/api/client
```

### 预期响应
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [],
    "totalElements": 0,
    "totalPages": 0,
    "currentPage": 0,
    "pageSize": 10
  }
}
```

## 目录结构要求

```
backend/
├── data/                # SQLite数据库文件目录
├── logs/                # 日志目录
└── target/              # 编译输出目录
```

### 权限要求
- data目录需要可写权限
- logs目录需要可写权限

## 升级指南

### 备份数据
```bash
cp ./data/monitor.sqlite ./data/monitor.sqlite.backup
```

### 停止服务
```bash
# Linux
kill $(ps aux | grep monitor-server | grep -v grep | awk '{print $2}')

# Windows
# 在任务管理器中结束java进程
```

### 部署新版本
```bash
cd backend
./mvnw clean package -DskipTests
java -jar target/monitor-server-1.0.0-SNAPSHOT.jar
```
