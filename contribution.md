# 贡献指南

## 概述

本文档定义了项目的开发约定和贡献流程。所有开发者必须遵循本指南。

## 开发规范

### 代码规范
- 遵循项目代码风格指南 [code-style.md](code-style.md)
- Java代码遵循三层架构规范
- Go代码遵循简洁风格
- 前端代码使用HTML5+CSS3+JavaScript

### 命名规范
详细规范请参见 [code-style.md](code-style.md)。

| 语言 | 类/类型 | 方法/函数 | 变量 | 常量 |
|------|---------|-----------|------|------|
| Java | PascalCase | camelCase | camelCase | UPPER_SNAKE_CASE |
| Go | PascalCase | camelCase | camelCase | PascalCase |
| 前端 | PascalCase | camelCase | camelCase | UPPER_SNAKE_CASE |

## 分支策略

| 分支类型 | 命名规则 | 用途 |
|----------|----------|------|
| main | main | 稳定版本分支，仅接受合并 |
| develop | develop | 开发分支 |
| feature/* | feature/xxx | 功能特性分支 |
| bugfix/* | bugfix/xxx | Bug修复分支 |

### 分支命名规范
- 功能分支: `feature/<功能描述>`
- Bug修复: `bugfix/<问题描述>`
- 热修复: `hotfix/<问题描述>`
- 示例: `feature/client-register`, `bugfix/memory-leak`

## Git提交规范

### 提交信息格式
```
<type>: <简短描述>

[详细说明]

[相关issue或文档]
```

### 类型说明

| 类型 | 说明 | 示例 |
|------|------|------|
| feat | 新功能 | `feat: 添加客户端注册API` |
| fix | Bug修复 | `fix: 修复内存监控数据丢失问题` |
| docs | 文档更新 | `docs: 更新API文档` |
| style | 代码格式调整 | `style: 格式化代码` |
| refactor | 代码重构 | `refactor: 重构数据访问层` |
| test | 测试用例 | `test: 添加单元测试` |
| chore | 构建/工具更新 | `chore: 更新Maven配置` |

### 提交示例
```bash
# 添加新功能
git commit -m "feat: 添加监控数据查询API

- 添加按客户端ID查询历史数据接口
- 添加时间范围查询功能
- 更新API文档

相关文档: api.md"

# 修复Bug
git commit -m "fix: 修复客户端断线重连失败问题

- 添加重试机制
- 优化错误处理
- 添加重连日志

相关issue: #123"
```

## 变更记录

每次重要变更必须更新 [CHANGELOG.md](CHANGELOG.md)：

```markdown
### Changed
- [YYYY-MM-DD HH:mm:ss] 变更人：变更内容（变更原因）
  - 影响范围：模块/文件
  - 相关文档：doc1.md, doc2.md
```

## PR流程

### 提交流程
1. 从develop分支创建特性分支
2. 在特性分支上进行开发和测试
3. 提交代码（遵循提交规范）
4. 更新CHANGELOG.md
5. 推送到远程仓库
6. 创建Pull Request

### PR描述模板
```markdown
## 描述
[简要描述本次变更]

## 变更类型
- [ ] 新功能
- [ ] Bug修复
- [ ] 文档更新
- [ ] 代码重构

## 影响范围
[描述变更影响的模块或功能]

## 测试情况
- [ ] 单元测试通过
- [ ] 集成测试通过
- [ ] 手动测试通过

## 相关文档
[列出更新或相关的文档]
```

## 代码评审准则

### 代码质量
- [ ] 代码符合编码规范
- [ ] 有适当的注释和文档
- [ ] 无硬编码的敏感信息
- [ ] 无潜在的性能问题

### 测试覆盖
- [ ] 有足够的测试覆盖率
- [ ] 新功能有对应的测试用例
- [ ] Bug修复有回归测试

### 安全性
- [ ] 无安全漏洞
- [ ] 输入参数正确校验
- [ ] 敏感数据正确处理

### 文档更新
- [ ] 相关文档已更新
- [ ] CHANGELOG.md已记录变更

## 环境配置

### 开发环境
- **JDK**: `C:\Program Files\Java\jdk-17`
- **Go**: `C:\Program Files\Go`
- **Maven**: `F:\tools\maven\apache-maven-3.9.11`

详细环境配置请参见 [deployment.md](deployment.md)。

## 常见问题

### Q: 如何处理合并冲突？
A: 1. 先拉取最新代码 `git pull origin develop`
   2. 切换到特性分支 `git checkout feature/xxx`
   3. 合并develop分支 `git merge develop`
   4. 解决冲突后提交

### Q: 提交信息写错了怎么办？
A: 使用 `git commit --amend` 修改最近一次提交
