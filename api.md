# 接口规范

## 概述

本文档定义了监控系统的API接口规范。

## 基础路径

所有API的基础路径为 `/api`

## 响应格式

### 成功响应
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 失败响应
```json
{
  "code": 400,
  "message": "error message",
  "data": null
}
```

### 分页响应
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [],
    "totalElements": 100,
    "totalPages": 10,
    "currentPage": 1,
    "pageSize": 10
  }
}
```

## 接口列表

### 客户端管理

| 方法 | 路径 | 功能 |
|------|------|------|
| POST | /api/client/register | 注册客户端 |
| GET | /api/client/{id} | 查询单个客户端 |
| GET | /api/client | 查询客户端列表(分页) |
| PUT | /api/client/{id} | 更新客户端信息 |
| DELETE | /api/client/{id} | 删除客户端 |

#### POST /api/client/register
**请求体：**
```json
{
  "clientName": "string (必填, 客户端名称)",
  "hostIp": "string (必填, 主机IP)",
  "hostName": "string (选填, 主机名称)",
  "osType": "string (选填, WINDOWS/LINUX)",
  "reportInterval": "integer (选填, 上报间隔秒数, 默认30)"
}
```

**响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "clientName": "client-001",
    "hostIp": "192.168.1.100",
    "reportInterval": 30,
    "createdAt": "2024-01-01 12:00:00"
  }
}
```

### 监控数据

| 方法 | 路径 | 功能 |
|------|------|------|
| POST | /api/monitor/report | 上报监控数据 |
| GET | /api/monitor/{clientId} | 查询客户端监控历史数据 |
| GET | /api/monitor/{clientId}/latest | 查询客户端最新数据 |
| GET | /api/monitor/dashboard | 获取仪表盘数据 |

#### POST /api/monitor/report
**请求体：**
```json
{
  "clientId": "long (必填, 客户端ID)",
  "cpuUsage": "decimal (选填, CPU使用率)",
  "physicalMemoryUsed": "long (选填, 物理内存使用量)",
  "physicalMemoryTotal": "long (选填, 物理内存总量)",
  "virtualMemoryUsed": "long (选填, 虚拟内存使用量)",
  "virtualMemoryTotal": "long (选填, 虚拟内存总量)",
  "diskUsage": "decimal (选填, 磁盘使用率)",
  "diskTotal": "long (选填, 磁盘总量)",
  "diskUsed": "long (选填, 磁盘使用量)",
  "processCount": "integer (选填, 进程数量)",
  "processInfo": "string (选填, 进程信息JSON)"
}
```

## 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 409 | 资源冲突(如客户端名称重复) |
| 500 | 服务器内部错误 |
