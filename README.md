# 技术选型
### SpringBoot3
### JDK 17
### mysql 8
### 数据库访问: [xbatis](https://xbatis.cn/)
### 权限认证: [sa-token](https://sa-token.cc/)
### 前端框架选择: [vue-pure-admin](https://github.com/pure-admin/vue-pure-admin)
### 接口测试: 采用 [Apifox](https://www.apifox.cn/help/ide-plugin/idea-plugin/quickstart/installation/) + 规范注释 + IDEA插件

**在此特别感谢大佬们!!!**

# 项目简介
力求简单,好用,能够快速开发.

新的一年希望有新气象,所以项目名就叫znew.

# znew多模块架构
目录组织结构采用简易DDD设计
```
model
    持久化对象 工具类 数据库 MQ 等中间件依赖
service
    核心业务实现
admin
    后台管理系统接口 启动配置
```

# 数据权限/多租户过滤
采用mybatis插件+jsqlparser动态改写sql

mybatis插件实现: `DataScopeInterceptor`

jsqlparser动态改写sql核心实现
``` java
@Setter
public class SqlFromItemVisitor extends FromItemVisitorAdapter<Void> {
    private Set<String> tables; //要添加数据权限过滤的表名
    private String expression; //数据权限过滤的表达式 如:dept_id=0 and tenant_d=0
    public SqlFromItemVisitor(Set<String> tables, String expression){
        this.tables = tables;
        this.expression = expression;
    }

    @Override
    public <S> Void visit(Table table, S context) {
        String name = table.getName();
        if(!tables.contains(name)){
            return null;
        }
        Alias alias = table.getAlias();
        if(context instanceof PlainSelect plainSelect){
            String aliasName = alias!=null ?alias.getName()+"." :"";
            String cond = aliasName+expression;
            try {
                Expression where = plainSelect.getWhere();
                if(where==null){
                    plainSelect.setWhere(CCJSqlParserUtil.parseExpression(cond));
                }else{
                    AndExpression andExpression = new AndExpression();
                    andExpression.setLeftExpression(where);
                    andExpression.setRightExpression(CCJSqlParserUtil.parseExpression(cond));
                    plainSelect.setWhere(andExpression);
                }
            }catch (JSQLParserException e){
                DataScopeInterceptor.monitorLog.error("数据权限SQL表达式解析失败", e);
            }
        }
        return null;
    }

}
```

管理页面配置
![](https://oscimg.oschina.net/oscnet/up-cae7e740577e03bc5e652e4481c30bd16f6.png)

# 代码生成

![image-20230113201820971](https://oscimg.oschina.net/oscnet/up-3c89e92f41f34755b35a5f872bbb6a1b756.png)

生成代码预览

![image-20230113201859476](https://oscimg.oschina.net/oscnet/up-285239ad3008c3d3aa8c6c7e5840a90cc41.png)

可拖拽排序的表单配置, 以及常用的表单字段类型

![image-20230113202149469](https://oscimg.oschina.net/oscnet/up-790d7bccdbc7d3115c73907dd5bc0f08ab6.png)

# 规范

## 后端规范
- 后端接口强制要求使用`@AdminLog` `@SaCheckPermission`进行日志记录的操作权限校验
- `LoginUser.getLoginUser();` //获取当前登录用户
- `LoginUser.enableDataScope("sys_demo");` //开启数据权限隔离,参数为数据权限配置的key
- 后端允许使用 entity 和 dto(继承entity) 直接接口参数, 但是必须实现 saveFilter 和 updateFilter 进行不接收的字段过滤
- POST复杂请求参数强制使用`@RequestBody`
- 属性拷贝推荐用 org.springframework.cglib.beans.BeanCopier, 工具类`BeanCopierUtil`
- excel导入导出: 使用easyexcel, 建议使用监听器`PageReadExceptListener` `ReadAllListener`(处理过数据格式转换异常), 参考SysDemoController

## 接口规范

- 接收数据原则上不运行直接使用entity或entity的继承类
- 接收数据必须做数据校验, 实现用 @Validated
- 返回数据原则上用VO(视图对象)

- 接口规范文档

```
请求方法
	提交数据推荐用 POST 方法
	获取数据用 GET 方法
	喜欢用restful风格的小伙伴请无视上两句
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
![](https://oscimg.oschina.net/oscnet/up-e7e55c20e8025c3193c3789648589140196.png)

![](https://oscimg.oschina.net/oscnet/up-161b7f3ff5cc32ece719f3bd2e3d6b56576.png)

![](https://oscimg.oschina.net/oscnet/up-9447f84f25a87922630f74e225bc9198acd.png)

![](https://oscimg.oschina.net/oscnet/up-31b4f150d4307826c6dbfed267ee12d00f2.png)

![](https://oscimg.oschina.net/oscnet/up-6e03e0c24caf7497721e8359329b2f4b290.png)

![](https://oscimg.oschina.net/oscnet/up-85f08faa10db5b557a5f64eab1f523ab880.png)

![](https://oscimg.oschina.net/oscnet/up-ff1d0f3ad8a5c5f98a710335840bb8ce37a.png)

# TODO
1. 集成工作流
2. 推出 spring cloud alibaba 微服务版本

# 工具类
## redis
### 去重
``` java
String orderNo = "0001";
String lockKey = "lock:order:"+orderNo;
//1分钟内去重
if(redisUtil.setNxEx(lockKey,1, 1,TimeUnit.MINUTES)){
    System.out.println("执行业务逻辑...");
}
```

### redisson锁
``` java
String orderNo = "0001";
String lockKey = "lock:order:"+orderNo;
RLock lock = redissonClient.getLock(lockKey);
//等待锁时间5秒,锁过期时间10秒
if(lock.tryLock(5, 10, TimeUnit.SECONDS)) {
    try {
        System.out.println("执行业务逻辑...");
    }finally {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
```

## JAVA11 HttpClient
### GET
发送请求
``` java
String url = "http://127.0.0.1/test_get";
String res = HttpUtil.get(url);
System.out.println(res);
```

携带参数
``` java
String url = "http://127.0.0.1/test_get";
Map<String,String> params = ChainMap.<String>create().putVal("name", "new牛").putVal("type", "student");
String uri = url +"?"+ HttpUtil.formToBody(params,"UTF-8");
String res = HttpUtil.get(uri);
System.out.println(res);
```

下载文件
``` java
String url = "http://127.0.0.1/test_file/test.txt";
HttpResponse<byte[]> response = HttpUtil.init().get_byte(url);
Files.write(Path.of("C:\\tmp\\test.txt"),response.body());
```
### POST
发送JSON请求(application/json)
``` java
String url = "http://127.0.0.1/test_post_json";
String body = "{\"name\":\"new牛\"}";
String res = HttpUtil.postJson(url, body);
System.out.println(res);
```

表单提交(application/x-www-form-urlencoded)
``` java
String url = "http://127.0.0.1/test_post_form";
Map<String,String> params = ChainMap.<String>create().putVal("name", "new牛");
String res = HttpUtil.postForm(url, params, "UTF-8");
System.out.println(res);
```

文件上传(multipart/form-data)
``` java
Path path = Path.of("C:\\tmp\\test.txt");
byte[] bytes = Files.readAllBytes(path);
String url = "http://127.0.0.1/test_form_data";
//第一个参数:接受文件的变量名, 第二个参数:文件名
HttpUtil.FormData formData = new HttpUtil.FormData("file", path.getFileName().toString(), bytes);
//携带额外参数
formData.addParam("name","new牛");
String res = HttpUtil.formData(url, formData);
System.out.println(res);
```

body GZ压缩(byte上传)
``` java
String url = "http://127.0.0.1/test_post_byte";
String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <student name=\"new牛\"></student>";
byte[] bytes = Compress.gz(xml.getBytes(StandardCharsets.UTF_8));
String res = HttpUtil.init()
        .setHeader("Content-Type", "application/octet-stream")
        .post(url, HttpRequest.BodyPublishers.ofByteArray(bytes))
        .body();
System.out.println(res);
```

### 其它
携带请求头
``` java
String url = "http://127.0.0.1/test_login";
String body = "{\"name\":\"new牛\"}";
String token = UUID.randomUUID().toString();
String res = HttpUtil.init()
        .setHeader("Content-Type", "application/json")
        .setHeader("token", token)
        .post(url, body)
        .body();
System.out.println(res);
```

获取响应头
``` java
String url = "http://www.java1234.com";
HttpResponse<String> response = HttpUtil.init().get_str(url);
System.out.println(response.headers());
System.out.println(response.statusCode());
System.out.println(response.body());
```

请求重试
``` java
String url = "http://www.java1234.com/";
HttpResponse<String> response = HttpUtil.init()
        .retryCount(3) //IOException(ConnectException HttpTimeoutException 等)异常重试次数
        .retrySleep(1000) //重试sleep时间
        .get_str(url);
System.out.println(response.body());
```

代理
``` java
//如果您的jdk版本在Java 8 Update 111以上,需要增加以下代码,设置https允许隧道代理
System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "false");
System.setProperty("jdk.http.auth.proxying.disabledSchemes", "false");

String url = "http://www.java1234.com/";
ProxySelector proxySelector = new ProxySelector() {
    @Override
    public List<Proxy> select(URI uri) {
        InetSocketAddress socketAddress = new InetSocketAddress("61.158.175.38", 9002);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        return List.of(proxy);
    }
    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
    }
};
Authenticator authenticator = new Authenticator() {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("user", "password".toCharArray());
    }
};
HttpResponse<String> response = HttpUtil.init()
        .proxySelector(proxySelector) //IP代理
        //.authenticator(authenticator) //代理认证
        .get_str(url);
System.out.println(response.body());
```

## 压缩
### GZ
``` java
String s = """
        String s = "";
        byte[] gzBytes = Compress.gz(s.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = Compress.ungz(gzBytes);
        System.out.println(new String(bytes,StandardCharsets.UTF_8));
        """;
byte[] gzBytes = Compress.gz(s.getBytes(StandardCharsets.UTF_8)); //压缩
byte[] bytes = Compress.ungz(gzBytes); //解压缩
System.out.println(new String(bytes,StandardCharsets.UTF_8));
```

### ZIP
``` java
String s = """
        String s = "";
        byte[] gzBytes = Compress.gz(s.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = Compress.ungz(gzBytes);
        System.out.println(new String(bytes,StandardCharsets.UTF_8));
        """;
byte[] zipBytes = Compress.zip(s.getBytes(StandardCharsets.UTF_8),"code.txt"); //压缩
byte[] bytes = Compress.unzip(zipBytes); //解压缩
System.out.println(new String(bytes,StandardCharsets.UTF_8));
```

## 加密
### 签名(摘要)
防篡改
``` java
System.out.println(SignUtil.md5("123456"));
System.out.println(SignUtil.sha1("123456"));
System.out.println(SignUtil.sha256("123456"));
System.out.println(SignUtil.hmacSHA256("123456","123456"));
```

### 对称加密
加密解密的计算量小，速度快

秘钥暴露后没有安全性

AES
``` java
AesUtil aes = new AesUtil("AES/CBC/PKCS5Padding");
aes.setKey("1234567890123456");
aes.setIv(new byte[16]);
String ciphertext = aes.encrypt("abcdefghigklmnopqrstuvwxyz0123456789");
String plaintext = aes.decrypt(ciphertext);
System.out.println(ciphertext); //8Z3dZzqn05FmiuBLowExK0CAbs4TY2GorC2dDPVlsn/tP+VuJGePqIMv1uSaVErr
System.out.println(plaintext);
```
PKCS7Padding
``` java
/* BC provider, supporting PKCS7Padding 需要添加依赖
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk18on</artifactId>
</dependency>
 */
//Security.addProvider(new BouncyCastleProvider()); //AesUtil中已添加

AesUtil aes = new AesUtil("AES/ECB/PKCS7Padding");
aes.setProvider("BC");
aes.setKeys(Base64.getDecoder().decode("pyJxan+SxWU8sGiZYL9Nqw=="));
String ciphertext = aes.encrypt("1");
String plaintext = aes.decrypt(ciphertext);
System.out.println(ciphertext); //e2eXyCsfJ66lPHYb9gm44w==
System.out.println(plaintext);
```
国密SM4
``` java
AesUtil aes = new AesUtil("SM4/ECB/PKCS7Padding");
aes.setKeys(Base64.getDecoder().decode("pyJxan+SxWU8sGiZYL9Nqw=="));
String ciphertext = aes.encrypt("1");
String plaintext = aes.decrypt(ciphertext);
System.out.println(ciphertext); //vLVgeOFxJSY/H4wZhUBogA==
System.out.println(plaintext);
```

### 非对称加密
有公钥和私钥,安全性高

算法复杂,加密速度慢,只适合对少量数据进行加密

RSA
``` java
RsaUtil rsa = new RsaUtil();
String data = "KYUCCNABCDEFGHIJ";
rsa.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCftkekOWqsntfcAvJxIhlK3rGzg3IbkAL4Vw0Jh2SSWBbqKt9mlWIgQ2sR1Zsjo1KXJ7Y9fU5TU0VIQM7mHDzwztbWHV0XwXR2VGjk45utoBWXKxTiGpvmCjPB5JPp+GxlnHLb6dvVKOKbZ6MDLZbSwFDqJ4nHlVP42LIWwFKt9QIDAQAB");
rsa.setPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ+2R6Q5aqye19wC8nEiGUresbODchuQAvhXDQmHZJJYFuoq32aVYiBDaxHVmyOjUpcntj19TlNTRUhAzuYcPPDO1tYdXRfBdHZUaOTjm62gFZcrFOIam+YKM8Hkk+n4bGWcctvp29Uo4ptnowMtltLAUOoniceVU/jYshbAUq31AgMBAAECgYBKvVWHX6sw/uCLQAHOuaNWayKDnFaw2VLafnpGZErHRVvr14ZWDkAuUv5vCSQhqFOFEvtwiQw3aDd62YE5JCvL0I4D2LVNpEHSChuKg5qePl1mnZiCDfip0gMZyaMR1u74u/YlYXXRK41u1sdtSwksalWRvG6lFSfH0QHqUNZafQJBAPQ0HfXJNPufBcXG7B6Ga87xnPJ3KTNVE/6CQsixVOys8UAjXxdBX/EJ3RxBACWsRd/VMiIxJtxp4npcceoDup8CQQCnbVGri6cPhvk+NgVMOzaKQDUxr9jRr0/3PCuJ/nuezUnh3dZ1RZHt7V/T2U8tsRN84FobS8+CjwLr2uVQGuLrAkEA1Tdv9TsBdLTK8H0Xiitpk91nYFhkc7pT48LOFramZKM3XP3FN+PPpgAru1CRlVMOCzn1NN9fg9E7egSfPWiWFwJAB48xA8zPYy0V7dAklxeJU96oSDEHWhhPRPtrf9SEolvkfRU2DJ9ygkqYbAlAwPBgz9+VUewvV1a7rAh7GA3OtwJAKE8P3EmY64C5ggIhG3C/ceVtR2yEt2YwNMEBG0z2v1oM7n3KY+CXEnlIScDamXvEd8uV/9xI+8v/4HDIRqzzdA==");
String ciphertext = rsa.encryptByPublicKey(data);
System.out.println(ciphertext);
System.out.println(rsa.decryptByPrivateKey(ciphertext));
```

SM2
BouncyCastleProvider 实现
``` java
Sm2Util sm2 = new Sm2Util();
sm2.generateKey();
//sm2.setPublicKey("");
//sm2.setPrivateKey("");
//sm2.setMode(SM2Engine.Mode.C1C3C2);
System.out.println("公钥:"+sm2.getPublicKey());
System.out.println("私钥:"+sm2.getPrivateKey());
String data = sm2.encrypt("123456");
System.out.println(data);
System.out.println(sm2.decrypt(data));
```

防伪造
1. 对内容签名后
2. 用私钥加密签名

协商加密数据传输
1. 随机生成对称加密秘钥 
2. 用公钥对`对称加密秘钥`进行加密, 把`对称加密秘钥密文`传给服务器作为`会话加密传输的秘钥`
3. 接下来的会话都用`协商好的秘钥`进行对称加/解密
