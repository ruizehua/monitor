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

## Go规范

### 命名
- **包名**: 简洁小写
- **导出函数**: PascalCase
- **私有函数**: camelCase
- **变量名**: camelCase

### 格式
- 使用 `go fmt` 格式化
- 大括号与声明同行

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
