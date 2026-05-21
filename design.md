# 技术设计方案

## 概述

本文档定义了监控系统的总体技术设计方案。

## 技术栈选型

| 模块 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 服务端语言 | Java | 17 | LTS版本，稳定可靠 |
| 服务端框架 | Spring Boot | 3.2.0 | 社区成熟，生态完善 |
| 数据库 | SQLite | 3.45+ | 轻量级嵌入式数据库 |
| ORM框架 | Spring Data JPA | 3.2.x | 简化数据访问 |
| 客户端语言 | Go | 1.21+ | 高性能，跨平台 |
| 前端 | HTML5 + CSS3 + JavaScript | - | 原生技术栈 |

## 架构决策

- **架构风格**: 客户端-服务端(C/S)架构
- **通信协议**: RESTful API (HTTP/HTTPS)
- **数据库选择**: SQLite作为轻量级嵌入式数据库，无需额外部署
- **前端集成**: 前端静态资源与后端整合，不分离部署

## 模块划分

| 模块 | 职责 | 说明 |
|------|------|------|
| **controller** | REST API控制层 | 处理HTTP请求、参数校验、调用Service |
| **service** | 业务逻辑层 | 封装业务逻辑、事务管理 |
| **repository** | 数据访问层 | 数据持久化操作 |
| **entity** | 数据库实体 | JPA实体类，映射数据库表 |
| **dto** | 数据传输对象 | 请求/响应数据结构 |
| **config** | 配置类 | Spring配置、跨域配置等 |
| **exception** | 异常处理 | 全局异常处理、自定义异常 |

## 数据流向

```
客户端注册 → ClientController → ClientService → ClientRepository → SQLite
监控数据上报 → MonitorController → MonitorService → MonitorRepository → SQLite
数据查询 → MonitorController → MonitorService → MonitorRepository → SQLite
```

## 关键设计

### 数据库设计原则
- 遵循第三范式，避免数据冗余
- 使用软删除（is_deleted字段）
- 每个表包含created_at和updated_at时间戳

### API设计原则
- RESTful风格，使用合适的HTTP方法
- 统一响应格式，包含code、message、data字段
- 支持分页查询接口

### 安全性设计
- 输入参数校验（使用@Valid注解）
- SQL注入防护（JPA参数化查询）
- 敏感信息脱敏处理
