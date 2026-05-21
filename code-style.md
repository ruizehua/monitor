# 代码风格指南

## 概述

本文档定义了本项目的代码风格规范。

## Java规范

### 命名
- **类名**: PascalCase
- **方法名**: camelCase
- **变量名**: camelCase
- **常量名**: UPPER_SNAKE_CASE
- **包名**: 全小写

### 格式
- 4空格缩进
- 行长度不超过120字符
- 大括号单独占行
- 使用Lombok注解

### 三层架构

#### 控制层 (Controller)
- 包名: `controller`
- 类名后缀: `Controller`
- 职责: 处理HTTP请求、参数校验、调用Service层、返回响应
- 不包含业务逻辑
- 使用`@RestController`注解

#### 业务层 (Service)
- 包名: `service`
- 类名后缀: `Service`
- 接口名后缀: `Service`
- 实现类后缀: `ServiceImpl`
- 职责: 封装业务逻辑、事务管理、调用Repository层

#### 数据层 (Repository)
- 包名: `repository`
- 类名后缀: `Repository`
- 职责: 数据访问、SQL执行
- 使用Spring Data JPA或MyBatis

### 分层依赖
- Controller → Service → Repository
- 禁止跨层调用
- Service之间可相互调用

### 异常处理
- 使用统一异常处理
- 自定义业务异常类
- 异常信息清晰明确

## 前端规范

### 命名
- **组件名**: PascalCase
- **变量名**: camelCase
- **函数名**: camelCase
- **常量名**: UPPER_SNAKE_CASE
- **文件命名**: kebab-case

### 格式
- 2空格缩进
- 行长度不超过100字符
- 大括号与声明同行
- 使用ESLint格式化

### 组件分层

#### 页面层 (Page)
- 目录: `pages/`
- 职责: 页面入口、路由配置、数据整合
- 不包含业务逻辑

#### 组件层 (Component)
- 目录: `components/`
- 职责: UI组件、复用性强
- 无状态组件优先

#### 业务组件层 (Business)
- 目录: `business/`
- 职责: 业务逻辑组件
- 包含特定业务逻辑

#### 工具层 (Utils)
- 目录: `utils/`
- 职责: 通用工具函数

#### 服务层 (Service)
- 目录: `services/`
- 职责: API请求封装

### 状态管理
- 使用Vuex或Pinia
- 状态分层管理
- 避免全局状态滥用

### 样式规范
- 使用CSS Modules或Tailwind CSS
- 类名使用BEM规范
- 避免全局样式污染

## Go规范

### 命名
- **包名**: 简洁小写
- **导出函数**: PascalCase
- **私有函数**: camelCase
- **变量名**: camelCase

### 格式
- 使用 `go fmt` 格式化
- 大括号与声明同行

### 项目结构
- `main.go`: 入口文件
- `cmd/`: 命令行工具
- `internal/`: 内部包
- `pkg/`: 可复用包
- `api/`: API接口定义
- `service/`: 业务逻辑
- `repository/`: 数据访问
- `config/`: 配置管理
- `util/`: 工具函数

### 错误处理
- 显式错误检查
- 错误信息清晰
- 使用errors包处理错误

### 并发
- 使用goroutine和channel
- 避免共享状态
- 使用sync包进行同步

## 数据库规范

### 表命名
- **表名**: snake_case，小写字母 + 下划线
- **表名前缀**: 模块缩写，如 `sys_`（系统）、`biz_`（业务）、`log_`（日志）
- **多词表名**: 使用下划线分隔，如 `user_info`、`order_detail`

### 字段命名
- **字段名**: snake_case，小写字母 + 下划线
- **主键字段**: 统一命名为 `id`，类型为BIGINT
- **外键字段**: 表名_id，如 `user_id`、`order_id`
- **布尔字段**: 前缀 `is_`，如 `is_deleted`、`is_enabled`
- **时间字段**: `created_at`、`updated_at`、`deleted_at`

### 字段类型
- **主键**: BIGINT，自增
- **字符串**: VARCHAR，避免TEXT类型滥用
- **数值**: INT/DECIMAL，根据精度选择
- **日期时间**: DATETIME/DATE/TIME
- **布尔**: TINYINT(1)，0/1表示

### 主键与索引
- **主键**: 每个表必须有主键，优先使用自增主键
- **唯一索引**: 唯一约束字段创建唯一索引
- **普通索引**: 用于查询条件的字段创建索引
- **联合索引**: 根据查询需求创建，注意最左前缀原则

### 约束规范
- **非空约束**: 必要字段添加NOT NULL
- **默认值**: 为字段设置合理默认值
- **外键约束**: 外键字段添加外键约束，级联删除/更新谨慎使用

### 设计原则
- **范式规范**: 遵循第三范式，避免数据冗余
- **软删除**: 使用 `is_deleted` 字段实现软删除
- **时间戳**: 每张表必须包含 `created_at` 和 `updated_at` 字段
- **表注释**: 为表和字段添加中文注释

### 建表语句示例
```sql
CREATE TABLE `user_info` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `is_enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用',
  `is_deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
```

## Git规范

### 分支策略
- main: 主分支
- develop: 开发分支
- feature/*: 功能分支
- bugfix/*: 修复分支

### 提交信息
```
<type>: <description>

[body]
```

类型: feat, fix, docs, style, refactor, test, chore
