# 数据模型定义

## 概述

本文档定义了项目的数据模型和数据库表结构。

## 实体关系

```
client 1:N monitor_data    (一个客户端对应多条监控数据)
client 1:N monitor_config  (一个客户端对应多个配置项)
```

## client表（客户端信息表）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PRIMARY KEY AUTOINCREMENT | 主键ID |
| client_name | VARCHAR(100) | NOT NULL UNIQUE | 客户端名称 |
| host_ip | VARCHAR(50) | NOT NULL | 主机IP地址 |
| host_name | VARCHAR(100) | | 主机名称 |
| os_type | VARCHAR(20) | | 操作系统类型(WINDOWS/LINUX) |
| report_interval | INTEGER | DEFAULT 30 | 上报间隔(秒) |
| is_enabled | TINYINT(1) | DEFAULT 1 | 是否启用(1=启用,0=禁用) |
| is_deleted | TINYINT(1) | DEFAULT 0 | 是否删除(1=已删除,0=正常) |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

## monitor_data表（监控数据表）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PRIMARY KEY AUTOINCREMENT | 主键ID |
| client_id | BIGINT | NOT NULL, FOREIGN KEY | 客户端ID(关联client.id) |
| cpu_usage | DECIMAL(5,2) | | CPU使用率(0-100) |
| physical_memory_used | BIGINT | | 物理内存使用量(字节) |
| physical_memory_total | BIGINT | | 物理内存总量(字节) |
| virtual_memory_used | BIGINT | | 虚拟内存使用量(字节) |
| virtual_memory_total | BIGINT | | 虚拟内存总量(字节) |
| disk_usage | DECIMAL(5,2) | | 磁盘使用率(0-100) |
| disk_total | BIGINT | | 磁盘总量(字节) |
| disk_used | BIGINT | | 磁盘使用量(字节) |
| process_count | INTEGER | | 进程数量 |
| process_info | TEXT | | 进程信息(JSON格式) |
| report_time | DATETIME | NOT NULL | 上报时间 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

## monitor_config表（监控配置表）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PRIMARY KEY AUTOINCREMENT | 主键ID |
| client_id | BIGINT | FOREIGN KEY | 客户端ID(关联client.id) |
| config_key | VARCHAR(100) | NOT NULL | 配置键 |
| config_value | TEXT | | 配置值 |
| description | VARCHAR(255) | | 配置描述 |
| is_deleted | TINYINT(1) | DEFAULT 0 | 是否删除 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

## 索引设计

| 表名 | 索引名 | 字段 | 类型 |
|------|--------|------|------|
| client | uk_client_name | client_name | UNIQUE |
| client | idx_client_is_deleted | is_deleted | NORMAL |
| monitor_data | idx_monitor_client_id | client_id | NORMAL |
| monitor_data | idx_monitor_report_time | report_time | NORMAL |
| monitor_config | idx_config_client_id | client_id | NORMAL |
| monitor_config | uk_config_client_key | client_id, config_key | UNIQUE |
