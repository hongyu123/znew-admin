



znew多模块架构:
    common(通用工具支持)
    model(通用业务实体类)
    basesystem(后台管理基础内容以及公共服务)
    admin(后台业务管理)
    api(app业务接口)
    plugin(通用第三方插件,独立)
    demo(示列模块)
common:
    lombok, servlet-api
    jackson, FastJson
    commons-io, commons-lang3, httpclient
    jjwt
model:
basesystem:
    spring-boot-starter, spring-boot-starter-web
    redis, validation, mybatis
    freemarker(代码生成器), Retrofit, swagger
admin:
    security, pagehelper
api:
    Freemarker模板引擎(文章h5页面)
plugins:
    ftp,sftp
    七牛云
    苹果登录和支付校验
    微信开放平台access_token获取及获取用户信息

依赖关系
    admin->basesystem->common
         ->model
         ->plugin->common
    api->basesystem->common
        ->model
        ->plugin->common


接口测试:采用apifox
    关于swagger:swagger界面太丑,knife4j集成高版本bug太多
    关于apipost:据说免费版有各种限制

记录:
日志
    springboot 默认使用slfj->logback实现,默认debug级别
    mybatis 不指定的话也是默认使用slfj
    因此日志只用配置logging.level.包名=level 指定不同包的日志级别
实体类
    PO:持久层对象,和数据库字段一一对应,默认不允许修改PO对象的属性
    entity:实体类,可以继承po对象添加数据库没有的多余属性
    VO:视图对象,返回给前端用