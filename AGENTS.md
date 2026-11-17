# znew-admin

SpringBoot 3.4.3(JDK 17)多模块 Maven 后端 + Vue3 前端(Geeker-Admin)组成的后台管理系统。数据库 PostgreSQL 15。持久层、认证、响应格式均为**自研封装**,与常见框架默认行为差异较大,写代码前先看本文件。

## 仓库布局

- `model/` — PO 实体、自研 MyBatis 通用 Mapper 封装、Redis/Jackson/Excel/工具类基础设施
- `service/` — 业务 Service 与 Mapper;包 `sys` 为系统内置模块,新业务代码放 `znew` 包
- `admin/` — Controller、启动类 `AdminApplication`、Spring 配置;端口 8080
- `Geeker-Admin/` — Vue3 + Vite + TS + Element Plus 前端,独立 package.json,用 **pnpm**
- `deployment/znew.sql` — PostgreSQL 建表脚本

依赖方向:`model` ← `service` ← `admin`。


## 后端关键约定(易踩坑)

1. **Mapper XML 放在 `src/main/java` 里**(与 Mapper 接口同目录),不放 resources。pom 已特殊配置把 java 目录下的 `*.xml` 打包。扫描路径:`classpath*:com/hfw/service/**/*Mapper.xml`。
2. **持久层是自研通用 Mapper,不是 MyBatis-Plus**(没有 LambdaQueryWrapper):
   - 常用表:`interface XxxMapper extends BaseMapper<Xxx>`
   - 非常用表:`SingleMapper`(方法第一个参数传 `Class`)
   - 复杂 SQL 才写 XML
   - 条件用实体内部 `COLUMN` 枚举构建:`Where.<SysDemo>where().eq(SysDemo.COLUMN.name, "x")`。`COLUMN` 枚举必须与实体属性一一对应(由代码生成器生成),不对应会在运行时抛 SQL 异常。
3. 获取当前登录用户:`LoginUser.getLoginUser()`。

## 前端(Geeker-Admin)

- 提交信息受 husky + commitlint(conventional 规范)约束。
- axios POST不使用请求参数params, 统一使用请求头 { "Content-Type": "application/x-www-form-urlencoded" }
- vue非通用组件,业务组件,业务页面不使用ts语言,统一使用lang="js"或lang="jsx", 页面和接口js文件放同一目录
- 枚举字段优先看src\enums\commonEnum.js, 其次使用src\api\modules\common.js的enums接口去后端请求, 最后都没有再自定义