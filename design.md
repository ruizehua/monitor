# 技术设计方案

## 概述

本文档定义了监控系统的总体技术设计方案。

## 技术栈选型

| 模块 | 技术 | 版本 |
|------|------|------|
| 服务端语言 | Java | 17 |
| 服务端框架 | Spring Boot | 3.4.11 |
| 数据库 | SQLite | 3.x |
| 客户端语言 | Go | 1.21+ |

## 架构决策

- 采用客户端-服务端架构
- 使用RESTful API通信
- SQLite作为轻量级数据库

## 模块划分

- **controller**: REST API控制层
- **service**: 业务逻辑层
- **repository**: 数据访问层
- **entity**: 数据库实体
- **dto**: 数据传输对象

## 数据流向

```
客户端注册 → Controller → Service → Repository → Database
监控上报 → Controller → Service → Repository → Database
```
