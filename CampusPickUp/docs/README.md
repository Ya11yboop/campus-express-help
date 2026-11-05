# 校园快递配送系统

面向高校校园的快递帮取/帮寄配送平台。学生既是下单方也是接单方（无需独立跑腿人员），实现"同校下单、同校接单、同校配送"的闭环。系统由**后台管理系统**、**微信小程序端**、**后端服务**三部分组成。

---

## 一、项目简介

- 学生通过微信小程序发布帮取 / 帮寄快递需求，同校其他学生在"接单大厅"接单并完成配送。
- 下单人只能取消自己「待接单」状态的订单，订单被接单后无法取消。
- 接单人接单后 **30 分钟内可无条件撤回**，订单回到待接单；**超过 30 分钟**需在后台提交撤回申请并填写理由，由管理员审核通过或驳回。
- 配送完成后需「申请配送完成」并上传配送到位凭证（可选图片），管理员审核通过后方可真正完成订单，同时通知下单人。
- 提供投诉/申诉、星级评价、信誉分扣除申请与审核、公告、轮播图、消息通知等功能。
- 支持**微信一键登录**自动注册；首次登录需完善身份证、学号、学校、姓名、电话、用户名、密码等信息后才能使用小程序。

## 二、技术栈

| 端 | 技术 |
| --- | --- |
| 后端服务 | Spring Boot 2.7.18、MyBatis、MySQL 8、Redis、JWT、WebSocket、阿里云 OSS、Druid |
| 后台管理系统 | Vue 3、Element Plus、Pinia、Vue Router、Vite、Axios |
| 微信小程序端 | 微信原生小程序（WXML / WXSS / JavaScript） |

## 三、目录结构

```
CampusPickUp
├── campus-pickup-server/        # 后端服务（Maven 多模块）
│   ├── pickup-admin/            # 启动模块（含配置、启动类）
│   ├── pickup-common/           # 公共模块（通用工具、返回结果、异常）
│   ├── pickup-framework/        # 框架模块（JWT、Redis、OSS、WebSocket、拦截器）
│   └── pickup-system/           # 业务模块（Controller/Service/Mapper/Domain）
├── campus-pickup-admin/         # 后台管理系统（Vue3 + Vite）
│   ├── src/views/               # 页面（系统/业务/内容/基础数据/首页）
│   ├── src/api/                 # 接口封装
│   ├── src/store/               # Pinia 状态管理
│   ├── src/router/              # 路由与菜单
│   └── src/layout/              # 布局（侧边栏/顶栏/多标签页）
├── campus-pickup-miniapp/       # 微信小程序端（原生）
│   ├── pages/                   # 页面（首页/接单大厅/下单/订单详情/个人中心/登录/完善信息等）
│   └── utils/                   # 请求封装、登录守卫
└── docs/                        # 项目文档
```

## 四、功能清单

### 后台管理系统
- 首页数据大屏：用户/订单/评价/申诉统计 + 近 7 天趋势图
- 系统管理：用户管理、角色管理
- 业务管理：订单管理、申诉投诉、评价管理、信誉分管理、信誉分申请、配送完成申请、接单撤回申请
- 内容管理：公告管理、轮播图管理
- 基础数据：驿站管理、宿舍楼管理、快递大小分类、学校管理、数据字典
- 登录认证、角色权限、校区数据隔离

### 微信小程序端
- 微信一键登录 + 账号密码登录、信息完善
- 首页（轮播图、公告、帮取/帮寄入口、通知角标）
- 下单（驿站选择、送达地址、配送费）、地址管理
- 接单大厅（按费用/驿站/收货地址搜索）、我的订单、订单详情
- 接单/撤回/取消/申请配送完成/投诉/评价
- 消息通知、个人中心（信誉分、我的订单入口等）

## 五、快速开始

### 1. 环境要求
- JDK 11、Maven 3.9+
- MySQL 8（数据库 `campus_pickup`）
- Redis（默认 6379）
- Node.js 16+、微信开发者工具

### 2. 后端启动
```bash
cd campus-pickup-server
mvn -pl pickup-admin -am package -DskipTests
java -jar pickup-admin/target/pickup-admin.jar
```
> 默认端口 8080；数据库/Redis/OSS/微信配置见 `pickup-admin/src/main/resources/application-druid.yml`。

### 3. 后台管理端启动
```bash
cd campus-pickup-admin
npm install
npm run dev
```
> 默认端口 80，接口代理到 `http://localhost:8080`。

### 4. 小程序端
用微信开发者工具导入 `campus-pickup-miniapp` 目录，在 `app.js` 中确认 `baseUrl` 指向后端地址。

## 六、默认账号

| 账号 | 密码 | 说明 |
| --- | --- | --- |
| admin | admin123 | 超级管理员 |
| student01 | 123456 | 示例学生 |

> 数据库初始化与示例数据见 `campus-pickup-server/sql/` 目录下的 SQL 文件。
