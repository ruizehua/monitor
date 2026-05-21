# 数据库监控系统 - Product Requirement Document

## Overview
- **Summary**: 自研数据库监控系统，分为Java服务端和Go客户端。服务端负责管理客户端注册、下发监控配置、接收和存储监控数据；客户端负责主机资源监控和定时上报。
- **Purpose**: 提供主机资源监控能力，包括CPU、内存、磁盘、进程等指标的采集和存储。
- **Target Users**: 运维人员、系统管理员

## Goals
- Java服务端支持客户端注册，记录客户端IP和名称
- 服务端根据配置文件向客户端下发监控项
- Go客户端采集主机资源指标（CPU、内存、磁盘、进程等）
- 客户端定时上报监控数据（间隔由服务端配置）
- 服务端将监控数据存储到SQLite数据库

## Non-Goals (Out of Scope)
- 不提供Web UI界面
- 不支持分布式部署
- 不提供告警功能

## Background & Context
- 数据库使用SQLite
- Java服务端使用Maven 3.9.11, JDK 17, Spring Boot 3.4.11
- Go客户端使用原生代码或命令行工具进行监控
- Go客户端以个人用户权限运行，无需root

## Functional Requirements
- **FR-1**: Java服务端提供客户端注册API，接收客户端IP和名称
- **FR-2**: 服务端根据配置文件向客户端下发监控配置（监控项、上报间隔）
- **FR-3**: Go客户端采集主机CPU使用率、物理内存、虚拟内存、磁盘占用、进程信息
- **FR-4**: Go客户端每隔N秒（服务端配置）上报监控数据
- **FR-5**: 服务端接收监控数据并存储到SQLite数据库
- **FR-6**: 服务端提供监控数据查询API

## Non-Functional Requirements
- **NFR-1**: 客户端上报间隔可配置（默认30秒）
- **NFR-2**: 服务端支持高并发客户端连接
- **NFR-3**: 监控数据存储保留时间可配置

## Constraints
- **Technical**: Java 17, Spring Boot 3.4.11, Maven 3.9.11, Go 1.21+, SQLite
- **Business**: Go客户端以非root用户运行
- **Dependencies**: 仅使用Go原生代码和标准库，不依赖第三方监控库

## Assumptions
- 服务端先于客户端启动
- 网络连接稳定，客户端能正常访问服务端
- 客户端有足够权限执行系统命令（如ps, top, df等）

## Acceptance Criteria

### AC-1: 客户端注册
- **Given**: 服务端已启动，监听指定端口
- **When**: 客户端发送注册请求（包含IP和名称）
- **Then**: 服务端返回成功响应，包含监控配置（监控项列表、上报间隔）
- **Verification**: `programmatic`

### AC-2: 监控数据上报
- **Given**: 客户端已注册成功，收到监控配置
- **When**: 达到上报间隔时间
- **Then**: 客户端采集监控数据并发送到服务端
- **Verification**: `programmatic`

### AC-3: 监控数据存储
- **Given**: 服务端收到客户端上报的监控数据
- **When**: 数据格式正确
- **Then**: 服务端将数据存储到SQLite数据库
- **Verification**: `programmatic`

### AC-4: CPU监控
- **Given**: Go客户端运行中
- **When**: 执行监控采集
- **Then**: 正确获取CPU使用率百分比
- **Verification**: `programmatic`

### AC-5: 内存监控
- **Given**: Go客户端运行中
- **When**: 执行监控采集
- **Then**: 正确获取物理内存和虚拟内存使用情况
- **Verification**: `programmatic`

### AC-6: 磁盘监控
- **Given**: Go客户端运行中
- **When**: 执行监控采集
- **Then**: 正确获取磁盘占用率
- **Verification**: `programmatic`

### AC-7: 进程监控
- **Given**: Go客户端运行中
- **When**: 执行监控采集
- **Then**: 正确获取指定进程的PID和资源占用
- **Verification**: `programmatic`

## Open Questions
- [ ] 是否需要支持多个客户端同时监控同一主机？
- [ ] 是否需要提供监控数据的删除或清理机制？
