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

## SQLite并发处理策略

### 问题分析

SQLite默认使用文件级锁，存在以下并发问题：
- 写操作会锁定整个数据库文件
- 高并发写入时容易导致锁等待和超时
- 长时间写事务会阻塞读操作

### 解决方案

#### 1. WAL模式（Write-Ahead Logging）
启用WAL模式可以实现读写并发：
- 读操作不需要等待写锁
- 写操作不会阻塞读操作
- 多个读操作可以同时进行

**配置方式：**
```sql
PRAGMA journal_mode=WAL;
PRAGMA synchronous=NORMAL;
PRAGMA cache_size=-20000;  -- 20MB缓存
```

#### 2. 读写锁机制设计

| 操作类型 | 锁类型 | 并发特性 |
|----------|--------|----------|
| 监控数据写入 | 共享写锁 | 支持批量写入合并 |
| 配置查询 | 共享读锁 | 支持多线程并发 |
| 客户端注册 | 独占写锁 | 需快速完成 |
| 历史数据查询 | 共享读锁 | 支持多线程并发 |

#### 3. 连接池配置

| 参数 | 配置值 | 说明 |
|------|--------|------|
| 最大连接数 | 20 | 根据并发量调整 |
| 最小空闲连接 | 5 | 保持一定数量的空闲连接 |
| 连接超时 | 30秒 | 避免长时间等待 |
| 最大等待时间 | 10秒 | 获取连接的最大等待时间 |

#### 4. 事务优化策略

**写入优化：**
- 使用批量插入（BATCH INSERT）
- 缩短事务生命周期
- 避免在事务中执行查询操作

**读取优化：**
- 使用只读事务
- 避免长时间持有读锁
- 合理使用索引

#### 5. 锁超时处理

```sql
PRAGMA busy_timeout=5000;  -- 5秒超时
```

当数据库被锁定时，等待5秒后返回错误，避免无限等待。

#### 6. 数据分区策略

为监控数据表设计时间分区，减少单表数据量：
- 按天分区：`monitor_data_20240101`
- 定期清理历史数据
- 查询时只访问相关分区

#### 7. 代码层面的并发控制

```
┌─────────────────────────────────────────────────────────────┐
│                    数据库访问层                              │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │   读操作    │    │   写操作    │    │   事务管理  │     │
│  │ (共享读锁)  │    │ (独占写锁)  │    │    组件     │     │
│  └──────┬──────┘    └──────┬──────┘    └──────┬──────┘     │
│         │                  │                  │             │
│         ▼                  ▼                  ▼             │
│  ┌─────────────────────────────────────────────────────┐    │
│  │              SQLite连接池 (WAL模式)                   │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

### 并发控制流程图

```
请求进入
    │
    ├─────── 读请求 ───────> 获取共享读锁 ────> 执行查询 ────> 释放锁 ────> 返回结果
    │
    └─────── 写请求 ───────> 获取独占写锁 ────> 执行写入 ────> 释放锁 ────> 返回结果
                              │
                              ├───── 锁等待超时 ─────> 返回错误
                              │
                              └───── 锁获取成功 ─────> 继续执行
```

### 注意事项

1. **避免长事务**：写事务应尽量短，避免持有锁时间过长
2. **批量写入**：多个客户端的监控数据可以合并批量写入
3. **异步写入**：非实时数据可以采用异步队列写入
4. **定期维护**：定期执行VACUUM清理数据库碎片
5. **监控告警**：监控锁等待时间，超过阈值时告警
