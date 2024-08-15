# Web威胁感知系统

本项目是一个使用 SpringBoot 框架和 RabbitMQ 消息队列构建的 Web 威胁感知系统。平台端使用 Java 编写，旨在同步平台和客户端之间的消息。

## 功能

- **资产探测：** 平台可以发送资产探测指令，收集客户端的系统信息、进程详情、应用信息和服务详情。
- **风险补丁：** 对客户端进行风险补丁操作。
- **弱口令检测：** 检测客户端的弱口令。
- **漏洞检测：** 检测客户端的系统漏洞。
- **应用风险探测：** 探测客户端的应用风险。
- **日志同步：** 实现客户端和平台之间的日志同步。
- **基线检查：** 对客户端进行基线检查。
- **操作日志管理：** 使用 AOP 实现平台端的操作日志管理。

## 技术栈

- **SpringBoot：** 用于构建平台的核心框架。
- **SpringSecurity：** 用于实现应用程序的安全管理，包括认证和授权功能。
- **RabbitMQ：** 用于实现消息队列，进行平台和客户端之间的消息同步。
- **Java：** 平台端的主要编程语言。
- **Python：** 客户端的主要编程语言。

## 安装与运行

1. 使用IDEA导入项目

2. 安装依赖

3. 配置 MySQL

   修改 `/resource` 目录下的配置文件`application.yml`

   补全以下配置

   - 数据库连接地址
   - 数据库用户名
   - 数据库密码

4. 配置Redis

   修改 `/resource` 目录下的配置文件`application.yml`

   补全以下配置

   - Redis 服务器地址
   - Redis 密码
   - Redis 端口

5. 创建RabbitMQ虚拟机

6. 配置RabbitMQ

   修改 `/resource` 目录下的配置文件`application.yml`

   补全以下配置

   - RabbitMQ 服务器地址
   - RabbitMQ 服务端口
   - RabbitMQ 用户名
   - RabbitMQ 密码
   - RabbitMQ 虚拟主机

7. 在 RabbitMQ 虚拟机下创建以下队列 `queue`

   Type: `Classic`

   Durability: `Durable`

   - `account_queue`
   - `app_queue`
   - `app_threats_queue`
   - `baseline_queue`
   - `hotfix_queue`
   - `logs_queue`
   - `process_queue`
   - `service_queue`
   - `status_queue`
   - `sysinfo_queue`
   - `vulnerability_queue`
   - `weak_pwd_queue`

8. 在 RabbitMQ 虚拟机下创建交换机 `exchange`

   Type: `direct`

   Durability: `Durable`

   - `sysinfo_exchange`
   - `agent_exchange`

9. 在 RabbitMQ 虚拟机下为 `sysinfo_exchange` 添加 `Routing Key`

   |         To          |  Routing key  |
   | :-----------------: | :-----------: |
   |    account_queue    |    account    |
   |      app_queue      |      app      |
   |  app_threats_queue  |  app_threats  |
   |   baseline_queue    |   baseline    |
   |    hotfix_queue     |    hotfix     |
   |     logs_queue      |     logs      |
   |    process_queue    |    process    |
   |    service_queue    |    service    |
   |    status_queue     |    status     |
   |    sysinfo_queue    |    sysinfo    |
   | vulnerability_queue | vulnerability |
   |   weak_pwd_queue    |    weakPwd    |

10. 运行项目

## 使用

平台启动后，可以通过可视化界面实现对客户端的各项检测和操作。

### 测试登录数据

| username | password   |
| -------- | ---------- |
| jay1an   | jay1an     |
| lisa     | jay1an     |
| admin    | adminadmin |

