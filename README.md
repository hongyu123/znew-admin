# 后端管理页面

### [Geeker-Admin](https://gitee.com/laramie/Geeker-Admin) ,在此特别感谢大佬!

# 接口测试

## 采用 [Apifox](https://www.apifox.cn/help/ide-plugin/idea-plugin/quickstart/installation/) + 规范注释 + IDEA插件,在此特别感谢[Apifox](https://www.apifox.cn/)团队!

 一键生成接口文档,简直不要太好用! 

# 项目简介

力求简单,好用,能够快速开发.
新的一年希望有新气象,所以项目名就叫znew.

# 数据库访问

基于mybatis,封装了一个通用实体类增删改查的工具类,也是本项目特色.

设计理念: 万能实体类的增删改查. 不考虑复杂sql,关联查询等,推荐用原生mybatis配置文件的方式.

## CommonDao使用

```java
/**
 * CommonDao使用示例
 * @author farkle
 * @date 2023-01-13
 */
@Service
public class CommonDaoDemo {
    @Autowired
    private CommonDao commonDao;

    //主键查找
    public SysDemo findByPk(Long id){
        return commonDao.findByPk(SysDemo.class, id);
    }
    //查询所有数据
    public List<SysDemo> findALl(){
        return commonDao.findALl(SysDemo.class);
    }
    //查询一条数据,推荐
    public SysDemo findByName(String name){
        return commonDao.findOne(new SysDemo().setName(name));
    }
    //查询一条数据2
    public SysDemo findByName2(String name){
        return commonDao.findOneCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    //列表查询,推荐
    public List<SysDemo> list(String name){
        return commonDao.list(new SysDemo().setName(name));
    }
    //列表查询2
    public List<SysDemo> list2(String name){
        return commonDao.listCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    /**
     * 分页查询,推荐
     * 这里只是简单分页,面对复杂查询的分页推荐使用pagehelper
     * @param name
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<SysDemo> listPage(String name, Integer pageNumber, Integer pageSize){
        return commonDao.list((SysDemo)new SysDemo().setName(name).setSortByField("id").setSortByWay(SortByWay.desc), pageNumber,pageSize);
    }
    //分页查询2
    public List<SysDemo> listPage2(String name, Integer pageNumber, Integer pageSize){
        return commonDao.listCond(Condition.create(SysDemo.class).put(SysDemo::getName, name), pageNumber,pageSize);
    }
    //统计,推荐
    public Long count(String name){
        return commonDao.count(new SysDemo().setName(name));
    }
    //统计2
    public Long count2(String name){
        return commonDao.countCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    //带统计数量的分页,推荐
    public PageResult<SysDemo> page(String name, Integer pageNumber, Integer pageSize){
        PageResult<SysDemo> pageResult = commonDao.page((SysDemo) new SysDemo().setName(name).setSortByField("id").setSortByWay(SortByWay.desc), pageNumber, pageSize);
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getData());
        return pageResult;
    }
    //带统计数量的分页2
    public PageResult<SysDemo> page2(String name, Integer pageNumber, Integer pageSize){
        PageResult<SysDemo> pageResult = commonDao.pageCond(Condition.create(SysDemo.class).put(SysDemo::getName, name), pageNumber,pageSize);
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getData());
        return pageResult;
    }
    //新增
    public int insert(){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setName("小明");
        return commonDao.insert(sysDemo);
    }
    //批量新增
    public int insertBatch(){
        List<SysDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SysDemo sysDemo = new SysDemo();
            sysDemo.setName("小明"+i);
            list.add(sysDemo);
        }
        return commonDao.insertBatch(list);
    }
    //主键更新
    public int updateByPk(Long id){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setId(id);
        sysDemo.setName("小明名称变更");
        return commonDao.updateByPk(sysDemo);
    }
    //updateAllField 根据主键根性所有字段

    //条件更新
    public int update(){
        SysDemo update = new SysDemo();
        update.setName("所有男的都叫小明");
        return commonDao.update(update, new SysDemo().setGender(Gender.Male));
    }
    //主键删除
    public int deleteByPk(Long id){
        return commonDao.deleteByPk(SysDemo.class, id);
    }
    //主键批量删除
    public int deleteBatch(List<Long> ids){
        return commonDao.deleteBatch(SysDemo.class, ids);
    }
    //条件删除
    public int delete(){
        //删除所有男性
        return commonDao.delete(new SysDemo().setGender(Gender.Male));
    }
}
```

CommonService:通用增删改服务

```java
/**
 * 通用服务
 * @author farkle
 * @date 2022-12-08
 */
public interface CommonService<T> {
    List<T> list(T t);
    T detail(Class<T> clazz, Long id);
    Long count(T t);
    int save(T t);
    int edit(T t);
    int update(T t, T cond);
    int del(Class<T> clazz, Long id);
    int dels(Class<T> clazz, List<Long> ids);
}

@Service
public class CommonServiceImpl<T> implements CommonService<T> {
    @Autowired
    private CommonDao commonDao;

    public List<T> list(T t){
        return commonDao.list(t);
    }
    public T detail(Class<T> clazz, Long id){
        return commonDao.findByPk(clazz, id);
    }
    public Long count(T t){
        return commonDao.count(t);
    }
    public int save(T t){
        return commonDao.insert(t);
    }
    public int edit(T t){
        return commonDao.updateByPk(t);
    }
    public int update(T t, T cond){
        return  commonDao.update(t,cond);
    }
    public int del(Class<T> clazz, Long id){
        return commonDao.deleteByPk(clazz, id);
    }
    public int dels(Class<T> clazz, List<Long> ids){
        return commonDao.deleteBatch(clazz, ids);
    }
}
```

# znew多模块架构

```
znew
├─ common                 # 通用工具支持
│  ├─ lombok, servlet-api
│  ├─ jackson, FastJson
│  ├─ commons-io, commons-lang3, httpclient
│  ├─ jjwt
├─ model                  # 通用业务实体类
├─ basesystem			  # 后台管理基础内容以及公共服务
│  ├─ common              
│  ├─ spring-boot-starter, spring-boot-starter-web
│  ├─ redis, validation, mybatis
│  ├─ freemarker(代码生成器), Retrofit, swagger
├─ admin		          # 后台业务管理
│  ├─ basesystem, model, plugin    
│  ├─ security, pagehelper
├─ api		          	  # app业务接口
│  ├─ basesystem, model, plugin    
│  ├─ Freemarker(文章h5页面)
├─ plugin		          # 通用第三方插件
│  ├─ common   
│  ├─ 微信授权登录,微信支付
│  ├─ 支付宝支付
│  ├─ 云存储 等
└─ 
```

# 后端管理页面权限控制

![image-20230113200504510](https://oscimg.oschina.net/oscnet/up-98dc0f77b993ff92d108d35109e6fcfb308.png)

参考增删改demo, 注意:权限编码和Controlelr是一一对应的,前端被屏蔽了,需要去数据库查看.实现方式自行查看**Authorization**类.

# 代码生成

![image-20230113201820971](https://oscimg.oschina.net/oscnet/up-3c89e92f41f34755b35a5f872bbb6a1b756.png)

生成代码预览

![image-20230113201859476](https://oscimg.oschina.net/oscnet/up-285239ad3008c3d3aa8c6c7e5840a90cc41.png)

可拖拽排序的表单配置, 以及常用的表单字段类型

![image-20230113202149469](https://oscimg.oschina.net/oscnet/up-790d7bccdbc7d3115c73907dd5bc0f08ab6.png)

# 接口规范

```
请求方法
	提交数据推荐用 POST 方法
	获取数据用 GET 方法
Response Headers(响应头设置)
	Content-Type: application/json;charset=utf-8
请求参数
	POST请求 用@RequestBody 接收(json格式,请求头设置:Content-Type: application/json;charset=UTF-8)
	后端必须做参数校验
文档
	请求参数，返回实体 必须有对应的说明
	特殊code 必须有对应的说明
状态类型标识的字段
	推荐用枚举,见名知意(看见对应的单词就知道代表的状态)
		disable(禁用) enable(启用)
	标识是否状态的字段是推荐用1(是)0(否)标识
接口变动
	方案一：做文件记录对应的接口变动
	方案二：接口加版版本区分
	方案三：...
接口日志
	建议做接口日志，方便排查问题

响应
	1.后端不允许直接报错给前端,必须经过错误处理，返回code=0
	2.返回的json数据不允许出现 null 类型，如果是空类型请按下列返回相应的"空数据"
		空数组请返回： []
		空字符串请返回： ""
		空数值请返回：0
		金额格式: 两位小数返回，为空返回 0
		日期格式: 转成对应的字符格式返回，为空返回 ""

	3.不推荐用复杂的数据类型返回（复杂类型,最多不超过3层）
		列子(不推荐):
			{
				"name": "教师zyh",
				"no": "教师001",
				"student": {
					"name": "学生zyh",
					"no": "学生001"
				}
			}
		错误返回示例：
			{
				"name": "教师zyh",
				"no": "教师001",
				"student": null
			}
		推荐返回：
			 {
				"name": "教师zyh",
				"no": "教师001",
				"studentName": "学生zyh",
				"studentNo": "学生001"
			}
			{
				"name": "教师zyh",
				"no": "教师001",
				"studentName": "",
				"studentNo": ""
			}

标准成功相应格式
	{
		"code": 1,
		"data":"",
		"message": ""
	}
标准失败响应格式
	{
		"code": 0,
		"data":"",
		"message": ""		
	}
标准带数据相应格式
	字符数据
		{
			"code": 1,
			"data": "",
			"message": ""
		}
	对象数据
		{
			"code": 1,
			"data": {},
			"message": ""
		}
	数组数据
		{
			"code": 1,
			"data": [],
			"message": ""
		}
	带有分页信息的数据,前端分页参数固定接收 pageNumber(第几页,默认值1) pageSize(每页数量,默认值10)
		{
			"code": 1,
			"data": [],
			"message": "",
			"total": 0    //总条数
			"pageNumber":1 //当前页
			"pageSize":10  //每页数量
		}
```



# 项目截图

![](https://oscimg.oschina.net/oscnet/up-20857fbf71fb1799231f658e0f5d9dd818e.png)

![](https://oscimg.oschina.net/oscnet/up-79681991122b288b5e29bc3b27900768af1.png)

![](https://oscimg.oschina.net/oscnet/up-27fe458984a34cca72d9512f678ecfa1932.png)

![](https://oscimg.oschina.net/oscnet/up-c6097ffd28dbc4898b5bda93493791773dc.png)

![](https://oscimg.oschina.net/oscnet/up-1f24926296e1a56b167d73de8091722c2e4.png)

![](https://oscimg.oschina.net/oscnet/up-69f5c6a7bb1b2ad365db254a036658e103d.png)

![](https://oscimg.oschina.net/oscnet/up-ffc061a2809f7cda925b4e3834cb7031d4c.png)

# TODO

1. CommonDao 更新多数据源,字段自动填充属性,更新策略等高级功能

2. 升级至Springboot3

3. 后台管理列表通用导入,导出功能

4. 集成工作流

5. 推出 spring cloud alibaba 微服务版本

