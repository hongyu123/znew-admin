-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: znew
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_advice`
--

DROP TABLE IF EXISTS `app_advice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_advice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `category` varchar(50) NOT NULL COMMENT '问题分类',
  `picture` varchar(200) DEFAULT NULL COMMENT '图片',
  `content` varchar(500) NOT NULL COMMENT '建议内容',
  `phone` char(11) DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app建议反馈';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_advice`
--

INSERT INTO `app_advice` VALUES (8,NULL,'产品建议','http://dummyimage.com/400x400','voluptate est','18175670489','2023-01-12 17:16:50',1),(9,7,'问题反馈','http://dummyimage.com/400x400','voluptate est','18175670489','2023-01-12 17:25:28',1);

--
-- Table structure for table `app_article`
--

DROP TABLE IF EXISTS `app_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '文章类型',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
  `content_type` tinyint NOT NULL COMMENT '内容类型(图文/超链接)',
  `content_id` bigint NOT NULL DEFAULT '0' COMMENT '图文详情',
  `link_url` varchar(255) DEFAULT NULL COMMENT '超链接',
  `location` varchar(255) DEFAULT NULL COMMENT '文章位置(方便显示用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_article`
--

INSERT INTO `app_article` VALUES (1,1,'用户协议','','',1,1,'',''),(2,1,'隐私政策','','',1,2,'',''),(3,1,'关于我们','','',1,3,'',''),(4,2,'1','http://127.0.0.1:8081/upload/202301/b4f49c5c-26f5-4ca1-9d15-d7d301ee0945.png','2',1,25,'','');

--
-- Table structure for table `app_banner`
--

DROP TABLE IF EXISTS `app_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '标题',
  `picture` varchar(255) NOT NULL COMMENT '图片',
  `type` tinyint NOT NULL COMMENT '类型',
  `link_url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `target_id` bigint DEFAULT NULL COMMENT '跳转目标id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app轮播图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_banner`
--

INSERT INTO `app_banner` VALUES (1,'无跳转','http://127.0.0.1:8081/upload/202301/9e9ad8ac-4e77-4203-93a6-84635d2a2e87.jpg',1,NULL,0,'2023-01-29 14:24:39','2023-01-29 14:24:59'),(2,'外部链接','http://127.0.0.1:8081/upload/202301/2d8a59e5-8d47-4784-93a2-03f1f0b3e676.jpg',2,'http://www.baidu.com',0,'2023-01-29 14:29:00','2023-01-29 14:33:01'),(3,'图文详情','http://127.0.0.1:8081/upload/202301/9ce6f9e6-453f-41c7-b811-ed153eafc56a.png',3,'',26,'2023-01-29 14:33:26','2023-01-29 14:33:34');

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `integral` int NOT NULL DEFAULT '0' COMMENT '积分',
  `balance` int NOT NULL DEFAULT '0' COMMENT '余额',
  `role` tinyint NOT NULL DEFAULT '1' COMMENT '角色',
  `enable_state` tinyint NOT NULL DEFAULT '1' COMMENT '启用状态',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

INSERT INTO `app_user` VALUES (5,'farkle','/123','12345678902',NULL,NULL,NULL,0,0,1,2,NULL),(6,'nickname','/headimgurl',NULL,1,NULL,NULL,0,0,1,1,NULL),(7,'郑敏','http://dummyimage.com/100x100','18112518645',2,'2010-04-03','湖北省新余市万荣县',21,7,1,1,'123');

--
-- Table structure for table `app_user_ext`
--

DROP TABLE IF EXISTS `app_user_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user_ext` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid',
  `unionid` varchar(255) DEFAULT NULL COMMENT '微信unionid',
  `appleid` varchar(255) DEFAULT NULL COMMENT '苹果登录id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app用户扩展信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user_ext`
--

INSERT INTO `app_user_ext` VALUES (1,6,'6',NULL,NULL),(2,7,NULL,NULL,'7');

--
-- Table structure for table `app_version`
--

DROP TABLE IF EXISTS `app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_version` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device` tinyint NOT NULL COMMENT '设备(android,ios)',
  `version` varchar(20) NOT NULL COMMENT '版本',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `download_url` varchar(200) DEFAULT NULL COMMENT 'apk下载url',
  `force_update` tinyint NOT NULL DEFAULT '0' COMMENT '是否强制更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app版本管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_version`
--

INSERT INTO `app_version` VALUES (1,1,'1','1','',0),(3,2,'3','3','',0),(5,2,'1.0','ios1.0',NULL,0),(6,1,'1','1','http://127.0.0.1:8081/upload/202301/d1b4b40b-c83c-4476-b1c5-60c1ab904b11.apk',1);

--
-- Table structure for table `sys_admin_log`
--

DROP TABLE IF EXISTS `sys_admin_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_admin_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `method` varchar(255) NOT NULL COMMENT '方法名',
  `account` varchar(50) NOT NULL COMMENT '管理员账号',
  `request_url` varchar(255) NOT NULL COMMENT '请求url',
  `request_ip` varchar(128) NOT NULL,
  `request_body` varchar(2000) NOT NULL COMMENT '请求体',
  `request_header` varchar(255) DEFAULT NULL COMMENT '请求头',
  `response` varchar(2000) DEFAULT NULL COMMENT '响应',
  `state` tinyint NOT NULL DEFAULT '1' COMMENT '状态(1正常,0异常)',
  `message` varchar(255) DEFAULT NULL COMMENT '异常信息',
  `times` bigint NOT NULL COMMENT '耗时',
  `request_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='admin日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin_log`
--

INSERT INTO `sys_admin_log` VALUES (1,'新增系统权限','com.hfw.admin.controller.sys.SysAuthController.save','admin','/sysAuth/save','127.0.0.1','[{\"authType\":2,\"authTypeDesc\":\"按钮\",\"cacheFlag\":1,\"frameFlag\":0,\"name\":\"新增\",\"parentId\":49,\"showFlag\":1,\"sort\":1,\"state\":0,\"webCode\":\"add\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,214,'2023-01-13 22:24:02'),(2,'新增系统权限','com.hfw.admin.controller.sys.SysAuthController.save','admin','/sysAuth/save','127.0.0.1','[{\"authType\":2,\"authTypeDesc\":\"按钮\",\"cacheFlag\":1,\"frameFlag\":0,\"name\":\"编辑\",\"parentId\":49,\"showFlag\":1,\"sort\":2,\"state\":0,\"webCode\":\"edit\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,8,'2023-01-13 22:24:19'),(3,'新增系统权限','com.hfw.admin.controller.sys.SysAuthController.save','admin','/sysAuth/save','127.0.0.1','[{\"authType\":2,\"authTypeDesc\":\"按钮\",\"cacheFlag\":1,\"frameFlag\":0,\"name\":\"删除\",\"parentId\":49,\"showFlag\":1,\"sort\":3,\"state\":0,\"webCode\":\"del\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,39,'2023-01-13 22:28:44'),(4,'新增系统权限','com.hfw.admin.controller.sys.SysAuthController.save','admin','/sysAuth/save','127.0.0.1','[{\"authType\":0,\"authTypeDesc\":\"菜单\",\"cacheFlag\":1,\"frameFlag\":0,\"name\":\"查看\",\"parentId\":49,\"showFlag\":1,\"sort\":4,\"state\":0,\"webCode\":\"detail\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,208,'2023-01-13 22:32:31'),(5,'编辑系统权限','com.hfw.admin.controller.sys.SysAuthController.edit','admin','/sysAuth/edit','127.0.0.1','[{\"authType\":0,\"authTypeDesc\":\"菜单\",\"cacheFlag\":1,\"component\":\"/sys/sysDemo/index\",\"createTime\":\"2023-01-04 10:25:00\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":49,\"name\":\"增删改(代码生成)demo\",\"parentId\":48,\"path\":\"/test/demo\",\"remark\":\"\",\"showFlag\":1,\"sort\":1,\"state\":0,\"updator\":\"\",\"webCode\":\"demo\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,18,'2023-01-13 22:33:27'),(6,'新增系统角色','com.hfw.admin.controller.sys.SysRoleController.save','admin','/sysRole/save','127.0.0.1','[{\"authList\":[{\"authType\":0,\"authTypeDesc\":\"菜单\",\"cacheFlag\":1,\"component\":\"\",\"createTime\":\"2023-01-04 10:23:46\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":48,\"name\":\"测试菜单\",\"parentId\":0,\"path\":\"/test\",\"remark\":\"\",\"showFlag\":1,\"sort\":20,\"state\":0,\"updateTime\":\"2023-01-04 10:24:01\",\"updator\":\"admin\",\"webCode\":\"testMenu\"},{\"authType\":0,\"authTypeDesc\":\"菜单\",\"cacheFlag\":1,\"component\":\"/sys/sysDemo/index\",\"createTime\":\"2023-01-04 10:25:00\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":49,\"name\":\"增删改(代码生成)demo\",\"parentId\":48,\"path\":\"/test/demo\",\"remark\":\"\",\"showFlag\":1,\"sort\":1,\"state\":0,\"updateTime\":\"2023-01-13 22:33:27\",\"updator\":\"admin\",\"webCode\":\"demo\"},{\"authType\":2,\"authTypeDesc\":\"按钮\",\"cacheFlag\":1,\"component\":\"\",\"createTime\":\"2023-01-13 22:24:02\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":54,\"name\":\"新增\",\"parentId\":49,\"path\":\"\",\"remark\":\"\",\"showFlag\":1,\"sort\":1,\"state\":0,\"updator\":\"\",\"webCode\":\"add\"},{\"authType\":2,\"authTypeDesc\":\"按钮\",\"cacheFlag\":1,\"component\":\"\",\"createTime\":\"2023-01-13 22:24:19\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":55,\"name\":\"编辑\",\"parentId\":49,\"path\":\"\",\"remark\":\"\",\"showFlag\":1,\"sort\":2,\"state\":0,\"updator\":\"\",\"webCode\":\"edit\"},{\"authType\":0,\"authTypeDesc\":\"菜单\",\"cacheFlag\":1,\"component\":\"\",\"createTime\":\"2023-01-13 22:32:31\",\"creator\":\"admin\",\"frameFlag\":0,\"frameUrl\":\"\",\"icon\":\"\",\"id\":57,\"name\":\"查看\",\"parentId\":49,\"path\":\"\",\"remark\":\"\",\"showFlag\":1,\"sort\":4,\"state\":0,\"updator\":\"\",\"webCode\":\"detail\"}],\"code\":\"test_auth\",\"name\":\"测试权限\",\"sort\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,60,'2023-01-13 22:38:47'),(7,'新增系统用户','com.hfw.admin.controller.sys.SysUserController.save','admin','/sysUser/save','127.0.0.1','[{\"account\":\"test\",\"roleList\":[5],\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,154,'2023-01-13 22:39:16'),(8,'编辑系统文章','com.hfw.admin.controller.sys.AppArticleController.edit','admin','/appArticle/edit','127.0.0.1','[{\"content\":\"<p>33333333333</p>\",\"contentId\":25,\"contentType\":0,\"id\":4,\"introduction\":\"2\",\"linkUrl\":\"\",\"location\":\"\",\"picture\":\"http://127.0.0.1:8081/upload/202301/b4f49c5c-26f5-4ca1-9d15-d7d301ee0945.png\",\"title\":\"1\",\"type\":1}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,4154,'2023-01-19 14:11:33'),(9,'编辑系统文章','com.hfw.admin.controller.sys.AppArticleController.edit','admin','/appArticle/edit','127.0.0.1','[{\"content\":\"<p>33333333333</p>\",\"contentId\":25,\"contentType\":0,\"id\":4,\"introduction\":\"2\",\"linkUrl\":\"\",\"location\":\"\",\"picture\":\"http://127.0.0.1:8081/upload/202301/b4f49c5c-26f5-4ca1-9d15-d7d301ee0945.png\",\"title\":\"1\",\"type\":1}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,10,'2023-01-19 14:12:35'),(10,'编辑系统文章','com.hfw.admin.controller.sys.AppArticleController.edit','admin','/appArticle','127.0.0.1','[{\"content\":\"<p>333333333334</p>\",\"contentId\":25,\"contentType\":0,\"id\":4,\"introduction\":\"2\",\"linkUrl\":\"\",\"location\":\"\",\"picture\":\"http://127.0.0.1:8081/upload/202301/b4f49c5c-26f5-4ca1-9d15-d7d301ee0945.png\",\"title\":\"1\",\"type\":1}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,177,'2023-01-26 14:39:27'),(11,'删除APP版本','com.hfw.admin.controller.sys.AppVersionController.del','admin','/appVersion/4','127.0.0.1','[4]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,144,'2023-01-26 14:48:30'),(12,'APP用户启用/禁用','com.hfw.admin.controller.AppUserController.state','admin','/appUser/state','127.0.0.1','[{\"enableState\":1,\"id\":5}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,164,'2023-01-26 14:51:11'),(13,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":52,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/93fafdfc-f5cd-4576-a631-2b85a9c37bda.png\"},{\"id\":53,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/3e943daf-e01d-446f-8dc3-3883eecb172a.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,88,'2023-01-26 15:20:58'),(14,'删除系统示例表','com.hfw.admin.controller.SysDemoController.del','admin','/sysDemo/1','127.0.0.1','[1]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,13,'2023-01-26 15:21:18'),(15,'批量删除系统示例表','com.hfw.admin.controller.SysDemoController.dels','admin','/sysDemo/dels','127.0.0.1','[[2,3]]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,7691,'2023-01-26 15:49:07'),(16,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,140,'2023-01-28 09:35:37'),(17,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,51,'2023-01-28 11:00:35'),(18,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,17,'2023-01-28 11:00:48'),(19,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,21,'2023-01-28 11:04:07'),(20,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,16,'2023-01-28 11:05:04'),(21,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"},{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,16,'2023-01-28 11:11:29'),(22,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":57,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"},{\"id\":56,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,229,'2023-01-28 11:13:49'),(23,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":58,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"},{\"id\":59,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,21,'2023-01-28 11:16:13'),(24,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"url\":\"http://127.0.0.1:8081/upload/202301/d93b6d8d-c2c5-4239-bbc7-06dc2ced3256.jpg\"},{\"id\":60,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"},{\"url\":\"http://127.0.0.1:8081/upload/202301/01299647-fc8f-4702-b1c7-4c487ab3e43f.jpg\"},{\"id\":61,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,51,'2023-01-28 11:17:51'),(25,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":65,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":63,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,17,'2023-01-28 12:11:46'),(26,'建议批量已读','com.hfw.admin.controller.sys.AppAdviceController.reads','admin','/appAdvice/reads','127.0.0.1','[[8]]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,9,'2023-01-28 12:14:00'),(27,'建议已读','com.hfw.admin.controller.sys.AppAdviceController.read','admin','/appAdvice/read/9','127.0.0.1','[9]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,10,'2023-01-28 12:18:12'),(28,'建议已读','com.hfw.admin.controller.sys.AppAdviceController.read','admin','/appAdvice/read/9','127.0.0.1','[9]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,5,'2023-01-28 12:18:34'),(29,'系统配置修改','com.hfw.admin.controller.sys.SysConfigController.sets','admin','/sysConfig','127.0.0.1','[{\"phone\":\"123456\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,38,'2023-01-30 12:33:53'),(30,'编辑系统示例表','com.hfw.admin.controller.SysDemoController.edit','admin','/sysDemo','127.0.0.1','[{\"age\":1,\"attachment\":\"http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt\",\"avatar\":\"http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png\",\"birth\":\"2023-01-06\",\"delete_flag\":0,\"detail\":\"<p><span style=\\\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\\\">详情</span></p>\",\"fileInput\":\"http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt\",\"gender\":0,\"id\":1,\"interest\":\"[\\\"1\\\",\\\"2\\\"]\",\"interestList\":[\"1\",\"2\"],\"introduction\":\"简介xxx\",\"lat\":24.927835,\"lng\":102.940364,\"location\":\"云南省昆明市呈贡区七甸街道白泥洞\",\"name\":\"zzzzz\",\"phone\":\"11111111111\",\"photos\":\"\",\"pictureList\":[{\"id\":66,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png\"},{\"id\":67,\"targetId\":1,\"type\":0,\"url\":\"http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png\"}],\"registTime\":\"2023-01-05 13:36:25\",\"score\":2,\"state\":0,\"video\":\"http://127.0.0.1:8081/upload/202301/4bc82364-2daa-4977-b982-25b1c67453d4.mp4\"}]',NULL,'{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}',1,NULL,146,'2023-01-30 12:54:06');

--
-- Table structure for table `sys_api_log`
--

DROP TABLE IF EXISTS `sys_api_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_api_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `method` varchar(255) NOT NULL COMMENT '方法名',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `request_url` varchar(255) NOT NULL COMMENT '请求url',
  `request_ip` varchar(128) NOT NULL,
  `request_body` varchar(2000) NOT NULL COMMENT '请求体',
  `request_header` varchar(255) DEFAULT NULL COMMENT '请求头',
  `response` varchar(2000) DEFAULT NULL COMMENT '响应',
  `state` tinyint NOT NULL DEFAULT '1' COMMENT '状态(1正常,0异常)',
  `message` varchar(255) DEFAULT NULL COMMENT '异常信息',
  `times` bigint NOT NULL COMMENT '耗时',
  `request_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='api日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_api_log`
--

INSERT INTO `sys_api_log` VALUES (1,'com.hfw.api.controller.AppUserController.userInfo',7,'/user/common/info','127.0.0.1','[]','601a17ed665a496d84747ee5366cb589','{\"code\":1,\"data\":{\"address\":\"湖北省新余市万荣县\",\"avatar\":\"http://dummyimage.com/100x100\",\"balance\":7,\"birth\":\"2010-04-03\",\"comment\":\"123\",\"enableState\":0,\"gender\":1,\"id\":7,\"integral\":21,\"nickname\":\"郑敏\",\"phone\":\"18112518645\",\"role\":1}}',1,NULL,173,'2023-01-13 23:37:59'),(2,'com.hfw.api.controller.UserIntegralController.list',7,'/user/integral/list','127.0.0.1','[0,10]','601a17ed665a496d84747ee5366cb589','{\"code\":1,\"data\":[{\"changeTime\":\"2023-01-13 21:57:42\",\"currentIntegral\":21,\"id\":6,\"integral\":11,\"orderId\":5,\"paymentsType\":0,\"source\":\"每日签到\",\"userId\":7},{\"changeTime\":\"2023-01-12 21:57:19\",\"currentIntegral\":10,\"id\":5,\"integral\":10,\"orderId\":4,\"paymentsType\":0,\"source\":\"每日签到\",\"userId\":7}]}',1,NULL,113,'2023-01-13 23:39:01'),(3,'com.hfw.api.controller.CommonController.appVersion',7,'/common/app/version','127.0.0.1','[\"android\",\"1\"]','601a17ed665a496d84747ee5366cb589','{\"code\":1,\"data\":{\"description\":\"1\",\"device\":0,\"downloadUrl\":\"http://127.0.0.1:8081/upload/202301/d1b4b40b-c83c-4476-b1c5-60c1ab904b11.apk\",\"forceUpdate\":1,\"hasUpdate\":0,\"id\":6,\"version\":\"1\"}}',1,NULL,2,'2023-01-13 23:39:53'),(4,'com.hfw.api.controller.CommonController.appVersion',NULL,'/common/app/version','127.0.0.1','[\"android\",\"1\"]',NULL,'{\"code\":1,\"data\":{\"description\":\"1\",\"device\":0,\"downloadUrl\":\"http://127.0.0.1:8081/upload/202301/d1b4b40b-c83c-4476-b1c5-60c1ab904b11.apk\",\"forceUpdate\":1,\"hasUpdate\":0,\"id\":6,\"version\":\"1\"}}',1,NULL,5,'2023-01-13 23:41:09');

--
-- Table structure for table `sys_auth`
--

DROP TABLE IF EXISTS `sys_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父id',
  `name` varchar(50) NOT NULL COMMENT '名称(菜单名)',
  `code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `web_code` varchar(50) NOT NULL COMMENT '前端权限编码(路由名/按钮名)',
  `auth_type` tinyint NOT NULL COMMENT '权限类型(菜单,目录,按钮,权限)',
  `sort` int DEFAULT NULL COMMENT '排序',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `frame_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否外链',
  `frame_url` varchar(255) DEFAULT NULL COMMENT '外链地址',
  `cache_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否缓存',
  `show_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否显示',
  `state` tinyint NOT NULL COMMENT '状态',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `contains_code` varchar(255) DEFAULT NULL COMMENT '包含权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_auth`
--

INSERT INTO `sys_auth` VALUES (1,0,'系统管理','','sys',2,1,'Platform','/sys','',0,'',1,1,1,'admin','2022-12-17 14:44:47','admin','2022-12-17 15:12:00','',''),(2,1,'用户管理','/sysUser/**','sysUser',1,1,'UserFilled','/sys/sysUser','/sys/sysUser/index',0,NULL,1,1,1,'admin','2022-12-17 14:52:17',NULL,NULL,NULL,NULL),(3,1,'角色管理','/sysRole/**','sysRole',1,10,'CollectionTag','/sys/sysRole','/sys/sysRole/index',0,'',1,1,1,'admin','2022-12-17 14:56:21','admin','2022-12-17 17:57:36','',''),(4,1,'权限管理','/sysAuth/**','sysAuth',1,20,'Unlock','/sys/sysAuth','/sys/sysAuth/index',0,'',1,1,1,'admin','2022-12-17 14:58:45','admin','2022-12-17 17:55:21','',''),(23,1,'系统日志','/sysAdminLog/**','sysAdminLog',1,30,'Operation','/sys/sysAdminLog','/sys/sysAdminLog/index',0,'',1,1,1,'admin','2022-12-17 21:39:57','admin','2022-12-19 18:20:04','',NULL),(24,1,'登录日志','/sysLoginLog/**','sysLoginLog',1,40,'Lock','/sys/sysLoginLog','/sys/sysLoginLog/index',0,'',1,1,1,'admin','2022-12-17 21:41:59','admin','2022-12-19 18:20:15','',NULL),(40,0,'APP管理',NULL,'app',2,10,'Apple',NULL,NULL,0,NULL,1,1,1,'admin','2022-12-20 09:42:33',NULL,NULL,NULL,NULL),(41,40,'系统文章','/appArticle/**','appArticle',1,1,NULL,'/app/appArticle','/sys/appArticle/index',0,NULL,1,1,1,'admin','2022-12-20 09:44:18',NULL,NULL,NULL,NULL),(42,40,'建议反馈','/appAdvice/**','appAdvice',1,10,NULL,'/app/appAdvice','/sys/appAdvice/index',0,NULL,1,1,1,'admin','2022-12-20 09:45:21',NULL,NULL,NULL,NULL),(43,40,'版本管理','/appVersion/**','appVersion',1,20,NULL,'/app/appVersion','/sys/appVersion/index',0,NULL,1,1,1,'admin','2022-12-20 09:46:01',NULL,NULL,NULL,NULL),(45,1,'角色授权用户','/sysRole/users','sysUserRole',1,11,'','/sys/sysUserRole/:roleId','/sys/sysRole/users',0,'',1,0,1,'admin','2022-12-28 15:10:23','admin','2022-12-28 15:30:12','',NULL),(46,1,'代码生成','/gen/**','codeGen',1,50,NULL,'/sys/gen','/sys/gen/index',0,NULL,1,1,1,'admin','2023-01-03 14:34:48',NULL,NULL,NULL,NULL),(47,1,'表单生成','/gen/**','formGen',1,60,'','/sys/formGen','/sys/gen/form',0,'',1,0,1,'admin','2023-01-03 14:35:29','admin','2023-01-05 21:55:47','',NULL),(48,0,'测试菜单',NULL,'testMenu',1,20,'','/test','',0,'',1,1,1,'admin','2023-01-04 10:23:46','admin','2023-01-04 10:24:01','',NULL),(49,48,'增删改(代码生成)demo','/sysDemo/page','demo',1,1,'','/test/demo','/sys/sysDemo/index',0,'',1,1,1,'admin','2023-01-04 10:25:00','admin','2023-01-13 22:33:27','',NULL),(50,1,'表单生成记录','/gen/**','formGenRecord',1,70,NULL,'/sys/formGenRecord','/sys/gen/list',0,NULL,1,1,1,'admin','2023-01-05 21:59:53',NULL,NULL,NULL,NULL),(51,48,'测试','/test/**','test',1,2,NULL,'/test/test','/test/index',0,NULL,1,1,1,'admin','2023-01-07 18:56:23',NULL,NULL,NULL,NULL),(52,40,'APP用户','/appUser/**','appUser',1,30,NULL,'/app/appUser','/appUser/index',0,NULL,1,1,1,'admin','2023-01-12 11:26:06',NULL,NULL,NULL,NULL),(53,1,'服务器信息','/monitor/**','serverInfo',1,21,'Monitor','/sys/serverInfo','/sys/monitor/server',0,NULL,1,1,1,'admin','2023-01-12 13:45:25',NULL,NULL,NULL,NULL),(54,49,'新增','/sysDemo/add','add',3,1,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:24:02',NULL,NULL,NULL,NULL),(55,49,'编辑','/sysDemo/edit','edit',3,2,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:24:19',NULL,NULL,NULL,NULL),(56,49,'删除','/sysDemo/del','del',3,3,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:28:44',NULL,NULL,NULL,'/sysDemo/dels'),(57,49,'查看','/sysDemo/detail','detail',3,4,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:32:31',NULL,NULL,NULL,NULL),(58,40,'系统配置','/sysConfig/**','sysConfig',1,40,NULL,'/app/sysConfig','/sys/sysConfig/edit',0,NULL,1,1,1,'admin','2023-01-29 10:52:47',NULL,NULL,NULL,NULL),(59,40,'轮播图管理','/appBanner/**','appBanner',1,50,NULL,'/app/appBanner','/sys/appBanner/index',0,NULL,1,1,1,'admin','2023-01-29 14:12:44',NULL,NULL,NULL,NULL);

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(200) NOT NULL,
  `value` varchar(255) NOT NULL,
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

INSERT INTO `sys_config` VALUES (1,'phone','123456',NULL);

--
-- Table structure for table `sys_content`
--

DROP TABLE IF EXISTS `sys_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_content` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL COMMENT '图文详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图文内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_content`
--

INSERT INTO `sys_content` VALUES (1,'<h1 style=\"text-align: center;\">“数据二十条”新政出台 促进数据赋能实体经济</h1><p>来源：央视网 | 2022年12月20日 10:36:53</p><p style=\"text-indent: 2em; text-align: start;\"><strong>央视网消息：</strong>近日，中共中央、国务院对外发布了《关于构建数据基础制度更好发挥数据要素作用的意见》，又称“数据二十条”。“数据二十条”提出构建数据产权、流通交易、收益分配、安全治理等制度，初步形成我国数据基础制度的“四梁八柱”。“数据二十条”的出台，有利于充分激活数据要素价值，赋能实体经济，推动高质量发展。</p><p style=\"text-indent: 2em; text-align: start;\">本次出台的“数据二十条”，构建了数据产权、流通交易、收益分配、安全治理等4项制度，共计20条政策措施。</p>'),(2,'<h2 style=\"text-indent: 2em; text-align: center;\"><strong>新华社北京12月19日电题：提振信心 笃定前行——广大干部群众畅谈学习贯彻中央经济工作会议精神</strong></h2><p style=\"text-indent: 2em; text-align: center;\">新华社记者刘开雄、刘羽佳</p><p style=\"text-indent: 2em; text-align: justify;\">连日来，全国各地、各行各业都在认真学习领会中央经济工作会议精神。广大干部群众表示，以习近平同志为核心的党中央关于经济工作的决策部署让大家对未来中国经济发展充满信心。要持续深入学习领会会议精神，纲举目张做好工作，将中央经济工作会议的各项部署落到实处。</p>'),(3,'<h1 style=\"text-align: center;\">学习时节丨心系澳门发展，总书记这些话直抵人心</h1><p><span style=\"color: rgb(130, 130, 130); font-size: 14px;\">2022-12-20 07:38</span> <span style=\"color: rgb(130, 130, 130); font-size: 14px;\">来源：南方网</span> </p><p style=\"text-align: left;\">　　党的十八大以来，习近平总书记一直心系澳门发展，殷殷寄语、深情勉励，为澳门保持繁荣稳定指明了前进方向、提供了不竭动力。今天（12月20日），是澳门回归祖国23周年纪念日。让我们一同重温总书记对澳门的殷切期许，感受直抵人心的力量。</p><p><br></p>'),(25,'<p>333333333334</p>');

--
-- Table structure for table `sys_demo`
--

DROP TABLE IF EXISTS `sys_demo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_demo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称(文本框)',
  `age` int DEFAULT NULL COMMENT '年龄(数字框)',
  `score` decimal(10,2) DEFAULT NULL COMMENT '分数(小数)',
  `gender` tinyint DEFAULT NULL COMMENT '性别(下拉框)',
  `state` tinyint NOT NULL DEFAULT '1' COMMENT '启用状态(单选框)',
  `interest` varchar(255) DEFAULT NULL COMMENT '兴趣(多选框)',
  `delete_flag` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `birth` date DEFAULT NULL COMMENT '生日(日期)',
  `regist_time` datetime NOT NULL COMMENT '注册时间(日期时间)',
  `avatar` varchar(200) NOT NULL COMMENT '头像(图片上传)',
  `video` varchar(200) DEFAULT NULL COMMENT '视频(视频上传)',
  `photos` varchar(200) DEFAULT NULL COMMENT '照片(多图删除)',
  `attachment` varchar(200) DEFAULT NULL COMMENT '附件(文件上传)',
  `introduction` varchar(255) NOT NULL COMMENT '简介(文本域)',
  `detail` varchar(500) DEFAULT NULL COMMENT '详情(富文本)',
  `phone` char(11) NOT NULL COMMENT '手机',
  `location` varchar(200) DEFAULT NULL COMMENT '地址',
  `lng` decimal(22,6) NOT NULL COMMENT '经度',
  `lat` decimal(22,6) NOT NULL COMMENT '纬度',
  `file_input` varchar(200) DEFAULT NULL COMMENT '文件输入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统示例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_demo`
--

INSERT INTO `sys_demo` VALUES (1,'zzzzz',1,2.00,1,0,'[\"1\",\"2\"]',0,'2023-01-06','2023-01-05 13:36:25','http://127.0.0.1:8081/upload/202301/2a5d6385-ff47-4500-aba7-30a3dfb58b5e.png','http://127.0.0.1:8081/upload/202301/4bc82364-2daa-4977-b982-25b1c67453d4.mp4','','http://127.0.0.1:8081/upload/202301/13e5d7e8-e7e7-4c5f-a0b4-a93385f4b354.txt','简介xxx','<p><span style=\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\">详情</span></p>','11111111111','云南省昆明市呈贡区七甸街道白泥洞',102.940364,24.927835,'http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt'),(2,'farkle',9,8.00,2,1,'[\"2\"]',1,'2023-01-11','2023-01-11 00:00:00','http://127.0.0.1:8081/upload/202301/61472aca-15d1-49a5-a04e-3174cf4a40e3.png',NULL,NULL,'http://127.0.0.1:8081/upload/202301/7195201d-6dfc-4640-91cf-a55e5516b117.txt','1','<p>2</p>','33333333333','云南省昆明市呈贡区七甸街道G78汕昆高速',102.885717,24.935197,'http://127.0.0.1:8081/upload/202301/51d6e555-ecda-4d4c-9c27-960afca8fc3b.txt'),(3,'fff',1,2.00,3,0,'[\"2\"]',1,'2023-02-01','2023-02-01 00:00:00','http://127.0.0.1:8081/upload/202301/5ed7fc91-03e9-40a7-8947-951d7e8b766c.png',NULL,NULL,'http://127.0.0.1:8081/upload/202301/75c992c6-a370-4b69-80f9-2e42f5fc2bcc.txt','1','<p>2</p>','33333333333','云南省昆明市官渡区太和街道站前路停车场(昆明铁路局计量所西)',102.719969,25.016006,'http://127.0.0.1:8081/upload/202301/a2c96972-188d-4c67-b97e-d5d27d4e79dc.txt');

--
-- Table structure for table `sys_gen_column`
--

DROP TABLE IF EXISTS `sys_gen_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_gen_column` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `table_name` varchar(100) NOT NULL COMMENT '表名',
  `column_name` varchar(100) NOT NULL COMMENT '字段名',
  `column_remark` varchar(200) DEFAULT NULL COMMENT '字段注释',
  `label` varchar(100) DEFAULT NULL COMMENT '显示标题',
  `property` varchar(100) NOT NULL COMMENT '属性名',
  `form_type` varchar(100) NOT NULL COMMENT '表单类型',
  `list_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否列表',
  `search_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否搜索',
  `required` tinyint NOT NULL COMMENT '是否必填',
  `maxlength` int DEFAULT NULL COMMENT '字符串长度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='表单生成字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_gen_column`
--

INSERT INTO `sys_gen_column` VALUES (72,'sys_demo','name','','名称','name','input',1,1,1,50),(73,'sys_demo','age','','年龄','age','number',0,0,0,0),(74,'sys_demo','score','','分数','score','number',0,0,0,0),(75,'sys_demo','gender','','性别','gender','select',1,1,0,0),(76,'sys_demo','state','','启用状态','state','radio',1,1,0,0),(77,'sys_demo','interest','','兴趣','interest','checkbox',0,0,0,255),(78,'sys_demo','birth','','生日','birth','date',1,1,0,0),(79,'sys_demo','regist_time','','注册时间','registTime','datetime',1,1,1,0),(80,'sys_demo','avatar','','头像','avatar','picture',1,0,1,200),(81,'sys_demo','photos','','照片','photos','pictures',0,0,0,200),(82,'sys_demo','attachment','','附件','attachment','file',0,0,0,200),(83,'sys_demo','file_input','','文件输入','fileInput','fileInput',0,0,0,0),(84,'sys_demo','introduction','','简介','introduction','textarea',0,0,1,255),(85,'sys_demo','detail','','详情','detail','richtext',0,0,0,500),(86,'sys_demo','phone','','手机','phone','phone',1,1,1,11),(87,'sys_demo','lat','','纬度','lat','number',0,0,1,0),(88,'sys_demo','lng','','经度','lng','number',0,0,1,0),(89,'sys_demo','location','','地址','location','map',1,0,0,200),(150,'app_article','type','','文章类型','type','select',1,1,1,0),(151,'app_article','title','','标题','title','input',1,1,1,255),(152,'app_article','picture','','图片','picture','picture',0,0,0,255),(153,'app_article','introduction','','简介','introduction','input',1,0,0,255),(154,'app_article','content_type','','内容类型','contentType','radio',0,0,1,0),(155,'app_article','content_id','','图文详情','contentId','richtext',0,0,1,0),(156,'app_article','link_url','','超链接','linkUrl','input',0,0,1,255),(157,'app_article','location','','文章位置','location','input',0,0,0,255),(163,'app_version','device','','设备','device','radio',1,1,1,0),(164,'app_version','version','','版本','version','input',1,1,1,20),(165,'app_version','description','','描述','description','input',1,0,1,255),(166,'app_version','download_url','','apk下载url','downloadUrl','input',1,0,1,200),(167,'app_version','force_update','','是否强制更新','forceUpdate','radio',1,1,1,0),(168,'app_user','nickname','','昵称','nickname','input',1,1,1,100),(169,'app_user','avatar','','头像','avatar','picture',1,0,0,200),(170,'app_user','phone','','手机号码','phone','phone',1,1,1,11),(171,'app_user','gender','','性别','gender','select',1,0,0,0),(172,'app_user','birth','','生日','birth','date',1,0,0,0),(173,'app_user','address','','地址','address','input',1,0,0,50),(174,'app_user','integral','','积分','integral','number',0,0,0,0),(175,'app_user','balance','','余额','balance','number',0,0,0,0),(176,'app_user','role','','角色','role','select',0,0,0,0),(177,'app_user','enable_state','','启用状态','enableState','select',1,1,1,0),(178,'app_user','comment','','备注','comment','input',1,0,0,255);

--
-- Table structure for table `sys_gen_table`
--

DROP TABLE IF EXISTS `sys_gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_gen_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `table_name` varchar(100) NOT NULL COMMENT '表名',
  `table_remark` varchar(200) DEFAULT NULL COMMENT '表注释',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='表单生成记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_gen_table`
--

INSERT INTO `sys_gen_table` VALUES (1,'sys_demo','系统示例表'),(7,'app_article','app文章'),(8,'app_version','app版本管理'),(9,'app_version','app版本管理'),(10,'app_user','app用户');

--
-- Table structure for table `sys_login_log`
--

DROP TABLE IF EXISTS `sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `ip` varchar(128) NOT NULL DEFAULT '' COMMENT '登录IP',
  `location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `message` varchar(255) NOT NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `state` tinyint NOT NULL DEFAULT '1' COMMENT '状态(1成功0失败)',
  `online_flag` tinyint NOT NULL DEFAULT '1' COMMENT '在线状态',
  `logout_type` tinyint DEFAULT NULL COMMENT '登出类型',
  `logout_time` datetime DEFAULT NULL COMMENT '登出时间',
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

INSERT INTO `sys_login_log` VALUES (1,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-13 22:17:37',1,0,1,NULL,'755de2d068f54e55957df6a5b8e5ec83'),(2,'test','127.0.0.1','本地局域网','搜狗Chrome 80','Windows 10','登录成功','2023-01-13 22:40:22',1,0,3,'2023-01-13 22:53:05','c5cc6d34b87941cf862429af33cab769'),(3,'test','127.0.0.1','本地局域网','搜狗Chrome 80','Windows 10','登录成功','2023-01-13 22:48:35',1,0,1,NULL,'7e68fbb5614b4e9da85e82d804593051'),(4,'test','127.0.0.1','本地局域网','搜狗Chrome 80','Windows 10','登录成功','2023-01-13 22:53:05',1,0,1,NULL,'bfc9d25caa144e1c9012d86187d69c68'),(5,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-13 23:46:40',1,0,1,NULL,'d166799226dd48e593c7a4a6356474ef'),(6,'','127.0.0.1','本地局域网','搜狗Chrome 80','Windows 10','密码错误','2023-01-14 00:24:28',0,0,1,NULL,NULL),(7,'test','127.0.0.1','本地局域网','搜狗Chrome 80','Windows 10','登录成功','2023-01-14 00:24:33',1,0,1,NULL,'5a3783f3865640a1b6c6272b4d789921'),(8,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-13 20:01:41',1,0,1,NULL,'006f1a9c9a0c48a092732e71f2e6e7da'),(9,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-13 22:23:47',1,0,1,NULL,'ef3650733e9249a8833b5f5b0cb7e9ff'),(10,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-13 23:34:42',1,0,1,NULL,'72ffed455b584ea2b174b957faa0b288'),(11,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-19 14:02:35',1,0,1,NULL,'d3097f805b454d52a1ba4f71e7b0e369'),(12,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-19 14:13:28',1,0,1,NULL,'57a21073582849e193b32b708c72b5cc'),(13,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-26 12:41:22',1,0,1,NULL,'8cfb32f534dc47158689eccacf8c303a'),(14,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-26 13:17:07',1,0,1,NULL,'c5f2b06b442a47499b518b4c58112831'),(15,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-26 13:28:11',1,1,NULL,NULL,'baba1641780044309491aa74544800ca'),(16,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-26 15:48:50',1,1,NULL,NULL,'aff7cf3c90474ec6863381adf3aba78b'),(17,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-28 09:02:55',1,1,NULL,NULL,'4f8ff051c5a74339a493f0dca81f6844'),(18,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-28 12:07:32',1,1,NULL,NULL,'38e12d6fbe5c45218c4310d71eb033a7'),(19,'admin','127.0.0.1','本地局域网','Chrome 10','Windows 10','登录成功','2023-01-30 12:27:12',1,1,NULL,NULL,'fd3b5e825332429a8ef5ca9e0566cc16');

--
-- Table structure for table `sys_picture`
--

DROP TABLE IF EXISTS `sys_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_picture` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL COMMENT '作品,纹身师,',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片url',
  `target_id` bigint NOT NULL COMMENT '目标id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_picture`
--

INSERT INTO `sys_picture` VALUES (68,-1,'http://127.0.0.1:8081/upload/202301/14dad9c7-940d-42e7-8d62-fe6bcfa3c36c.png',1),(69,-1,'http://127.0.0.1:8081/upload/202301/966ab6b9-a7e3-4716-9499-f022152c7a08.png',1);

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `sort` int DEFAULT NULL COMMENT '排序',
  `state` tinyint NOT NULL COMMENT '状态',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否系统内置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

INSERT INTO `sys_role` VALUES (1,'管理员','admin',1,1,'admin','2022-12-17 15:39:13',NULL,NULL,NULL,0),(5,'测试权限','test_auth',2,1,'admin','2023-01-13 22:38:47',NULL,NULL,NULL,0);

--
-- Table structure for table `sys_role_auth`
--

DROP TABLE IF EXISTS `sys_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `auth_id` bigint NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色-权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_auth`
--

INSERT INTO `sys_role_auth` VALUES (1,5,48),(2,5,49),(3,5,54),(4,5,55),(5,5,57);

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `state` tinyint NOT NULL COMMENT '状态',
  `system_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否系统内置',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$IPnALJm4UjWekCNwlwNRq.lZSbjVS3YjH2gloSZVwr9aPq5g2OVAC','','系统管理员','','',1,1,1,'admin','2022-12-17 15:44:57','admin','2022-12-19 16:50:55',''),(5,'test','$2a$10$ZCkxAyNnFAhP0Ulx5wQZ4e3WxQg61VvJHRGBEtjIaL/T8tsz1pzAO',NULL,NULL,NULL,NULL,NULL,1,0,'admin','2023-01-13 22:39:16',NULL,NULL,NULL);

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户-角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

INSERT INTO `sys_user_role` VALUES (18,5,5);

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(60) NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `total_amount` int NOT NULL COMMENT '总价(单位分)',
  `discount_amount` int NOT NULL DEFAULT '0' COMMENT '优惠金额(单位分)',
  `pay_amount` int DEFAULT NULL COMMENT '支付金额(单位分)',
  `state` tinyint NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_way` tinyint NOT NULL COMMENT '支付方式',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uindex_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--


--
-- Table structure for table `t_order_address`
--

DROP TABLE IF EXISTS `t_order_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL COMMENT '订单id',
  `order_no` varchar(60) NOT NULL COMMENT '订单号',
  `address_id` bigint DEFAULT NULL COMMENT '收货地址id',
  `contact_person` varchar(50) NOT NULL COMMENT '联系人',
  `contact_phone` char(11) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) NOT NULL COMMENT '收货地址',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单收货信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_address`
--


--
-- Table structure for table `t_order_item`
--

DROP TABLE IF EXISTS `t_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL COMMENT '订单id',
  `order_no` varchar(60) NOT NULL COMMENT '订单号',
  `goods_id` bigint DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `goods_picture` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `goods_price` int NOT NULL COMMENT '商品单价(单位分)',
  `goods_num` int NOT NULL COMMENT '商品数量',
  `total_price` int NOT NULL COMMENT '总价(单位分)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单项';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_item`
--


--
-- Table structure for table `user_balance`
--

DROP TABLE IF EXISTS `user_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_balance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `balance` int NOT NULL COMMENT '余额',
  `payments_type` tinyint NOT NULL COMMENT '收支类型',
  `change_time` datetime NOT NULL COMMENT '时间',
  `current_balance` int NOT NULL COMMENT '当前余额',
  `source` varchar(50) NOT NULL COMMENT '收入支出来源',
  `order_id` bigint DEFAULT NULL COMMENT '来源订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户余额明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_balance`
--

INSERT INTO `user_balance` VALUES (2,7,10,1,'2023-01-14 21:53:47',10,'充值',0),(3,7,3,2,'2023-01-14 21:54:28',7,'余额支付',0);

--
-- Table structure for table `user_integral`
--

DROP TABLE IF EXISTS `user_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_integral` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `integral` int NOT NULL COMMENT '积分',
  `payments_type` tinyint NOT NULL COMMENT '收支类型',
  `change_time` datetime NOT NULL COMMENT '时间',
  `current_integral` int NOT NULL COMMENT '当前积分',
  `source` varchar(50) NOT NULL COMMENT '收入支出来源',
  `order_id` bigint DEFAULT NULL COMMENT '来源订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户积分明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_integral`
--

INSERT INTO `user_integral` VALUES (5,7,10,1,'2023-01-12 21:57:19',10,'每日签到',4),(6,7,11,1,'2023-01-13 21:57:42',21,'每日签到',5);

--
-- Table structure for table `user_signin`
--

DROP TABLE IF EXISTS `user_signin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_signin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `signin_date` date NOT NULL COMMENT '签到日期',
  `signin_time` datetime NOT NULL COMMENT '签到时间',
  `integral` int NOT NULL COMMENT '积分',
  `continue_days` int NOT NULL COMMENT '连续签到天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-签到明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_signin`
--

INSERT INTO `user_signin` VALUES (4,7,'2023-01-12','2023-01-12 21:57:19',10,1),(5,7,'2023-01-13','2023-01-13 21:57:42',11,2);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-30 13:43:15
