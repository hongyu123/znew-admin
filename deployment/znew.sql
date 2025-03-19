-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: znew
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

LOCK TABLES `app_advice` WRITE;
/*!40000 ALTER TABLE `app_advice` DISABLE KEYS */;
INSERT INTO `app_advice` VALUES (8,NULL,'产品建议','http://dummyimage.com/400x400','voluptate est','18175670489','2023-01-12 17:16:50',1),(9,7,'问题反馈','http://dummyimage.com/400x400','voluptate est','18175670489','2023-01-12 17:25:28',1);
/*!40000 ALTER TABLE `app_advice` ENABLE KEYS */;
UNLOCK TABLES;

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
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app文章';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_article`
--

LOCK TABLES `app_article` WRITE;
/*!40000 ALTER TABLE `app_article` DISABLE KEYS */;
INSERT INTO `app_article` VALUES (1,1,'用户协议','','',1,1,'','','2023-02-03 08:39:59'),(2,1,'隐私政策','','',1,2,'','','2023-02-03 08:39:59'),(3,1,'关于我们','','',1,3,'','','2023-02-03 08:39:59'),(5,2,'1','http://127.0.0.1:8081/upload/202302/ad6999e5-4374-4849-ba20-27c21c832a7a.png','',1,26,'','','2023-02-11 17:31:17');
/*!40000 ALTER TABLE `app_article` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `app_banner` WRITE;
/*!40000 ALTER TABLE `app_banner` DISABLE KEYS */;
INSERT INTO `app_banner` VALUES (1,'无跳转','http://127.0.0.1:8081/upload/202301/9e9ad8ac-4e77-4203-93a6-84635d2a2e87.jpg',1,'',0,'2023-01-29 14:24:39','2023-02-11 17:33:38'),(2,'外部链接','http://127.0.0.1:8081/upload/202301/2d8a59e5-8d47-4784-93a2-03f1f0b3e676.jpg',2,'http://www.baidu.com',0,'2023-01-29 14:29:00','2023-01-29 14:33:01'),(3,'图文详情','http://127.0.0.1:8081/upload/202301/9ce6f9e6-453f-41c7-b811-ed153eafc56a.png',3,'',26,'2023-01-29 14:33:26','2023-01-29 14:33:34');
/*!40000 ALTER TABLE `app_banner` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (5,'farkle','/123','12345678902',NULL,NULL,NULL,0,0,1,2,NULL),(6,'nickname','/headimgurl',NULL,1,NULL,NULL,0,0,1,1,NULL),(7,'郑敏','http://dummyimage.com/100x100','18112518645',2,'2010-04-03','湖北省新余市万荣县',21,7,1,1,'123');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `app_user_ext` WRITE;
/*!40000 ALTER TABLE `app_user_ext` DISABLE KEYS */;
INSERT INTO `app_user_ext` VALUES (1,6,'6',NULL,NULL),(2,7,NULL,NULL,'7');
/*!40000 ALTER TABLE `app_user_ext` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `app_version` WRITE;
/*!40000 ALTER TABLE `app_version` DISABLE KEYS */;
INSERT INTO `app_version` VALUES (1,1,'1','1','',0),(5,2,'1.0','ios1.0',NULL,0),(6,1,'1','1','http://127.0.0.1:8081/upload/202301/d1b4b40b-c83c-4476-b1c5-60c1ab904b11.apk',1);
/*!40000 ALTER TABLE `app_version` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='admin日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin_log`
--

LOCK TABLES `sys_admin_log` WRITE;
/*!40000 ALTER TABLE `sys_admin_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_admin_log` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='api日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_api_log`
--

LOCK TABLES `sys_api_log` WRITE;
/*!40000 ALTER TABLE `sys_api_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_api_log` ENABLE KEYS */;
UNLOCK TABLES;

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
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_auth`
--

LOCK TABLES `sys_auth` WRITE;
/*!40000 ALTER TABLE `sys_auth` DISABLE KEYS */;
INSERT INTO `sys_auth` VALUES (1,0,'系统管理','','sys',2,1,'ep:platform','/sys',NULL,0,'',1,1,1,'admin','2022-12-17 14:44:47','admin','2025-03-18 23:52:51',''),(2,1,'用户管理','/sysUser/**','sysUser',1,1,'ep:user-filled','/sys/sysUser','/sys/sysUser/index',0,'',1,1,1,'admin','2022-12-17 14:52:17','admin','2025-03-18 23:58:11',''),(3,1,'角色管理','/sysRole/**','sysRole',1,10,'ep:collection-tag','/sys/sysRole','/sys/sysRole/index',0,'',1,1,1,'admin','2022-12-17 14:56:21','admin','2025-03-18 23:59:53',''),(4,1,'菜单权限','/sysAuth/**','sysAuth',1,20,'ep:unlock','/sys/sysAuth','/sys/sysAuth/index',0,'',1,1,1,'admin','2022-12-17 14:58:45','admin','2025-03-19 00:14:20',''),(23,1,'系统日志','/sysAdminLog/**','sysAdminLog',1,30,'ep:operation','/sys/sysAdminLog','/sys/sysAdminLog/index',0,'',1,1,1,'admin','2022-12-17 21:39:57','admin','2025-03-19 00:01:13',''),(24,1,'登录日志','/sysLoginLog/**','sysLoginLog',1,40,'ep:lock','/sys/sysLoginLog','/sys/sysLoginLog/index',0,'',1,1,1,'admin','2022-12-17 21:41:59','admin','2025-03-19 00:01:36',''),(40,0,'APP管理','','app',2,10,'ep:apple','/app',NULL,0,'',1,1,1,'admin','2022-12-20 09:42:33','admin','2025-03-19 00:02:33',''),(41,40,'系统文章','/appArticle/**','appArticle',1,1,NULL,'/app/appArticle','/sys/appArticle/index',0,NULL,1,1,1,'admin','2022-12-20 09:44:18',NULL,NULL,NULL),(42,40,'建议反馈','/appAdvice/**','appAdvice',1,10,NULL,'/app/appAdvice','/sys/appAdvice/index',0,NULL,1,1,1,'admin','2022-12-20 09:45:21',NULL,NULL,NULL),(43,40,'版本管理','/appVersion/**','appVersion',1,20,NULL,'/app/appVersion','/sys/appVersion/index',0,NULL,1,1,1,'admin','2022-12-20 09:46:01',NULL,NULL,NULL),(45,1,'角色授权用户','/sysRole/users','sysUserRole',1,11,NULL,'/sys/sysUserRole/:roleId','/sys/sysRole/users',0,'',1,0,1,'admin','2022-12-28 15:10:23','admin','2022-12-28 15:30:12',''),(46,1,'代码生成','/gen/**','codeGen',1,50,NULL,'/sys/gen','/sys/gen/index',0,NULL,1,1,1,'admin','2023-01-03 14:34:48',NULL,NULL,NULL),(47,1,'表单生成','/gen/**','formGen',1,60,NULL,'/sys/formGen','/sys/gen/form',0,'',1,0,1,'admin','2023-01-03 14:35:29','admin','2023-01-05 21:55:47',''),(48,0,'测试菜单',NULL,'testMenu',2,20,NULL,'/test',NULL,0,'',1,1,1,'admin','2023-01-04 10:23:46','admin','2023-01-04 10:24:01',''),(49,48,'增删改(代码生成)demo','GET/sysDemo/page','demo',1,1,'','/test/demo','/sys/sysDemo/index',0,'',1,1,1,'admin','2023-01-04 10:25:00','admin','2025-03-19 00:13:44',''),(50,1,'表单生成记录','/gen/**','formGenRecord',1,70,NULL,'/sys/formGenRecord','/sys/gen/list',0,NULL,1,1,1,'admin','2023-01-05 21:59:53',NULL,NULL,NULL),(52,40,'APP用户','/appUser/**','appUser',1,30,NULL,'/app/appUser','/appUser/index',0,NULL,1,1,1,'admin','2023-01-12 11:26:06',NULL,NULL,NULL),(54,49,'新增','POST/sysDemo','add',3,1,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:24:02',NULL,NULL,NULL),(55,49,'编辑','PUT/sysDemo','edit',3,2,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:24:19',NULL,NULL,NULL),(56,49,'删除','DELETE/sysDemo','del',3,3,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:28:44',NULL,NULL,NULL),(57,49,'查看','GET/sysDemo','view',3,4,NULL,NULL,NULL,0,NULL,1,1,1,'admin','2023-01-13 22:32:31',NULL,NULL,NULL),(58,40,'系统配置','/sysConfig/**','sysConfig',1,40,NULL,'/app/sysConfig','/sys/sysConfig/edit',0,NULL,1,1,1,'admin','2023-01-29 10:52:47',NULL,NULL,NULL),(59,40,'轮播图管理','/appBanner/**','appBanner',1,50,NULL,'/app/appBanner','/sys/appBanner/index',0,NULL,1,1,1,'admin','2023-01-29 14:12:44',NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_auth` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'phone','123456',NULL);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图文内容';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_content`
--

LOCK TABLES `sys_content` WRITE;
/*!40000 ALTER TABLE `sys_content` DISABLE KEYS */;
INSERT INTO `sys_content` VALUES (1,'<h1 style=\"text-align: center;\">“数据二十条”新政出台 促进数据赋能实体经济</h1><p>来源：央视网 | 2022年12月20日 10:36:53</p><p style=\"text-indent: 2em; text-align: start;\"><strong>央视网消息：</strong>近日，中共中央、国务院对外发布了《关于构建数据基础制度更好发挥数据要素作用的意见》，又称“数据二十条”。“数据二十条”提出构建数据产权、流通交易、收益分配、安全治理等制度，初步形成我国数据基础制度的“四梁八柱”。“数据二十条”的出台，有利于充分激活数据要素价值，赋能实体经济，推动高质量发展。</p><p style=\"text-indent: 2em; text-align: start;\">本次出台的“数据二十条”，构建了数据产权、流通交易、收益分配、安全治理等4项制度，共计20条政策措施。</p>'),(2,'<h2 style=\"text-indent: 2em; text-align: center;\"><strong>新华社北京12月19日电题：提振信心 笃定前行——广大干部群众畅谈学习贯彻中央经济工作会议精神</strong></h2><p style=\"text-indent: 2em; text-align: center;\">新华社记者刘开雄、刘羽佳</p><p style=\"text-indent: 2em; text-align: justify;\">连日来，全国各地、各行各业都在认真学习领会中央经济工作会议精神。广大干部群众表示，以习近平同志为核心的党中央关于经济工作的决策部署让大家对未来中国经济发展充满信心。要持续深入学习领会会议精神，纲举目张做好工作，将中央经济工作会议的各项部署落到实处。</p>'),(3,'<h1 style=\"text-align: center;\">学习时节丨心系澳门发展，总书记这些话直抵人心</h1><p><span style=\"color: rgb(130, 130, 130); font-size: 14px;\">2022-12-20 07:38</span> <span style=\"color: rgb(130, 130, 130); font-size: 14px;\">来源：南方网</span> </p><p style=\"text-align: left;\">　　党的十八大以来，习近平总书记一直心系澳门发展，殷殷寄语、深情勉励，为澳门保持繁荣稳定指明了前进方向、提供了不竭动力。今天（12月20日），是澳门回归祖国23周年纪念日。让我们一同重温总书记对澳门的殷切期许，感受直抵人心的力量。</p><p><br></p>'),(26,'<p>1111111111112</p>');
/*!40000 ALTER TABLE `sys_content` ENABLE KEYS */;
UNLOCK TABLES;

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
  `gender` tinyint DEFAULT NULL COMMENT 'enum_Gender_性别(下拉框)',
  `state` tinyint NOT NULL DEFAULT '1' COMMENT '启用状态(单选框)',
  `interest` varchar(255) DEFAULT NULL COMMENT '兴趣(多选框)',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `birth` date DEFAULT NULL COMMENT '生日(日期)',
  `regist_time` datetime NOT NULL COMMENT '注册时间(日期时间)',
  `avatar` varchar(200) NOT NULL COMMENT '头像(图片上传)',
  `video` varchar(200) DEFAULT NULL COMMENT '视频(视频上传)',
  `photos` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '照片(多图上传)',
  `attachment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '附件(文件上传)',
  `introduction` varchar(255) NOT NULL COMMENT '简介(文本域)',
  `detail` varchar(500) DEFAULT NULL COMMENT '详情(富文本)',
  `phone` char(11) NOT NULL COMMENT '手机',
  `location` varchar(200) DEFAULT NULL COMMENT '地址',
  `lng` decimal(22,6) DEFAULT NULL COMMENT '经度',
  `lat` decimal(22,6) DEFAULT NULL COMMENT '纬度',
  `file_input` varchar(200) DEFAULT NULL COMMENT '文件输入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统示例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_demo`
--

LOCK TABLES `sys_demo` WRITE;
/*!40000 ALTER TABLE `sys_demo` DISABLE KEYS */;
INSERT INTO `sys_demo` VALUES (4,'farkle',20,100.00,1,1,'[\"2\",\"1\"]',0,'2025-03-12','2025-03-11 21:25:13','/202503/85128da8-70a5-467c-a379-29a649cab949.gif',NULL,'[{\"id\":13,\"name\":\"QR57a.jpg\",\"url\":\"/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg\"},{\"id\":14,\"name\":\"QRBHS.jpg\",\"url\":\"/202503/6a5cbff9-266b-4dd5-bba2-a0f443eff18b.jpg\"}]','[{\"id\":17,\"name\":\"hello.txt\",\"url\":\"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt\"},{\"id\":16,\"name\":\"test.txt\",\"url\":\"/202503/e03f4fa8-c518-45fe-a0d8-89fe76d62e09.txt\"}]','简介xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','<p>详情<img src=\"http://localhost:8080/upload/202503/996db02c-0a2c-4588-ae6a-6f51d8e39881.jpeg\" alt=\"\" data-href=\"\" style=\"\"/></p>','15288192183',NULL,NULL,NULL,NULL),(5,'zzzzz',1,2.00,2,1,'[\"1\",\"2\"]',0,'2023-01-06','2023-01-05 13:36:25','/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg','http://127.0.0.1:8081/upload/202301/4bc82364-2daa-4977-b982-25b1c67453d4.mp4','[{\"id\":15,\"name\":\"QRqMK.jpg\",\"url\":\"/202503/3022ce7a-1f54-4c65-a268-238b11b89eb4.jpg\"}]','[{\"id\":17,\"name\":\"hello.txt\",\"url\":\"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt\"}]','简介xxx','<p><span style=\"color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;\">详情123</span></p>','11111111111','云南省昆明市呈贡区七甸街道白泥洞',NULL,NULL,'http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt');
/*!40000 ALTER TABLE `sys_demo` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='表单生成字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_gen_column`
--

LOCK TABLES `sys_gen_column` WRITE;
/*!40000 ALTER TABLE `sys_gen_column` DISABLE KEYS */;
INSERT INTO `sys_gen_column` VALUES (150,'app_article','type','','文章类型','type','select',1,1,1,0),(151,'app_article','title','','标题','title','input',1,1,1,255),(152,'app_article','picture','','图片','picture','picture',0,0,0,255),(153,'app_article','introduction','','简介','introduction','input',1,0,0,255),(154,'app_article','content_type','','内容类型','contentType','radio',0,0,1,0),(155,'app_article','content_id','','图文详情','contentId','richtext',0,0,1,0),(156,'app_article','link_url','','超链接','linkUrl','input',0,0,1,255),(157,'app_article','location','','文章位置','location','input',0,0,0,255),(163,'app_version','device','','设备','device','radio',1,1,1,0),(164,'app_version','version','','版本','version','input',1,1,1,20),(165,'app_version','description','','描述','description','input',1,0,1,255),(166,'app_version','download_url','','apk下载url','downloadUrl','input',1,0,1,200),(167,'app_version','force_update','','是否强制更新','forceUpdate','radio',1,1,1,0),(168,'app_user','nickname','','昵称','nickname','input',1,1,1,100),(169,'app_user','avatar','','头像','avatar','picture',1,0,0,200),(170,'app_user','phone','','手机号码','phone','phone',1,1,1,11),(171,'app_user','gender','','性别','gender','select',1,0,0,0),(172,'app_user','birth','','生日','birth','date',1,0,0,0),(173,'app_user','address','','地址','address','input',1,0,0,50),(174,'app_user','integral','','积分','integral','number',0,0,0,0),(175,'app_user','balance','','余额','balance','number',0,0,0,0),(176,'app_user','role','','角色','role','select',0,0,0,0),(177,'app_user','enable_state','','启用状态','enableState','select',1,1,1,0),(178,'app_user','comment','','备注','comment','input',1,0,0,255),(198,'sys_demo','name','','名称','name','input',1,0,1,50),(199,'sys_demo','age','','年龄','age','number',1,0,0,NULL),(200,'sys_demo','score','','分数','score','number',1,0,0,NULL),(201,'sys_demo','gender','Gender','性别','gender','enum',1,0,0,NULL),(202,'sys_demo','state','','启用状态','state','number',1,0,0,NULL),(203,'sys_demo','interest','','兴趣','interest','input',1,0,0,255),(204,'sys_demo','birth','','生日','birth','date',1,0,0,NULL),(205,'sys_demo','regist_time','','注册时间','registTime','datetime',1,0,1,NULL),(206,'sys_demo','avatar','','头像','avatar','input',1,0,1,200),(207,'sys_demo','video','','视频','video','input',1,0,0,200),(208,'sys_demo','photos','','照片','photos','input',1,0,0,500),(209,'sys_demo','attachment','','附件','attachment','input',1,0,0,500),(210,'sys_demo','introduction','','简介','introduction','input',1,0,1,255),(211,'sys_demo','detail','','详情','detail','input',1,0,0,500),(212,'sys_demo','phone','','手机','phone','input',1,0,1,11),(213,'sys_demo','location','','地址','location','input',1,0,0,200),(214,'sys_demo','lng','','经度','lng','number',1,0,0,NULL),(215,'sys_demo','lat','','纬度','lat','number',1,0,0,NULL),(216,'sys_demo','file_input','','文件输入','fileInput','input',1,0,0,200),(241,'sys_organization','pid','','父部门id','pid','number',0,0,0,NULL),(242,'sys_organization','type','','类型','type','radio',1,0,1,NULL),(243,'sys_organization','name','','部门名称','name','input',1,1,1,50),(244,'sys_organization','sort','','显示顺序','sort','number',1,0,0,NULL),(245,'sys_organization','link_name','','联系人','linkName','input',1,0,0,20),(246,'sys_organization','link_phone','','联系电话','linkPhone','input',1,0,0,20),(247,'sys_organization','link_email','','联系邮箱','linkEmail','input',1,0,0,50),(248,'sys_organization','state','EnableState','状态','state','enum',1,0,1,NULL),(249,'sys_organization','creator','','创建账号','creator','input',0,0,1,50),(250,'sys_organization','create_time','','创建时间','createTime','datetime',0,0,1,NULL),(251,'sys_organization','updator','','更新账号','updator','input',0,0,0,50),(252,'sys_organization','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(253,'sys_role','name','','角色名','name','input',1,0,1,50),(254,'sys_role','code','','角色编码','code','input',1,0,0,50),(255,'sys_role','sort','','排序','sort','number',1,0,0,NULL),(256,'sys_role','state','','状态','state','radio',1,0,1,NULL),(257,'sys_role','system_flag','','是否系统内置','systemFlag','radio',0,0,0,NULL),(258,'sys_role','remark','','备注','remark','input',1,0,0,255),(259,'sys_role','create_user','','创建账号','createUser','input',0,0,1,50),(260,'sys_role','create_time','','创建时间','createTime','datetime',1,0,1,NULL),(261,'sys_role','update_user','','更新账号','updateUser','input',0,0,0,50),(262,'sys_role','update_time','','更新时间','updateTime','datetime',0,0,0,NULL);
/*!40000 ALTER TABLE `sys_gen_column` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='表单生成记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_gen_table`
--

LOCK TABLES `sys_gen_table` WRITE;
/*!40000 ALTER TABLE `sys_gen_table` DISABLE KEYS */;
INSERT INTO `sys_gen_table` VALUES (1,'sys_demo','系统示例表'),(7,'app_article','app文章'),(8,'app_version','app版本管理'),(9,'app_version','app版本管理'),(10,'app_user','app用户'),(11,'sys_demo','系统示例表'),(12,'sys_demo','系统示例表'),(13,'sys_organization','组织机构'),(14,'sys_organization','组织机构'),(15,'sys_organization','组织机构'),(16,'sys_role','系统角色');
/*!40000 ALTER TABLE `sys_gen_table` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login_log`
--

LOCK TABLES `sys_login_log` WRITE;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` VALUES (1,'admin','127.0.0.1','','Chrome 13','Windows 10','登录成功','2025-03-08 23:03:38',1,1,NULL,NULL,'21ecfd70431d48139eb1111c9873c24b'),(2,'admin','127.0.0.1','','Chrome 13','Windows 10','登录成功','2025-03-08 23:04:24',1,1,NULL,NULL,'ec97c6ba3302415f9aa50a5edae9a5ff');
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_organization` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `pid` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父部门id',
  `type` tinyint unsigned NOT NULL COMMENT '类型(1组织机构,2部门)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `link_name` varchar(20) DEFAULT NULL COMMENT '联系人',
  `link_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
  `link_email` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `state` tinyint unsigned NOT NULL COMMENT 'enum_EnableState_状态',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组织机构';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
INSERT INTO `sys_organization` VALUES (1,0,1,'云南鸿运有限公司',0,NULL,NULL,NULL,1,'admin','2025-03-16 21:57:28',NULL,NULL),(2,1,1,'1x',0,'','','',1,'admin','2025-03-16 22:20:24','updator','2025-03-16 22:20:42');
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统图片';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_picture`
--

LOCK TABLES `sys_picture` WRITE;
/*!40000 ALTER TABLE `sys_picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_picture` ENABLE KEYS */;
UNLOCK TABLES;

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
  `system_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否系统内置',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员','admin',1,1,0,NULL,'admin','2022-12-17 15:39:13',NULL,NULL),(5,'测试权限','test_auth',2,1,0,NULL,'admin','2023-01-13 22:38:47',NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色-权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_auth`
--

LOCK TABLES `sys_role_auth` WRITE;
/*!40000 ALTER TABLE `sys_role_auth` DISABLE KEYS */;
INSERT INTO `sys_role_auth` VALUES (1,5,48),(2,5,49),(3,5,54),(4,5,55),(5,5,57),(6,5,56);
/*!40000 ALTER TABLE `sys_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_upload`
--

DROP TABLE IF EXISTS `sys_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_upload` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `md5` varchar(60) NOT NULL,
  `url` varchar(200) NOT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件名',
  `file_suffix` varchar(20) DEFAULT NULL COMMENT '文件后缀',
  `upload_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统文件上传';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_upload`
--

LOCK TABLES `sys_upload` WRITE;
/*!40000 ALTER TABLE `sys_upload` DISABLE KEYS */;
INSERT INTO `sys_upload` VALUES (12,'ecba184421c1c757d6e124f4e14e29b2','/202503/85128da8-70a5-467c-a379-29a649cab949.gif','D:\\tmp\\upload\\202503\\85128da8-70a5-467c-a379-29a649cab949.gif','avatar-Dcbh69co.gif','gif','2025-03-11 21:25:18',6334),(13,'f94d1affa17559b90a477d404dd441dc','/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg','D:\\tmp\\upload\\202503\\3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg','QR57a.jpg','jpg','2025-03-11 21:25:28',20581),(14,'f439908ea8858eff7f5a807e6e821930','/202503/6a5cbff9-266b-4dd5-bba2-a0f443eff18b.jpg','D:\\tmp\\upload\\202503\\6a5cbff9-266b-4dd5-bba2-a0f443eff18b.jpg','QRBHS.jpg','jpg','2025-03-11 21:25:28',27509),(15,'1bca6bed6a01c11f6a036f453941d958','/202503/3022ce7a-1f54-4c65-a268-238b11b89eb4.jpg','D:\\tmp\\upload\\202503\\3022ce7a-1f54-4c65-a268-238b11b89eb4.jpg','QRqMK.jpg','jpg','2025-03-11 21:25:28',25395),(16,'4739c5c11d833bb199c16ff95a92b267','/202503/e03f4fa8-c518-45fe-a0d8-89fe76d62e09.txt','D:\\tmp\\upload\\202503\\e03f4fa8-c518-45fe-a0d8-89fe76d62e09.txt','test.txt','txt','2025-03-11 21:25:33',6),(17,'5d41402abc4b2a76b9719d911017c592','/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt','D:\\tmp\\upload\\202503\\fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt','hello.txt','txt','2025-03-11 21:25:33',5),(18,'2e31bb4cd9f371a62b1f6df87b738ac6','/202503/996db02c-0a2c-4588-ae6a-6f51d8e39881.jpeg','D:\\tmp\\upload\\202503\\996db02c-0a2c-4588-ae6a-6f51d8e39881.jpeg','巴旦木.jpeg','jpeg','2025-03-11 21:25:58',13224);
/*!40000 ALTER TABLE `sys_upload` ENABLE KEYS */;
UNLOCK TABLES;

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
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$IPnALJm4UjWekCNwlwNRq.lZSbjVS3YjH2gloSZVwr9aPq5g2OVAC','','系统管理员','','',1,1,1,'admin','2022-12-17 15:44:57','admin','2022-12-19 16:50:55',''),(5,'test','$2a$10$ZCkxAyNnFAhP0Ulx5wQZ4e3WxQg61VvJHRGBEtjIaL/T8tsz1pzAO',NULL,NULL,NULL,NULL,NULL,1,0,'admin','2023-01-13 22:39:16',NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (18,5,5);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `t_order_address` WRITE;
/*!40000 ALTER TABLE `t_order_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order_address` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `t_order_item` WRITE;
/*!40000 ALTER TABLE `t_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order_item` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `user_balance` WRITE;
/*!40000 ALTER TABLE `user_balance` DISABLE KEYS */;
INSERT INTO `user_balance` VALUES (2,7,10,1,'2023-01-14 21:53:47',10,'充值',0),(3,7,3,2,'2023-01-14 21:54:28',7,'余额支付',0);
/*!40000 ALTER TABLE `user_balance` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `user_integral` WRITE;
/*!40000 ALTER TABLE `user_integral` DISABLE KEYS */;
INSERT INTO `user_integral` VALUES (5,7,10,1,'2023-01-12 21:57:19',10,'每日签到',4),(6,7,11,1,'2023-01-13 21:57:42',21,'每日签到',5);
/*!40000 ALTER TABLE `user_integral` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `user_signin` WRITE;
/*!40000 ALTER TABLE `user_signin` DISABLE KEYS */;
INSERT INTO `user_signin` VALUES (4,7,'2023-01-12','2023-01-12 21:57:19',10,1),(5,7,'2023-01-13','2023-01-13 21:57:42',11,2);
/*!40000 ALTER TABLE `user_signin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'znew'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-19  9:13:17
