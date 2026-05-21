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
