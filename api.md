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

## 接口列表

### 客户端管理

| 方法 | 路径 | 功能 |
|------|------|------|
| POST | /client/register | 注册客户端 |
| GET | /client/{id} | 查询客户端 |
| GET | /client | 查询所有客户端 |
| DELETE | /client/{id} | 删除客户端 |

### 监控数据

| 方法 | 路径 | 功能 |
|------|------|------|
| POST | /monitor/report | 上报监控数据 |
| GET | /monitor/{clientId} | 查询监控数据 |
| GET | /monitor/{clientId}/latest | 查询最新数据 |

## 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
