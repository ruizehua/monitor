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
- [2026-05-22 14:30:00] 管理员：优化前端页面显示效果和修复多个功能问题
  - 影响范围：backend/src/main/resources/static/html/index.html, client/src/main.go, backend/Dockerfile, backend/src/main/java/com/nari/monitor/config/WebMvcConfig.java
  - 相关文档：design.md
  - 变更内容：
    1. 优化前端页面显示效果（现代化深色主题设计）
    2. 修复Go客户端GLIBC兼容性问题（使用CGO_ENABLED=0静态编译）
    3. 修复磁盘数据获取逻辑（使用df命令正确获取根目录磁盘信息）
    4. 添加客户端注册容错机制（当客户端名称已存在时自动查找现有客户端）
    5. 新增WebMvcConfig配置类（静态资源配置和欢迎页面配置）
    6. 添加docker-compose.yml文件（项目根目录统一管理Docker配置）
- [2026-05-22 10:15:00] 管理员：将Go客户端集成到Java服务容器中（同一容器部署）
  - 影响范围：backend/Dockerfile, backend/start.sh, client/src/main.go
  - 相关文档：deployment.md
- [2026-05-22 10:10:00] 管理员：修复部署脚本中文乱码问题（将中文提示改为英文）
  - 影响范围：backend/deploy.bat
  - 相关文档：deployment.md
- [2026-05-22 10:00:00] 管理员：增强Docker部署强制规则（添加4.4节专门的部署规则条款）
  - 影响范围：AGENTS.md
  - 相关文档：AGENTS.md
- [2026-05-22 09:55:00] 管理员：增加强制部署规则（每次修改需求或代码后必须更新Docker部署）
  - 影响范围：AGENTS.md, backend/deploy.bat, backend/deploy.sh
  - 相关文档：AGENTS.md, deployment.md
- [2026-05-22 09:50:00] 管理员：更新Docker部署规则（增加docker-compose和热更新方式）
  - 影响范围：AGENTS.md
  - 相关文档：AGENTS.md, deployment.md
- [2026-05-22 09:45:00] 管理员：添加Docker部署规则（修改需求或代码后需重新部署Docker）
  - 影响范围：AGENTS.md
  - 相关文档：AGENTS.md, deployment.md
- [2026-05-21 15:50:00] 管理员：生成完整代码实现（Java后端+Go客户端+HTML前端）
  - 影响范围：backend/src/main/java/, backend/src/main/resources/, client/src/main.go
  - 相关文档：requirements.md, design.md
- [2026-05-21 15:45:00] 管理员：添加SQLite并发处理策略（WAL模式、读写锁机制）
  - 影响范围：data-model.md, backend/src/main/resources/application.yml, backend/src/main/java/com/nari/monitor/config/SQLiteConfig.java
  - 相关文档：data-model.md
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
