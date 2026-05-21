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
- Spring Boot 3.4.11
- SQLite
- Maven 3.9.11

### 客户端 (Go)
- Go 1.21+
- 原生代码实现，无第三方监控库依赖

## 快速开始

### 启动服务端

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

## API 接口

### 客户端注册

```http
POST /api/client/register
Content-Type: application/json

{
  "clientName": "myclient",
  "hostIp": "192.168.1.100"
}
```

响应：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "clientId": 1,
    "clientName": "myclient",
    "hostIp": "192.168.1.100",
    "reportInterval": 30,
    "monitorItems": ["cpu_usage", "physical_memory", "virtual_memory", "disk_usage", "process_info"]
  }
}
```

### 监控数据上报

```http
POST /api/monitor/report
Content-Type: application/json

{
  "clientId": 1,
  "cpuUsage": 25.5,
  "physicalMemoryUsed": 4294967296,
  "physicalMemoryTotal": 16777216000,
  "virtualMemoryUsed": 2147483648,
  "virtualMemoryTotal": 33554432000,
  "diskUsage": 65.2,
  "diskTotal": 500000000000,
  "diskUsed": 326000000000,
  "processInfo": "[...]"
}
```

### 查询监控数据

```http
GET /api/monitor/{clientId}
GET /api/monitor/{clientId}?startTime=2024-01-01T00:00:00&endTime=2024-01-02T00:00:00
GET /api/monitor/{clientId}/latest
```

## 配置说明

服务端配置文件位于 `backend/src/main/resources/application.yml`：

```yaml
monitor:
  default-report-interval: 30  # 默认上报间隔（秒）
  default-monitor-items:       # 默认监控项
    - cpu_usage
    - physical_memory
    - virtual_memory
    - disk_usage
    - process_info
```

## 项目结构

```
monitor/
├── backend/                   # Java服务端
│   ├── src/main/java/         # Java源码
│   ├── src/main/resources/    # 配置文件
│   └── pom.xml                # Maven配置
├── client/                    # Go客户端
│   ├── main.go                # 主入口
│   ├── client.go              # HTTP客户端
│   └── collector.go           # 监控数据采集
├── specs/                     # 规格文档
│   ├── SPEC.md                # 需求规格
│   ├── tasks.md               # 实现计划
│   └── checklist.md           # 验证清单
└── README.md
```

## 数据库表结构

### client 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INTEGER | 主键 |
| client_name | TEXT | 客户端名称 |
| host_ip | TEXT | 主机IP |
| report_interval | INTEGER | 上报间隔 |
| created_at | TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | 更新时间 |

### monitor_data 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INTEGER | 主键 |
| client_id | INTEGER | 客户端ID |
| cpu_usage | REAL | CPU使用率 |
| physical_memory_used | INTEGER | 物理内存使用量 |
| physical_memory_total | INTEGER | 物理内存总量 |
| virtual_memory_used | INTEGER | 虚拟内存使用量 |
| virtual_memory_total | INTEGER | 虚拟内存总量 |
| disk_usage | REAL | 磁盘使用率 |
| disk_total | INTEGER | 磁盘总量 |
| disk_used | INTEGER | 磁盘使用量 |
| process_info | TEXT | 进程信息(JSON) |
| report_time | TIMESTAMP | 上报时间 |
| created_at | TIMESTAMP | 创建时间 |

## License

MIT License
