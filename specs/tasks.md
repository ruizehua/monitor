# 数据库监控系统 - The Implementation Plan

## [x] Task 1: 创建Java后端项目结构
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 创建Maven项目结构
  - 添加Spring Boot 3.4.11依赖
  - 配置SQLite数据源
- **Acceptance Criteria Addressed**: AC-1, AC-3, AC-6
- **Test Requirements**:
  - `programmatic`: 项目能正常编译
  - `human-judgement`: 目录结构符合规范

## [ ] Task 2: 创建数据库表结构
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 创建客户端注册表(client)
  - 创建监控数据表(monitor_data)
  - 创建监控配置表(monitor_config)
- **Acceptance Criteria Addressed**: AC-3
- **Test Requirements**:
  - `programmatic`: 数据库表能正常创建
  - `programmatic`: 表结构符合设计

## [ ] Task 3: 实现客户端注册API
- **Priority**: P0
- **Depends On**: Task 2
- **Description**: 
  - 创建ClientController
  - 实现客户端注册接口
  - 返回监控配置（监控项、上报间隔）
- **Acceptance Criteria Addressed**: AC-1
- **Test Requirements**:
  - `programmatic`: POST /api/client/register 返回200
  - `programmatic`: 返回包含monitorItems和reportInterval的JSON

## [x] Task 4: 实现监控数据接收API
- **Priority**: P0
- **Depends On**: Task 2
- **Description**: 
  - 创建MonitorController
  - 实现监控数据上报接口
  - 将数据存储到SQLite
- **Acceptance Criteria Addressed**: AC-2, AC-3
- **Test Requirements**:
  - `programmatic`: POST /api/monitor/report 返回200
  - `programmatic`: 数据正确存储到数据库

## [x] Task 5: 实现监控数据查询API
- **Priority**: P1
- **Depends On**: Task 4
- **Description**: 
  - 实现按客户端ID查询监控数据
  - 实现按时间范围查询监控数据
- **Acceptance Criteria Addressed**: FR-6
- **Test Requirements**:
  - `programmatic`: GET /api/monitor/{clientId} 返回200
  - `programmatic`: GET /api/monitor/{clientId}?startTime=&endTime= 返回200

## [x] Task 6: 创建Go客户端项目结构
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 创建Go项目目录结构
  - 添加必要的Go模块
- **Acceptance Criteria Addressed**: AC-4, AC-5, AC-6, AC-7
- **Test Requirements**:
  - `programmatic`: go build 能正常编译
  - `human-judgement`: 目录结构清晰

## [x] Task 7: 实现主机资源采集功能
- **Priority**: P0
- **Depends On**: Task 6
- **Description**: 
  - 使用Go原生代码采集CPU使用率
  - 采集物理内存和虚拟内存
  - 采集磁盘占用率
  - 采集进程信息（PID、资源占用）
- **Acceptance Criteria Addressed**: AC-4, AC-5, AC-6, AC-7
- **Test Requirements**:
  - `programmatic`: 能正确获取CPU使用率
  - `programmatic`: 能正确获取内存信息
  - `programmatic`: 能正确获取磁盘信息
  - `programmatic`: 能正确获取进程信息

## [x] Task 8: 实现客户端注册和上报功能
- **Priority**: P0
- **Depends On**: Task 7
- **Description**: 
  - 实现客户端注册逻辑
  - 实现定时上报逻辑（30秒间隔）
  - 实现HTTP请求发送
- **Acceptance Criteria Addressed**: AC-1, AC-2
- **Test Requirements**:
  - `programmatic`: 注册请求成功
  - `programmatic`: 定时上报正常工作

## [x] Task 9: 编写配置文件
- **Priority**: P1
- **Depends On**: Task 3
- **Description**: 
  - 编写服务端配置文件（监控项配置）
  - 编写客户端配置文件（服务端地址等）
- **Acceptance Criteria Addressed**: FR-2
- **Test Requirements**:
  - `human-judgement`: 配置文件完整

## [ ] Task 10: 集成测试
- **Priority**: P1
- **Depends On**: Task 4, Task 8
- **Description**: 
  - 启动服务端
  - 启动客户端
  - 验证端到端流程
- **Acceptance Criteria Addressed**: AC-1, AC-2, AC-3
- **Test Requirements**:
  - `programmatic`: 客户端注册成功
  - `programmatic`: 监控数据正确上报并存储
