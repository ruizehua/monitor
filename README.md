# 数据库监控系统

基于Spring Boot和Go的主机资源监控系统，分为Java服务端和Go客户端。

## 功能特性

- **客户端注册**: Go客户端启动时向服务端注册，提供主机IP和客户端名称
- **动态配置下发**: 服务端根据配置文件向客户端下发监控项和上报间隔
- **主机资源监控**:
  - CPU使用率
  - 物理内存/虚拟内存
  - 磁盘占用率
  - 进程信息（PID、CPU、内存占用）
- **定时上报**: 默认30秒上报间隔，可配置
- **数据持久化**: 使用SQLite存储监控数据

## 技术栈

### 服务端 (Java)
- Java 17
- Spring Boot 3.2.0
- SQLite
- Maven 3.9.11

### 客户端 (Go)
- Go 1.21+
- 原生代码实现，无第三方监控库依赖

### 前端
- HTML5 + CSS3 + JavaScript
- 整合到Java后端项目中

## 快速开始

### 环境要求

- **服务端**: Java 17, Maven 3.9.11
- **客户端**: Go 1.21+

### 启动服务端

```bash
cd backend
./mvnw spring-boot:run
```

或者使用本地Maven：

```bash
cd backend
mvn spring-boot:run
```

服务端将在 `http://localhost:8080` 启动。

### 启动客户端

```bash
cd client
go build -o monitor-client
./monitor-client -name=myclient -ip=192.168.1.100
```

参数说明：
- `-name`: 客户端名称（必填）
- `-ip`: 主机IP地址（必填）
- `-server`: 服务端地址（可选，默认 `http://localhost:8080`）
- `-interval`: 上报间隔秒数（可选，默认30）

## 项目结构

```
monitor/
├── backend/                           # Java服务端
│   ├── src/main/java/                 # Java源码
│   │   └── com/nari/monitor/         # 三层架构
│   │       ├── controller/           # 控制层
│   │       ├── service/               # 业务层
│   │       ├── repository/            # 数据层
│   │       ├── entity/                # 实体类
│   │       ├── dto/                   # 数据传输对象
│   │       ├── config/                # 配置类
│   │       └── exception/             # 异常处理
│   ├── src/main/resources/            # 配置文件
│   │   ├── static/                    # 前端静态资源
│   │   │   ├── css/                  # CSS样式
│   │   │   ├── js/                   # JavaScript脚本
│   │   │   ├── images/                # 图片资源
│   │   │   └── html/                  # HTML页面
│   │   └── application.yml            # 应用配置
│   ├── pom.xml                        # Maven配置
│   └── mvnw.cmd                      # Maven启动脚本
├── client/                            # Go客户端
│   └── src/                          # Go源码
├── specs/                             # 规格文档
│   ├── SPEC.md                        # 需求规格
│   ├── tasks.md                       # 实现计划
│   └── checklist.md                   # 验证清单
├── requirements.md                     # 功能需求文档
├── architecture.md                     # 架构设计文档
├── api.md                             # API接口文档
├── data-model.md                      # 数据模型文档
├── deployment.md                      # 部署文档
├── code-style.md                     # 代码风格指南
└── README.md                          # 项目说明
```

## 相关文档

| 文档 | 说明 |
|------|------|
| [requirements.md](requirements.md) | 功能需求文档 |
| [architecture.md](architecture.md) | 系统架构设计 |
| [api.md](api.md) | API接口规范 |
| [data-model.md](data-model.md) | 数据模型定义 |
| [deployment.md](deployment.md) | 部署文档 |
| [code-style.md](code-style.md) | 代码风格指南 |
| [design.md](design.md) | 技术设计方案 |
| [security.md](security.md) | 安全策略 |
| [contribution.md](contribution.md) | 贡献指南 |

## License

MIT License
