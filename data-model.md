# 数据模型定义

## 概述

本文档定义了项目的数据模型。

## 实体关系

```
client 1:N monitor_data
client 1:N monitor_config
```

## client表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键 |
| client_name | TEXT | NOT NULL UNIQUE | 客户端名称 |
| host_ip | TEXT | NOT NULL | 主机IP |
| report_interval | INTEGER | | 上报间隔 |
| created_at | TIMESTAMP | | 创建时间 |
| updated_at | TIMESTAMP | | 更新时间 |

## monitor_data表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键 |
| client_id | INTEGER | NOT NULL | 客户端ID |
| cpu_usage | REAL | | CPU使用率 |
| physical_memory_used | INTEGER | | 物理内存使用量 |
| physical_memory_total | INTEGER | | 物理内存总量 |
| virtual_memory_used | INTEGER | | 虚拟内存使用量 |
| virtual_memory_total | INTEGER | | 虚拟内存总量 |
| disk_usage | REAL | | 磁盘使用率 |
| disk_total | INTEGER | | 磁盘总量 |
| disk_used | INTEGER | | 磁盘使用量 |
| process_info | TEXT | | 进程信息JSON |
| report_time | TIMESTAMP | NOT NULL | 上报时间 |
| created_at | TIMESTAMP | | 创建时间 |

## monitor_config表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 主键 |
| client_id | INTEGER | | 客户端ID |
| config_key | TEXT | NOT NULL | 配置键 |
| config_value | TEXT | | 配置值 |
| created_at | TIMESTAMP | | 创建时间 |
| updated_at | TIMESTAMP | | 更新时间 |
