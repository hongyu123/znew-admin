# znew-admin

SpringBoot 3.4.3(JDK 17)多模块 Maven 后端 + Vue3 前端(Geeker-Admin)组成的后台管理系统。数据库 PostgreSQL 15。持久层、认证、响应格式均为**自研封装**,与常见框架默认行为差异较大,写代码前先看本文件。

## 仓库布局

- `model/` — PO 实体、自研 MyBatis 通用 Mapper 封装、Redis/Jackson/Excel/工具类基础设施
- `service/` — 业务 Service 与 Mapper;包 `sys` 为系统内置模块,新业务代码放 `znew` 包
- `admin/` — Controller、启动类 `AdminApplication`、Spring 配置;端口 8080
- `Geeker-Admin/` — Vue3 + Vite + TS + Element Plus 前端,独立 package.json,用 **pnpm**
- `deployment/znew.sql` — PostgreSQL 建表脚本

依赖方向:`model` ← `service` ← `admin`。

## 常用命令

后端(仓库根目录):

```bash
mvn package                 # 构建全部模块,产出 admin/target/admin.jar(finalName 无版本号)
mvn -pl admin -am package   # 只构建 admin 及其依赖
```

启动:IDE 运行 `AdminApplication`,或运行打出的 jar。

前端(`Geeker-Admin/` 目录):

```bash
pnpm install
pnpm dev          # Vite 开发服务器
pnpm type:check   # vue-tsc 类型检查(单独跑)
pnpm build:pro    # 生产构建(脚本内已先跑 vue-tsc)
pnpm lint:eslint  # ESLint 修复;另有 lint:prettier / lint:stylelint
```

**没有自动化测试套件**:`admin` 模块 surefire 配置 `skip=true`,仓库里唯一的 "test"(`admin/src/test/java/Test.java`)是带 main 方法的临时调试类。不要用 `mvn test` 当验证手段。

## 后端关键约定(易踩坑)

1. **Mapper XML 放在 `src/main/java` 里**(与 Mapper 接口同目录),不放 resources。pom 已特殊配置把 java 目录下的 `*.xml` 打包。扫描路径:`classpath*:com/hfw/service/**/*Mapper.xml`。
2. **持久层是自研通用 Mapper,不是 MyBatis-Plus**(没有 LambdaQueryWrapper):
   - 常用表:`interface XxxMapper extends BaseMapper<Xxx>`
   - 非常用表:`SingleMapper`(方法第一个参数传 `Class`)
   - 复杂 SQL 才写 XML
   - 条件用实体内部 `COLUMN` 枚举构建:`Where.<SysDemo>where().eq(SysDemo.COLUMN.name, "x")`。`COLUMN` 枚举必须与实体属性一一对应(由代码生成器生成),不对应会在运行时抛 SQL 异常。
   - 实体注解 `@Table/@TableId/@TableField/@LogicDelete` 名字与 mybatis-plus 相同,但是自研实现,行为见根目录 README。
3. **响应码与常见约定相反**:`Result` 的 code=1 成功、code=0 失败。返回 JSON 禁止 null:空数组返回 `[]`、空串返回 `""`、空数值返回 `0`(NullSerializerModifier 已统一处理)。分页入参 `pageNumber`/`pageSize`(默认 1/10),分页返回 `PageResult`。
4. **认证是自研拦截器,sa-token 并未集成**(README 提到 sa-token 是过时信息:无依赖、注解全被注释,以代码为准)。实际鉴权走 `AuthInterceptor`(`MvcConfigurer` 注册,拦截 `/**`),token 放请求头或参数 `token`,白名单:`/login`、`/test`、`/upload/**`。写接口加 `@AdminLog("操作名")` 记录操作日志。
5. **数据权限**:`LoginUser.enableDataScope("sys_demo")` 开启(参数为数据权限配置的 key),由 `DataScopeInterceptor` + jsqlparser 动态改写 SQL。
6. **接口入参**:允许 DTO(继承 entity)直接接收,但必须实现 `saveFilter()`/`updateFilter()` 过滤不可接收字段;POST 复杂参数用 `@RequestBody` + `@Validated`;属性拷贝用 `BeanCopierUtil`。
7. **Excel** 用 fesod-sheet(easyexcel 的 Apache 孵化版),导入用监听器 `PageReadExceptListener`/`ReadAllListener`。完整参考实现:`admin/controller/sys/SysDemoController.java`(含分页、增删改查、导入导出、模板导出)。
10. 获取当前登录用户:`LoginUser.getLoginUser()`。全局异常处理在 `GlobalExceptionHandler`,后端不允许把原始异常直接抛给前端。

## 前端(Geeker-Admin)

- 提交信息受 husky + commitlint(conventional 规范)约束。
- axios POST不使用请求参数params, 统一使用请求头 { "Content-Type": "application/x-www-form-urlencoded" }
- vue非组件,业务页面统一使用js脚本, 页面和接口js文件放同一目录
- 枚举字段优先看src\enums\commonEnum.js, 其次使用src\api\modules\common.js的enums接口去后端请求, 最后都没有再自定义