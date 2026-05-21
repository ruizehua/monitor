# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- 客户端注册功能
- 监控数据上报功能
- CPU使用率监控
- 物理内存监控
- 虚拟内存监控
- 磁盘占用监控
- 进程信息监控
- SQLite数据库存储
- 监控数据查询API

### Changed
- [2026-05-21 15:40:00] 管理员：分离功能需求和非功能需求
  - 影响范围：requirements.md, non-functional.md
  - 相关文档：requirements.md, non-functional.md
- [2026-05-21 15:35:00] 管理员：整理功能需求和规范文档
  - 影响范围：requirements.md, README.md, contribution.md, non-functional.md
  - 相关文档：requirements.md, README.md, contribution.md, non-functional.md
- [2026-05-21 15:25:00] 管理员：配置本地JDK和Go环境
  - 影响范围：pom.xml, deployment.md
  - 相关文档：design.md, deployment.md
- [2026-05-21 15:12:00] 管理员：完善技术规范文档（architecture、design、data-model、api、security、deployment）
  - 影响范围：*.md文档
  - 相关文档：architecture.md, design.md, data-model.md, api.md, security.md, deployment.md
- [2026-05-21 15:08:00] 管理员：调整前端架构，整合到后端项目（HTML5+JS+CSS3）
  - 影响范围：backend/src/main/resources/static/
  - 相关文档：architecture.md
- [2026-05-21 15:02:00] 管理员：创建项目目录结构（后端、前端、客户端）
  - 影响范围：backend/、frontend/、client/
  - 相关文档：architecture.md
- [2026-05-21 14:58:00] 管理员：添加数据库表开发规范（完善代码风格指南）
  - 影响范围：code-style.md
  - 相关文档：code-style.md
- [2026-05-21 14:55:00] 管理员：添加Java、前端、Go开发规范（完善代码风格指南）
  - 影响范围：code-style.md
  - 相关文档：code-style.md

### Deprecated
- None

### Removed
- None

### Fixed
- None

### Security
- None
