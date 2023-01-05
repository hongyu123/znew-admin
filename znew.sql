/*
Navicat MySQL Data Transfer

Source Server         : 启月
Source Server Version : 50726
Source Host           : 47.108.230.110:3306
Source Database       : entertainment

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2022-12-28 16:23:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_advice
-- ----------------------------
DROP TABLE IF EXISTS `app_advice`;
CREATE TABLE `app_advice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `category` varchar(50) NOT NULL COMMENT '问题分类',
  `picture` varchar(200) DEFAULT NULL COMMENT '图片',
  `content` varchar(500) NOT NULL COMMENT '建议内容',
  `phone` char(11) DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='app建议反馈';

-- ----------------------------
-- Records of app_advice
-- ----------------------------
INSERT INTO `app_advice` VALUES ('1', '1', '产品建议', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '11111', '123456', '2022-12-03 16:50:31', '1');
INSERT INTO `app_advice` VALUES ('2', '1', '问题反馈', null, 'c22222', null, '2022-12-03 16:50:35', '1');
INSERT INTO `app_advice` VALUES ('3', '1', '问题反馈', null, 'c22222', null, '2022-12-03 16:50:36', '1');
INSERT INTO `app_advice` VALUES ('4', null, '产品建议', null, '产品建议', null, '2022-12-20 17:17:40', '1');
INSERT INTO `app_advice` VALUES ('5', null, '问题反馈', null, '问题反馈', null, '2022-12-20 17:17:55', '1');
INSERT INTO `app_advice` VALUES ('6', '1', '问题反馈', null, '问题反馈2', null, '2022-12-20 17:20:23', '1');
INSERT INTO `app_advice` VALUES ('7', '1', '产品建议', null, '产品建议2', null, '2022-12-20 17:20:30', '1');

-- ----------------------------
-- Table structure for app_article
-- ----------------------------
DROP TABLE IF EXISTS `app_article`;
CREATE TABLE `app_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文章类型',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
  `content_type` tinyint(4) NOT NULL COMMENT '内容类型(图文/超链接)',
  `content_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '图文详情',
  `link_url` varchar(255) DEFAULT NULL COMMENT '超链接',
  `location` varchar(255) DEFAULT NULL COMMENT '文章位置(方便显示用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='app文章';

-- ----------------------------
-- Records of app_article
-- ----------------------------
INSERT INTO `app_article` VALUES ('1', '1', '用户协议', '', '', '1', '1', '', '');
INSERT INTO `app_article` VALUES ('2', '1', '隐私政策', '', '', '1', '2', '', '');
INSERT INTO `app_article` VALUES ('3', '1', '关于我们', '', '', '1', '3', '', '');

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `address_detail` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `tags` varchar(255) DEFAULT NULL COMMENT '标签(逗号分隔)',
  `focus_num` int(11) NOT NULL DEFAULT '0' COMMENT '关注数量',
  `fans_num` int(11) NOT NULL DEFAULT '0' COMMENT '粉丝数量',
  `integral` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `works_num` int(11) NOT NULL DEFAULT '0' COMMENT '作品数量',
  `role` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色(用户/优质创作者/商户)',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  `message_on` tinyint(4) NOT NULL DEFAULT '1' COMMENT '消息总开关',
  `sys_message_on` tinyint(4) NOT NULL DEFAULT '1' COMMENT '系统推送消息开关',
  `im_message_on` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'im消息开关',
  `openid` varchar(255) DEFAULT NULL,
  `apple_id` varchar(255) DEFAULT NULL,
  `im_id` varchar(255) DEFAULT '',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='app用户';

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES ('1', 'znew', 'http://rmm29sxv4.hn-bkt.clouddn.com/03887615-d170-46c8-8f06-a56dac9daa97.png', '15288192181', '1', '2020-01-01', 'address', null, '1,2', '10', '4', '131', '0', '1', '1', '1', '1', '1', null, null, ' ', '');
INSERT INTO `app_user` VALUES ('2', '184', 'http://prototype.ynqiyue.com/subculture/App/images/%E7%94%A8%E6%88%B7%E4%B8%BB%E9%A1%B5/rectangle_u1846.svg', '15288192184', null, null, null, null, '1,2,3', '0', '3', '0', '0', '1', '2', '1', '1', '1', null, null, ' ', '');
INSERT INTO `app_user` VALUES ('3', '185', 'http://prototype.ynqiyue.com/subculture/App/images/%E7%94%A8%E6%88%B7%E4%B8%BB%E9%A1%B5/rectangle_u1846.svg', '15288192185', null, null, null, null, null, '1', '2', '0', '0', '1', '1', '1', '1', '1', null, null, ' ', '');
INSERT INTO `app_user` VALUES ('4', 'farkle', '/farkle', '15288192183', '1', '2022-12-11', 'km', null, 'Goth', '3', '3', '0', '0', '1', '1', '1', '1', '1', null, null, ' ', '');

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device` tinyint(4) NOT NULL COMMENT '设备(android,ios)',
  `version` varchar(20) NOT NULL COMMENT '版本',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `download_url` varchar(200) DEFAULT NULL COMMENT 'apk下载url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='app版本管理';

-- ----------------------------
-- Records of app_version
-- ----------------------------
INSERT INTO `app_version` VALUES ('1', '1', '1', '1', '');
INSERT INTO `app_version` VALUES ('3', '2', '3', '3', '');
INSERT INTO `app_version` VALUES ('4', '1', '1.0', '123456', 'http://rmm29sxv4.hn-bkt.clouddn.com/8cb992a6-905c-4925-9173-4d7b93d84325.apk');
INSERT INTO `app_version` VALUES ('5', '2', '1.0', 'ios1.0', null);

-- ----------------------------
-- Table structure for sys_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_log`;
CREATE TABLE `sys_admin_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `method` varchar(255) NOT NULL COMMENT '方法名',
  `account` varchar(50) NOT NULL COMMENT '管理员账号',
  `request_url` varchar(255) NOT NULL COMMENT '请求url',
  `request_ip` varchar(128) NOT NULL,
  `request_body` varchar(2000) NOT NULL COMMENT '请求体',
  `request_header` varchar(255) DEFAULT NULL COMMENT '请求头',
  `response` varchar(2000) NOT NULL COMMENT '响应',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(1正常,0异常)',
  `message` varchar(255) DEFAULT NULL COMMENT '异常信息',
  `times` bigint(20) NOT NULL COMMENT '耗时',
  `request_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='admin日志';

-- ----------------------------
-- Records of sys_admin_log
-- ----------------------------
INSERT INTO `sys_admin_log` VALUES ('1', '修改文身师', 'com.hfw.admin.controller.TattooController.edit', 'admin', '/tattoo/edit', '127.0.0.1', '[{\"address\":\"5\",\"content\":\"5\",\"gender\":1,\"genderDesc\":\"女\",\"id\":8,\"name\":\"4x\",\"phone\":\"55555555555\",\"picList\":[{\"id\":38,\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/445dc5d2-6be6-4016-928c-153c7909e3fc.png\",\"targetId\":8,\"type\":1,\"url\":\"http://rmm29sxv4.hn-bkt.clouddn.com/445dc5d2-6be6-4016-928c-153c7909e3fc.png\"},{\"id\":39,\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/72f5429e-3f85-40fd-8406-ac7b69952380.png\",\"targetId\":8,\"type\":1,\"url\":\"http://rmm29sxv4.hn-bkt.clouddn.com/72f5429e-3f85-40fd-8406-ac7b69952380.png\"}],\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/b384bad1-a60d-4ab8-86d5-28e4ce15c3d7.png\",\"pictureList\":[],\"sort\":5,\"updateTime\":\"2022-12-11 20:27:49\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '163', '2022-12-17 21:25:14');
INSERT INTO `sys_admin_log` VALUES ('2', '删除文身师', 'com.hfw.admin.controller.TattooController.del', 'admin', '/tattoo/del', '127.0.0.1', '[{\"id\":2}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '0', '想', '15000', '2022-12-17 21:25:39');
INSERT INTO `sys_admin_log` VALUES ('3', '删除标签', 'com.hfw.admin.controller.TagController.del', 'admin', '/tag/del', '127.0.0.1', '[{\"id\":5}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '123', '2022-12-21 13:59:51');
INSERT INTO `sys_admin_log` VALUES ('4', '批量删除标签', 'com.hfw.admin.controller.TagController.dels', 'admin', '/tag/dels', '127.0.0.1', '[[14,13,12,11,10,9,8,7,6,4]]', null, '{\"code\":0,\"data\":\"\",\"message\":\"[4]标签下有子标签,无法删除!\"}', '1', null, '231', '2022-12-21 13:59:55');
INSERT INTO `sys_admin_log` VALUES ('5', '批量删除标签', 'com.hfw.admin.controller.TagController.dels', 'admin', '/tag/dels', '127.0.0.1', '[[14,13,12,11,10,9,8,7,6,4]]', null, '{\"code\":0,\"data\":\"\",\"message\":\"[4]标签下有子标签,无法删除!\"}', '1', null, '233', '2022-12-21 14:00:25');
INSERT INTO `sys_admin_log` VALUES ('6', '删除标签', 'com.hfw.admin.controller.TagController.del', 'admin', '/tag/del', '127.0.0.1', '[{\"id\":14}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '71', '2022-12-21 14:00:52');
INSERT INTO `sys_admin_log` VALUES ('7', '批量删除标签', 'com.hfw.admin.controller.TagController.dels', 'admin', '/tag/dels', '127.0.0.1', '[[13,12,11,10,9,8,7,6,4]]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '259', '2022-12-21 14:00:59');
INSERT INTO `sys_admin_log` VALUES ('8', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"sort\":1,\"tag\":\"美学\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '52', '2022-12-21 14:01:56');
INSERT INTO `sys_admin_log` VALUES ('9', '修改标签', 'com.hfw.admin.controller.TagController.edit', 'admin', '/tag/edit', '127.0.0.1', '[{\"children\":[],\"createTime\":\"2022-12-21 14:01:56\",\"creator\":\"admin\",\"id\":17,\"parentId\":0,\"sort\":1,\"tag\":\"美学\",\"updator\":\"\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '56', '2022-12-21 14:02:07');
INSERT INTO `sys_admin_log` VALUES ('10', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"sort\":2,\"tag\":\"滑板\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '345', '2022-12-21 14:03:21');
INSERT INTO `sys_admin_log` VALUES ('11', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"tag\":\"音乐\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '49', '2022-12-21 14:03:29');
INSERT INTO `sys_admin_log` VALUES ('12', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"parentId\":17,\"sort\":1,\"tag\":\"Goth\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '55', '2022-12-21 14:04:37');
INSERT INTO `sys_admin_log` VALUES ('13', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"parentId\":17,\"tag\":\"工人阶级哥特\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '51', '2022-12-21 14:04:50');
INSERT INTO `sys_admin_log` VALUES ('14', '删除文身师', 'com.hfw.admin.controller.TattooController.del', 'admin', '/tattoo/del', '127.0.0.1', '[{\"id\":3}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '250', '2022-12-22 10:27:13');
INSERT INTO `sys_admin_log` VALUES ('15', '新增文身师', 'com.hfw.admin.controller.TattooController.save', 'admin', '/tattoo/save', '127.0.0.1', '[{\"address\":\"3\",\"content\":\"4\",\"gender\":0,\"genderDesc\":\"男\",\"name\":\"1\",\"phone\":\"22222222222\",\"picList\":[{\"url\":\"http://rmm29sxv4.hn-bkt.clouddn.com/daa80ca9-ddb8-4d5c-84fd-29894f615554.png\"}],\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/a2c2c678-9880-4056-9569-beabc44f3d47.png\",\"sort\":5}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '347', '2022-12-22 14:40:20');
INSERT INTO `sys_admin_log` VALUES ('16', '修改文身师', 'com.hfw.admin.controller.TattooController.edit', 'admin', '/tattoo/edit', '127.0.0.1', '[{\"address\":\"3\",\"content\":\"4\",\"gender\":0,\"genderDesc\":\"男\",\"id\":9,\"name\":\"1\",\"phone\":\"22222222222\",\"picList\":[{\"id\":50,\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/daa80ca9-ddb8-4d5c-84fd-29894f615554.png\",\"targetId\":9,\"type\":1,\"url\":\"http://rmm29sxv4.hn-bkt.clouddn.com/daa80ca9-ddb8-4d5c-84fd-29894f615554.png\"}],\"picture\":\"http://rmm29sxv4.hn-bkt.clouddn.com/a2c2c678-9880-4056-9569-beabc44f3d47.png\",\"pictureList\":[],\"sort\":5,\"updateTime\":\"2022-12-22 14:40:20\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '262', '2022-12-22 14:40:29');
INSERT INTO `sys_admin_log` VALUES ('17', '删除文身师', 'com.hfw.admin.controller.TattooController.del', 'admin', '/tattoo/del', '127.0.0.1', '[{\"id\":9}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '199', '2022-12-22 14:40:31');
INSERT INTO `sys_admin_log` VALUES ('18', '新增标签', 'com.hfw.admin.controller.TagController.save', 'admin', '/tag/save', '127.0.0.1', '[{\"parentId\":17,\"sort\":2,\"tag\":\"1\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '59', '2022-12-22 14:41:19');
INSERT INTO `sys_admin_log` VALUES ('19', '修改标签', 'com.hfw.admin.controller.TagController.edit', 'admin', '/tag/edit', '127.0.0.1', '[{\"children\":[],\"createTime\":\"2022-12-22 14:41:19\",\"creator\":\"admin\",\"id\":22,\"parentId\":17,\"sort\":2,\"tag\":\"1\",\"updator\":\"\"}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '58', '2022-12-22 14:41:24');
INSERT INTO `sys_admin_log` VALUES ('20', '删除标签', 'com.hfw.admin.controller.TagController.del', 'admin', '/tag/del', '127.0.0.1', '[{\"id\":22}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '89', '2022-12-22 14:41:27');
INSERT INTO `sys_admin_log` VALUES ('21', 'APP用户状态', 'com.hfw.admin.controller.AppUserController.state', 'admin', '/appUser/state', '127.0.0.1', '[{\"comment\":\"\",\"id\":4,\"state\":1}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '256', '2022-12-22 14:52:29');
INSERT INTO `sys_admin_log` VALUES ('22', 'APP用户状态', 'com.hfw.admin.controller.AppUserController.state', 'admin', '/appUser/state', '127.0.0.1', '[{\"comment\":\"\",\"id\":4,\"state\":0}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '57', '2022-12-22 14:52:33');
INSERT INTO `sys_admin_log` VALUES ('23', 'APP用户状态', 'com.hfw.admin.controller.AppUserController.state', 'admin', '/appUser/state', '127.0.0.1', '[{\"comment\":\"启用\",\"id\":2,\"state\":0}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '51', '2022-12-22 14:54:15');
INSERT INTO `sys_admin_log` VALUES ('24', 'APP用户状态', 'com.hfw.admin.controller.AppUserController.state', 'admin', '/appUser/state', '127.0.0.1', '[{\"comment\":\"\",\"id\":2,\"state\":1}]', null, '{\"code\":1,\"data\":\"\",\"message\":\"操作成功\"}', '1', null, '84718', '2022-12-22 14:57:45');

-- ----------------------------
-- Table structure for sys_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `name` varchar(50) NOT NULL COMMENT '名称(菜单名)',
  `code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `web_code` varchar(50) NOT NULL COMMENT '前端权限编码(路由名/按钮名)',
  `auth_type` tinyint(4) NOT NULL COMMENT '权限类型(菜单,目录,按钮,权限)',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `frame_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否外链',
  `frame_url` varchar(255) DEFAULT NULL COMMENT '外链地址',
  `cache_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否缓存',
  `show_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `state` tinyint(4) NOT NULL COMMENT '状态',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `contains_code` varchar(255) DEFAULT NULL COMMENT '包含权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限';

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES ('1', '0', '系统管理', '', 'sys', '2', '1', 'Platform', '/sys', '', '0', '', '1', '1', '1', 'admin', '2022-12-17 14:44:47', 'admin', '2022-12-17 15:12:00', '', '');
INSERT INTO `sys_auth` VALUES ('2', '1', '用户管理', '/sysUser/**', 'sysUser', '1', '1', 'UserFilled', '/sys/sysUser', '/sys/sysUser/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 14:52:17', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('3', '1', '角色管理', '/sysRole/**', 'sysRole', '1', '10', 'CollectionTag', '/sys/sysRole', '/sys/sysRole/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 14:56:21', 'admin', '2022-12-17 17:57:36', '', '');
INSERT INTO `sys_auth` VALUES ('4', '1', '权限管理', '/sysAuth/**', 'sysAuth', '1', '20', 'Unlock', '/sys/sysAuth', '/sys/sysAuth/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 14:58:45', 'admin', '2022-12-17 17:55:21', '', '');
INSERT INTO `sys_auth` VALUES ('5', '0', '角色管理', '', 'role', '2', '15', 'Avatar', '/role', '', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:04:52', 'admin', '2022-12-20 09:41:36', '', '');
INSERT INTO `sys_auth` VALUES ('6', '5', '用户管理', '/appUser/**', 'appUser', '1', '1', null, '/role/appUser', '/appUser/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:09:07', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('7', '5', '商家管理', '/merchants/page', 'merchants', '1', '10', null, '/role/merchants', '/merchants/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:09:53', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('8', '5', '导师管理', '/teacher/page', 'teacher', '1', '40', '', '/role/teacher', '/teacher/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:10:33', 'admin', '2022-12-19 21:00:24', '', null);
INSERT INTO `sys_auth` VALUES ('9', '5', '文身师管理', '/tattoo/**', 'tattoo', '1', '30', null, '/role/tattoo', '/tattoo/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:11:05', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('10', '0', '内容管理', null, 'content', '2', '20', 'Grid', '/content', null, '0', null, '1', '1', '1', 'admin', '2022-12-17 15:14:04', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('11', '10', '活动管理', '/activity/**', 'activity', '1', '5', '', '/content/activity', '/activity/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:15:05', 'admin', '2022-12-21 13:56:59', '', null);
INSERT INTO `sys_auth` VALUES ('12', '10', '场地管理', '/site/**', 'site', '1', '10', '', '/content/site', '/site/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:15:35', 'admin', '2022-12-17 15:17:39', '', '');
INSERT INTO `sys_auth` VALUES ('13', '10', 'party管理', '/party/**', 'party', '1', '20', '', '/content/party', '/party/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:16:16', 'admin', '2022-12-17 15:18:05', '', '');
INSERT INTO `sys_auth` VALUES ('14', '10', '课程管理', '/course/page', 'course', '1', '30', null, '/content/course', '/course/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:17:21', null, null, null, '/course/user/signup');
INSERT INTO `sys_auth` VALUES ('15', '0', '积分系统', null, 'integral', '2', '30', 'Lollipop', '/integral', null, '0', null, '1', '1', '1', 'admin', '2022-12-17 15:22:42', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('16', '15', '代金券管理', '/voucher/**', 'voucher', '1', '1', null, '/integral/voucher', '/voucher/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:24:23', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('17', '15', '代金券核销', '/userVoucher/**', 'userVoucher', '1', '10', null, '/integral/userVoucher/:voucherId', '/userVoucher/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:24:57', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('18', '15', '积分任务', '/integralTask/**', 'integralTask', '1', '20', null, '/integral/integralTask', '/integralTask/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:25:33', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('19', '15', '积分明细', '/userIntegrals/**', 'userIntegrals', '1', '30', null, '/integral/userIntegrals', '/userIntegrals/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:26:01', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('20', '0', '审核中心', null, 'audit', '2', '40', 'Checked', '/audit', null, '0', null, '1', '1', '1', 'admin', '2022-12-17 15:28:21', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('21', '20', '帖子审核', '/userWorks/**', 'userWorks', '1', '1', null, '/audit/userWorks', '/userWorks/index', '0', null, '1', '1', '1', 'admin', '2022-12-17 15:29:14', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('22', '20', '商户认证', '/userMerchantsAuth/**', 'userMerchantsAuth', '1', '10', '', '/audit/userMerchantsAuth', '/userMerchantsAuth/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 15:29:37', 'admin', '2022-12-19 23:50:43', '', null);
INSERT INTO `sys_auth` VALUES ('23', '1', '系统日志', '/sysAdminLog/*', 'sysAdminLog', '1', '30', 'Operation', '/sys/sysAdminLog', '/sys/sysAdminLog/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 21:39:57', 'admin', '2022-12-19 18:20:04', '', null);
INSERT INTO `sys_auth` VALUES ('24', '1', '登录日志', '/sysLoginLog/*', 'sysLoginLog', '1', '40', 'Lock', '/sys/sysLoginLog', '/sys/sysLoginLog/index', '0', '', '1', '1', '1', 'admin', '2022-12-17 21:41:59', 'admin', '2022-12-19 18:20:15', '', null);
INSERT INTO `sys_auth` VALUES ('25', '8', '查看', '/teacher/detail', 'detail', '3', '1', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 17:36:26', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('26', '8', '新增', '/teacher/save', 'save', '3', '2', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 17:47:16', null, null, null, '/teacher/select');
INSERT INTO `sys_auth` VALUES ('27', '8', '编辑', '/teacher/edit', 'edit', '3', '3', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 17:47:33', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('28', '8', '启用/禁用', '/teacher/state', 'state', '3', '4', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 17:48:34', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('29', '8', '删除', '/teacher/del', 'del', '3', '5', '', '', '', '0', '', '1', '1', '1', 'admin', '2022-12-19 17:48:53', 'admin', '2022-12-19 17:53:55', '', '/teacher/dels');
INSERT INTO `sys_auth` VALUES ('30', '7', '查看', '/merchants/detail', 'detail', '3', '1', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:04:39', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('31', '7', '新增', '/merchants/save', 'save', '3', '2', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:04:57', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('32', '7', '编辑', '/merchants/edit', 'edit', '3', '3', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:05:14', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('33', '7', '启用/禁用', '/merchants/state', 'state', '3', '4', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:06:02', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('34', '7', '删除', '/merchants/del', 'del', '3', '5', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:06:30', null, null, null, '/merchants/dels');
INSERT INTO `sys_auth` VALUES ('35', '14', '查看', '/course/detail', 'detail', '3', '1', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:08:04', null, null, null, '/activity/category');
INSERT INTO `sys_auth` VALUES ('36', '14', '新增', '/course/save', 'save', '3', '2', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:08:26', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('37', '14', '编辑', '/course/edit', 'edit', '3', '3', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:08:45', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('38', '14', '启用/禁用', '/course/state', 'state', '3', '4', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:09:35', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('39', '14', '删除', '/course/del', 'del', '3', '5', null, null, null, '0', null, '1', '1', '1', 'admin', '2022-12-19 21:09:53', null, null, null, '/course/dels');
INSERT INTO `sys_auth` VALUES ('40', '0', 'APP管理', null, 'app', '2', '10', 'Apple', null, null, '0', null, '1', '1', '1', 'admin', '2022-12-20 09:42:33', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('41', '40', '系统文章', '/appArticle/*', 'appArticle', '1', '1', null, '/app/appArticle', '/sys/appArticle/index', '0', null, '1', '1', '1', 'admin', '2022-12-20 09:44:18', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('42', '40', '建议反馈', '/appAdvice/*', 'appAdvice', '1', '10', null, '/app/appAdvice', '/sys/appAdvice/index', '0', null, '1', '1', '1', 'admin', '2022-12-20 09:45:21', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('43', '40', '版本管理', '/appVersion/*', 'appVersion', '1', '20', null, '/app/appVersion', '/sys/appVersion/index', '0', null, '1', '1', '1', 'admin', '2022-12-20 09:46:01', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('44', '10', '标签管理', '/tag/**', 'tag', '1', '1', null, '/content/tag', '/tag/index', '0', null, '1', '1', '1', 'admin', '2022-12-21 13:58:37', null, null, null, null);
INSERT INTO `sys_auth` VALUES ('45', '1', '角色授权用户', '/sysRole/users', 'sysUserRole', '1', '11', '', '/sys/sysUserRole/:roleId', '/sys/sysRole/users', '0', '', '1', '0', '1', 'admin', '2022-12-28 15:10:23', 'admin', '2022-12-28 15:30:12', '', null);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(200) NOT NULL,
  `value` varchar(255) NOT NULL,
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_content
-- ----------------------------
DROP TABLE IF EXISTS `sys_content`;
CREATE TABLE `sys_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL COMMENT '图文详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='图文内容';

-- ----------------------------
-- Records of sys_content
-- ----------------------------
INSERT INTO `sys_content` VALUES ('1', '<h1 style=\"text-align: center;\">“数据二十条”新政出台 促进数据赋能实体经济</h1><p>来源：央视网 | 2022年12月20日 10:36:53</p><p style=\"text-indent: 2em; text-align: start;\"><strong>央视网消息：</strong>近日，中共中央、国务院对外发布了《关于构建数据基础制度更好发挥数据要素作用的意见》，又称“数据二十条”。“数据二十条”提出构建数据产权、流通交易、收益分配、安全治理等制度，初步形成我国数据基础制度的“四梁八柱”。“数据二十条”的出台，有利于充分激活数据要素价值，赋能实体经济，推动高质量发展。</p><p style=\"text-indent: 2em; text-align: start;\">本次出台的“数据二十条”，构建了数据产权、流通交易、收益分配、安全治理等4项制度，共计20条政策措施。</p>');
INSERT INTO `sys_content` VALUES ('2', '<h2 style=\"text-indent: 2em; text-align: center;\"><strong>新华社北京12月19日电题：提振信心 笃定前行——广大干部群众畅谈学习贯彻中央经济工作会议精神</strong></h2><p style=\"text-indent: 2em; text-align: center;\">新华社记者刘开雄、刘羽佳</p><p style=\"text-indent: 2em; text-align: justify;\">连日来，全国各地、各行各业都在认真学习领会中央经济工作会议精神。广大干部群众表示，以习近平同志为核心的党中央关于经济工作的决策部署让大家对未来中国经济发展充满信心。要持续深入学习领会会议精神，纲举目张做好工作，将中央经济工作会议的各项部署落到实处。</p>');
INSERT INTO `sys_content` VALUES ('3', '<h1 style=\"text-align: center;\">学习时节丨心系澳门发展，总书记这些话直抵人心</h1><p><span style=\"color: rgb(130, 130, 130); font-size: 14px;\">2022-12-20 07:38</span> <span style=\"color: rgb(130, 130, 130); font-size: 14px;\">来源：南方网</span> </p><p style=\"text-align: left;\">　　党的十八大以来，习近平总书记一直心系澳门发展，殷殷寄语、深情勉励，为澳门保持繁荣稳定指明了前进方向、提供了不竭动力。今天（12月20日），是澳门回归祖国23周年纪念日。让我们一同重温总书记对澳门的殷切期许，感受直抵人心的力量。</p><p><br></p>');
INSERT INTO `sys_content` VALUES ('4', '内容');
INSERT INTO `sys_content` VALUES ('6', 'party内容');
INSERT INTO `sys_content` VALUES ('7', '<p>123456</p>');
INSERT INTO `sys_content` VALUES ('8', '<p>1</p>');
INSERT INTO `sys_content` VALUES ('9', '<p>222222222222</p>');
INSERT INTO `sys_content` VALUES ('10', '<p>333333333333333333</p>');
INSERT INTO `sys_content` VALUES ('12', '1111111113');
INSERT INTO `sys_content` VALUES ('13', '<p>111111111111111x</p>');
INSERT INTO `sys_content` VALUES ('14', '<p>333333333333</p>');
INSERT INTO `sys_content` VALUES ('16', '<p>aaaaaaaaaaaaa</p>');
INSERT INTO `sys_content` VALUES ('18', '<p>444444444444444444444444444</p>');
INSERT INTO `sys_content` VALUES ('19', '<p>nullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnull</p>');
INSERT INTO `sys_content` VALUES ('20', '<p>课程111111111111111111111111</p>');
INSERT INTO `sys_content` VALUES ('21', '<p>21</p>');

-- ----------------------------
-- Table structure for sys_demo
-- ----------------------------
DROP TABLE IF EXISTS `sys_demo`;
CREATE TABLE `sys_demo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄(数字框)',
  `name` varchar(50) NOT NULL COMMENT '名称(文本框)',
  `gender` tinyint(4) NOT NULL DEFAULT '3' COMMENT '性别(下拉框)',
  `deleted` tinyint(4) NOT NULL COMMENT '逻辑删除',
  `score` decimal(4,2) DEFAULT NULL COMMENT '分数',
  `birth` date NOT NULL COMMENT '生日',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统示例表';

-- ----------------------------
-- Records of sys_demo
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `ip` varchar(128) NOT NULL DEFAULT '' COMMENT '登录IP',
  `location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `message` varchar(255) NOT NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(1成功0失败)',
  `online_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '在线状态',
  `logout_type` tinyint(4) DEFAULT NULL COMMENT '登出类型',
  `logout_time` datetime DEFAULT NULL COMMENT '登出时间',
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb4 COMMENT='系统登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('1', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 00:24:53', '1', '0', '1', null, 'b96d5d73eecd4d4eb95ec71bf903c9bd');
INSERT INTO `sys_login_log` VALUES ('2', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 00:27:04', '1', '0', '1', null, 'f244c6ce25614faab8cb757f75c86cd6');
INSERT INTO `sys_login_log` VALUES ('3', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 00:30:55', '1', '0', '1', null, 'c66d8e328bdb4a73aa7032034abd0a6f');
INSERT INTO `sys_login_log` VALUES ('4', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 00:48:12', '1', '0', '1', null, 'c2ca2e1c40ff4b4b85c4ae770df24f4a');
INSERT INTO `sys_login_log` VALUES ('5', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 01:47:40', '1', '0', '1', null, '68d1f1fe85624209ad5ad46eed653735');
INSERT INTO `sys_login_log` VALUES ('6', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 01:56:17', '1', '0', '1', null, 'c658205c50984b22831647c21a45f3e2');
INSERT INTO `sys_login_log` VALUES ('7', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 01:58:24', '1', '0', '1', null, '07d3093e103e45c1970f72f7438a6f94');
INSERT INTO `sys_login_log` VALUES ('8', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:00:21', '1', '0', '1', null, '540e82b0bb3b44c6b52ebb994d88e8b6');
INSERT INTO `sys_login_log` VALUES ('9', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:00:46', '1', '0', '1', null, '6040a3dcb6724b89914dc6647d911ad0');
INSERT INTO `sys_login_log` VALUES ('10', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:03:54', '1', '0', '1', null, 'efba7df75e344881b66b2f72a4893426');
INSERT INTO `sys_login_log` VALUES ('11', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:09:29', '1', '0', '2', '2022-12-18 02:09:41', '4e311187153449c28128c77959628ef9');
INSERT INTO `sys_login_log` VALUES ('12', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:10:22', '1', '0', '3', '2022-12-18 02:12:34', '864a8b62a9344253ac6ed26aded5d1cc');
INSERT INTO `sys_login_log` VALUES ('13', 'admin', '127.0.0.1', '', '', '', '登录成功', '2022-12-18 02:12:31', '1', '0', '3', '2022-12-18 02:12:48', 'fe4c2c55e869431b8ca2360e5cd35746');
INSERT INTO `sys_login_log` VALUES ('14', 'admin', '127.0.0.1', '', '', '', '登录成功', '2022-12-18 02:12:34', '1', '0', '3', '2022-12-18 02:14:41', 'f008f890c51544f08189b8576ce934a7');
INSERT INTO `sys_login_log` VALUES ('15', 'admin', '127.0.0.1', '', '', '', '登录成功', '2022-12-18 02:12:49', '1', '0', '1', null, 'af17d4f17f6c4ffab4e5210615d071ee');
INSERT INTO `sys_login_log` VALUES ('16', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:14:41', '1', '0', '1', null, '752f8d27274c48b585096f5d675692b8');
INSERT INTO `sys_login_log` VALUES ('17', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:17:42', '1', '0', '1', null, '00e7b3c7ceef451f9b4b58078f4e52df');
INSERT INTO `sys_login_log` VALUES ('18', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:19:27', '1', '0', '1', null, 'd26918e3d2c2457ab349b1749a48e2ee');
INSERT INTO `sys_login_log` VALUES ('19', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:24:44', '1', '0', '1', null, '6b020a4ac68b4a90b2420351c88dc93b');
INSERT INTO `sys_login_log` VALUES ('20', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:26:13', '1', '0', '1', null, '76332de7ac9a4580aac4b585c2359011');
INSERT INTO `sys_login_log` VALUES ('21', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:28:49', '1', '0', '3', '2022-12-18 02:29:33', '1ed37bc6c02b4e86a20e1c4609f7431c');
INSERT INTO `sys_login_log` VALUES ('22', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:28:56', '1', '0', '1', null, 'aa9374987ad74a0e9d803914e1302fed');
INSERT INTO `sys_login_log` VALUES ('23', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:29:33', '1', '0', '1', null, 'cd84d484c6984815a37e7da2f5363f22');
INSERT INTO `sys_login_log` VALUES ('24', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:31:13', '1', '0', '1', null, '87f2290754d8427b9b8c5761f972813a');
INSERT INTO `sys_login_log` VALUES ('25', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:32:28', '1', '0', '1', null, '21c6358fd6174b5abc03697aa19bd920');
INSERT INTO `sys_login_log` VALUES ('26', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:34:00', '1', '0', '1', null, 'd4f9db60042b4bf1a77b7439b7f02f87');
INSERT INTO `sys_login_log` VALUES ('27', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:41:53', '1', '0', '1', null, '6c067159e4b94415965ea4bd56895e37');
INSERT INTO `sys_login_log` VALUES ('28', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:43:31', '1', '0', '1', null, 'f4aeeddcbeaf413aa30ce44cd4c83865');
INSERT INTO `sys_login_log` VALUES ('29', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:49:48', '1', '0', '1', null, '72a5e77ecaac438f8d6c23afc8b9f151');
INSERT INTO `sys_login_log` VALUES ('30', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:52:18', '1', '0', '1', null, 'e8d3bbf4669a49aaa1213f883a381fb9');
INSERT INTO `sys_login_log` VALUES ('31', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 02:57:52', '1', '0', '1', null, 'dd22c5bca2804647a396ff9cb2816409');
INSERT INTO `sys_login_log` VALUES ('32', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:00:19', '1', '0', '3', '2022-12-18 03:00:54', '35d5268ddbf24e2d9aa24d3b141be7fb');
INSERT INTO `sys_login_log` VALUES ('33', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:00:33', '1', '0', '3', '2022-12-18 03:01:40', '111d031cbed5450e96cd7b582d0bc846');
INSERT INTO `sys_login_log` VALUES ('34', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:00:54', '1', '0', '1', null, 'aa5480bd601b4994bf26546da985f73f');
INSERT INTO `sys_login_log` VALUES ('35', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:01:40', '1', '0', '1', null, 'e18ab985aea546e48da69707c59fa0bd');
INSERT INTO `sys_login_log` VALUES ('36', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:02:53', '1', '0', '1', null, 'a47e1d66b6274dda826acd481b623852');
INSERT INTO `sys_login_log` VALUES ('37', 'admin', '127.0.0.1', '', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-18 03:03:13', '1', '0', '2', '2022-12-18 03:03:22', '1a59bb6df59c4df3a794647d569940bc');
INSERT INTO `sys_login_log` VALUES ('38', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 10:44:54', '1', '0', '1', null, '742ce124a2074f6ca1ee36e19ea6f535');
INSERT INTO `sys_login_log` VALUES ('39', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 10:50:04', '1', '0', '1', null, 'c32acfeb452c45eb87011d66cc57feff');
INSERT INTO `sys_login_log` VALUES ('40', '', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '密码错误', '2022-12-19 12:19:19', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('41', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 12:19:23', '1', '0', '2', '2022-12-19 12:50:08', 'd5731d2a81d34400af70251e7ff8c599');
INSERT INTO `sys_login_log` VALUES ('42', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '密码错误', '2022-12-19 12:39:11', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('43', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 12:39:15', '1', '0', '2', '2022-12-19 12:50:08', 'd323e5ca3a914a3fa8ba22c7e33207a0');
INSERT INTO `sys_login_log` VALUES ('44', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 12:50:33', '1', '0', '2', '2022-12-19 12:52:20', '815af828fb4a466a86018ca03f581131');
INSERT INTO `sys_login_log` VALUES ('45', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 12:52:28', '1', '0', '3', '2022-12-19 13:04:39', '6219a7bab15f4a1092067ffca5c0da7c');
INSERT INTO `sys_login_log` VALUES ('46', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:04:38', '1', '0', '3', '2022-12-19 13:07:18', 'b1147e26964e4c449db9c8e9f676b526');
INSERT INTO `sys_login_log` VALUES ('47', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:04:40', '1', '1', null, null, '55f72fe5ddc449eb91744cec51896ed1');
INSERT INTO `sys_login_log` VALUES ('48', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:07:18', '1', '1', null, null, 'a92fc205a6bb4138acf4b678288408fc');
INSERT INTO `sys_login_log` VALUES ('49', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:08:16', '1', '0', '3', '2022-12-19 13:08:24', 'bf979caacac649ca95a505a5b6b7db5e');
INSERT INTO `sys_login_log` VALUES ('50', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:08:22', '1', '0', '3', '2022-12-19 13:38:13', '735b583ca68a402789572e9e1c068146');
INSERT INTO `sys_login_log` VALUES ('51', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:08:24', '1', '0', '1', null, '1bbbdeba9aae44d4a06a0fd2933c8dca');
INSERT INTO `sys_login_log` VALUES ('52', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:38:14', '1', '0', '1', null, '7c44fc63ac7943728e081399589cad91');
INSERT INTO `sys_login_log` VALUES ('53', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:47:43', '1', '0', '1', null, 'f13b832dea8c4be2b79ad2fb078b937a');
INSERT INTO `sys_login_log` VALUES ('54', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:48:06', '1', '0', '1', null, 'b995b87fcab54904b529166640eb86ad');
INSERT INTO `sys_login_log` VALUES ('55', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:51:55', '1', '1', null, null, '99fdd025112345278d7a3019cbf30977');
INSERT INTO `sys_login_log` VALUES ('56', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:56:09', '1', '0', '3', '2022-12-19 13:56:15', '3b6dfdb353064fd39628f0471be7648b');
INSERT INTO `sys_login_log` VALUES ('57', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:56:14', '1', '0', '3', '2022-12-19 13:57:47', '5ae5cc4a753942908e6d7b7bd73362b0');
INSERT INTO `sys_login_log` VALUES ('58', 'admin', '127.0.0.1', '本地局域网', '', '', '登录成功', '2022-12-19 13:56:16', '1', '0', '2', '2022-12-19 14:00:29', '417640dbcb154d9dacc0ce6ad45a2231');
INSERT INTO `sys_login_log` VALUES ('59', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 13:57:47', '1', '0', '2', '2022-12-19 14:00:29', 'ea09641544b341d8be9f5ee4a02f2601');
INSERT INTO `sys_login_log` VALUES ('60', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 14:00:35', '1', '0', '2', '2022-12-19 14:17:46', 'ae39d9da4e35451dbc7258ad2f01d802');
INSERT INTO `sys_login_log` VALUES ('61', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 14:17:55', '1', '1', null, null, 'c19883d27b214666a7b377e8d6f89fda');
INSERT INTO `sys_login_log` VALUES ('62', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:02:00', '1', '0', '3', '2022-12-19 16:20:01', '43d52893a5234cdc9e071e44591694a3');
INSERT INTO `sys_login_log` VALUES ('63', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:11:03', '1', '0', '3', '2022-12-19 16:20:20', 'ba58ec93ecbe41c3acdfea97de6d4607');
INSERT INTO `sys_login_log` VALUES ('64', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '密码错误', '2022-12-19 16:19:58', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('65', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:20:01', '1', '0', '2', '2022-12-19 16:45:33', '39336cf08c1e45458b3b792b04cab1be');
INSERT INTO `sys_login_log` VALUES ('66', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:20:20', '1', '0', '2', '2022-12-19 16:45:33', '14fc7eee45d948ceb6f99e3ce3b5ea5f');
INSERT INTO `sys_login_log` VALUES ('67', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:45:45', '1', '0', '2', '2022-12-19 16:51:28', '0fcaf1afce3b4d92a86d3a679436540b');
INSERT INTO `sys_login_log` VALUES ('68', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:46:25', '1', '0', '2', '2022-12-19 16:51:28', 'e2b04a823429478e9de5fbfe427d8fba');
INSERT INTO `sys_login_log` VALUES ('69', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 16:51:36', '1', '0', '3', '2022-12-19 18:19:02', 'b8ddb6519ec94b78a313e32c6c07b151');
INSERT INTO `sys_login_log` VALUES ('70', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 17:08:46', '1', '1', null, null, 'b2dacd9e8eb14450afae24a83795a451');
INSERT INTO `sys_login_log` VALUES ('71', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 17:45:13', '1', '1', null, null, '77883b6371174fbfbe5d5304c095990f');
INSERT INTO `sys_login_log` VALUES ('72', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 18:18:31', '1', '1', null, null, '5991b95c534e4d0fa9bea00a1135cb2b');
INSERT INTO `sys_login_log` VALUES ('73', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 18:19:02', '1', '0', '3', '2022-12-19 19:12:41', '9090e64089b64bfa8dd89f8abfe04319');
INSERT INTO `sys_login_log` VALUES ('74', 'audit', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 18:31:04', '1', '0', '3', '2022-12-19 19:00:23', 'ebc19a74048e4ebfbe763173e4afbc01');
INSERT INTO `sys_login_log` VALUES ('75', 'audit', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 18:42:34', '1', '0', '2', '2022-12-19 19:00:49', 'c2c731f8944c4e2798d3015384379f94');
INSERT INTO `sys_login_log` VALUES ('76', 'merchants', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 18:43:23', '1', '1', null, null, '17c17e263f0e48eaac943ad0c86da14b');
INSERT INTO `sys_login_log` VALUES ('77', 'audit', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 19:00:24', '1', '0', '2', '2022-12-19 19:00:49', '00f07c6df96a4127b081debcfb255fd5');
INSERT INTO `sys_login_log` VALUES ('78', 'admin', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 19:00:59', '1', '1', null, null, '3e0dd4e4254b4f5496f845e8db68f666');
INSERT INTO `sys_login_log` VALUES ('79', 'audit', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 19:03:07', '1', '0', '2', '2022-12-19 19:17:27', '7dbe0f4913644b8d9cbbaf2ca35293b5');
INSERT INTO `sys_login_log` VALUES ('80', 'admin', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 19:12:41', '1', '1', null, null, '1c7fbbce1bb144beb495eee9f3471106');
INSERT INTO `sys_login_log` VALUES ('81', 'merchants', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 19:17:35', '1', '1', null, null, 'a779e25100e1464cb4424781f75f6504');
INSERT INTO `sys_login_log` VALUES ('82', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 20:47:10', '1', '0', '3', '2022-12-19 23:44:29', '541d8349279b45518e95a78d94d3e381');
INSERT INTO `sys_login_log` VALUES ('83', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 22:24:54', '1', '0', '3', '2022-12-19 22:52:03', 'f658269d392b483e88165bebdbc81474');
INSERT INTO `sys_login_log` VALUES ('84', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 22:32:32', '1', '0', '2', '2022-12-19 23:18:14', '84d91322750d497da7a5d3f40563e5b1');
INSERT INTO `sys_login_log` VALUES ('85', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 22:52:03', '1', '0', '2', '2022-12-19 23:18:14', '9f929a57312045b8956156b6063022d3');
INSERT INTO `sys_login_log` VALUES ('86', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 23:18:22', '1', '0', '1', null, 'e0a0dcee663d454e8375cadb501f9f48');
INSERT INTO `sys_login_log` VALUES ('87', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 23:39:19', '1', '0', '3', '2022-12-19 23:50:26', 'b45206672711487686ff030f012ed3b5');
INSERT INTO `sys_login_log` VALUES ('88', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 23:44:29', '1', '0', '1', null, 'd4e155501dfd4434bae7fae6bed51c12');
INSERT INTO `sys_login_log` VALUES ('89', 'audit', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-19 23:49:49', '1', '0', '1', null, '5387843cce85477b8a9a964b427011b4');
INSERT INTO `sys_login_log` VALUES ('90', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-19 23:50:26', '1', '0', '1', null, 'd3f2d2ae7b894995b0392900cdff0300');
INSERT INTO `sys_login_log` VALUES ('91', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 09:40:30', '1', '0', '1', null, '72da111bcdd647d5bfe2578c038f7dc9');
INSERT INTO `sys_login_log` VALUES ('92', 'admin', '39.130.66.99', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 14:47:59', '1', '0', '3', '2022-12-20 15:46:50', '337d961ebfe64dbdab897114b7ccef5f');
INSERT INTO `sys_login_log` VALUES ('93', 'admin', '39.130.100.237', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 15:46:51', '1', '1', null, null, 'bce8bde789ba4193a89cf1d2f1eb5e7b');
INSERT INTO `sys_login_log` VALUES ('94', 'admin', '39.130.66.99', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 17:21:40', '1', '0', '3', '2022-12-20 17:32:00', 'c0c1a4f2399348888ebe1d0bf0b83e35');
INSERT INTO `sys_login_log` VALUES ('95', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 17:23:50', '1', '1', null, null, '1c56b77593554616834e64594ee00514');
INSERT INTO `sys_login_log` VALUES ('96', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-20 17:32:02', '1', '1', null, null, '8c7c9a3e10ea4666996c117629f70a37');
INSERT INTO `sys_login_log` VALUES ('97', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-21 13:53:57', '1', '1', null, null, '96436d493c874667a90f7fcec85d80f1');
INSERT INTO `sys_login_log` VALUES ('98', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-21 16:53:08', '1', '1', null, null, '3eb3aae8c5674a57aa8bdebb08bea78f');
INSERT INTO `sys_login_log` VALUES ('99', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 10:07:14', '1', '0', '2', '2022-12-22 11:12:27', 'a4c0ad4ccd394abea62d21bf2ae5d864');
INSERT INTO `sys_login_log` VALUES ('100', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 10:41:34', '1', '0', '2', '2022-12-22 11:06:02', '65ddce9648614c4f810bf5c0a5e69cff');
INSERT INTO `sys_login_log` VALUES ('101', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 10:53:34', '1', '0', '2', '2022-12-22 11:06:02', '178b68120ebb4599956d013a74c5ea46');
INSERT INTO `sys_login_log` VALUES ('102', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:06:12', '1', '0', '2', '2022-12-22 11:12:12', '4a8c76802faf4561911d58c31b863d73');
INSERT INTO `sys_login_log` VALUES ('103', 'admin', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:12:23', '1', '0', '2', '2022-12-22 11:12:27', 'c5057280bd484389a48af5359b58fbde');
INSERT INTO `sys_login_log` VALUES ('104', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:12:48', '1', '0', '2', '2022-12-22 11:21:10', '9479a34126c843dd86012a93e36a9f44');
INSERT INTO `sys_login_log` VALUES ('105', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 11:14:41', '1', '0', '2', '2022-12-22 11:22:04', 'd6c12c8591f34c2fb21335a397d84df0');
INSERT INTO `sys_login_log` VALUES ('106', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:21:21', '1', '0', '2', '2022-12-22 11:24:31', '6b99c9c085d9422dbbd26403546293d7');
INSERT INTO `sys_login_log` VALUES ('107', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 11:22:11', '1', '0', '1', null, '6b515f301d1d4e3eb58f47212a005439');
INSERT INTO `sys_login_log` VALUES ('108', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:24:43', '1', '0', '2', '2022-12-22 11:46:45', 'ac40254d6d954b6298d6f6e351436646');
INSERT INTO `sys_login_log` VALUES ('109', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 11:46:56', '1', '0', '1', null, 'fcc45a07adc44c6d9d5760fd8a8877c0');
INSERT INTO `sys_login_log` VALUES ('110', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 13:05:39', '1', '0', '2', '2022-12-22 15:10:23', '88632e42c5b34e2b843b7856ecbbc924');
INSERT INTO `sys_login_log` VALUES ('111', 'merchants', '127.0.0.1', '云南省昆明市 移动', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 14:25:54', '1', '0', '1', null, '762e5e86b1344242a1ebfa730a65d2a6');
INSERT INTO `sys_login_log` VALUES ('112', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-22 14:27:09', '1', '0', '2', '2022-12-22 15:09:03', 'a03cd302cc184870849eaae58b6ac2e0');
INSERT INTO `sys_login_log` VALUES ('113', 'merchants', '39.130.66.99', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 15:08:27', '1', '0', '2', '2022-12-22 15:09:03', '7b1e08587de04686928e778b536fffb9');
INSERT INTO `sys_login_log` VALUES ('114', 'admin', '39.130.66.99', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-22 15:09:09', '1', '0', '2', '2022-12-22 15:10:23', '92a9fa67de2a453b9c087ebda9dbe49a');
INSERT INTO `sys_login_log` VALUES ('115', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-28 10:17:01', '1', '1', null, null, '43c70c9bd70f4794a8fb99ae5922af90');
INSERT INTO `sys_login_log` VALUES ('116', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:22:10', '1', '0', '2', '2022-12-28 10:23:58', '97ee0fc98c6847df82aebc9dc0e18952');
INSERT INTO `sys_login_log` VALUES ('117', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:24:14', '1', '0', '2', '2022-12-28 10:28:04', 'bd53ca39b5444902b198ba3342a28d72');
INSERT INTO `sys_login_log` VALUES ('118', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:27:18', '1', '0', '2', '2022-12-28 10:28:04', 'a0c7150da1ae439fb4ba791e3a28a90a');
INSERT INTO `sys_login_log` VALUES ('119', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:28:11', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('120', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:31:03', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('121', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:31:15', '1', '1', null, null, '0611a370aeb04ba9a9e45a40e0328e42');
INSERT INTO `sys_login_log` VALUES ('122', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:31:35', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('123', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:40:00', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('124', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:40:02', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('125', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录失败:被禁用的账号!', '2022-12-28 10:40:02', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('126', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:40:13', '1', '1', null, null, 'd2426f19cfef429f8c87192500850762');
INSERT INTO `sys_login_log` VALUES ('127', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:40:33', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('128', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:40:35', '0', '1', null, null, null);
INSERT INTO `sys_login_log` VALUES ('129', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:40:49', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('130', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:40:55', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('131', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:41:06', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('132', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:42:16', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('133', '', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '密码错误', '2022-12-28 10:43:33', '0', '0', '1', null, null);
INSERT INTO `sys_login_log` VALUES ('134', 'test', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 10:45:30', '1', '0', '1', null, '7f1b2234a8394f41bc649e5422d1e0a9');
INSERT INTO `sys_login_log` VALUES ('135', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-28 14:17:28', '1', '0', '1', null, 'df0cbb7f36484ca8afa67b1f35f840cc');
INSERT INTO `sys_login_log` VALUES ('136', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 14:18:09', '1', '0', '1', null, '0db8bc02aa394ff6be92faafe90dd946');
INSERT INTO `sys_login_log` VALUES ('137', 'admin', '127.0.0.1', '本地局域网', 'Chrome 10', 'Windows 10', '登录成功', '2022-12-28 15:05:10', '1', '1', null, null, 'fc20838969d44b9892e4295e587d92f8');
INSERT INTO `sys_login_log` VALUES ('138', 'merchants', '127.0.0.1', '本地局域网', '搜狗Chrome 80', 'Windows 10', '登录成功', '2022-12-28 15:41:34', '1', '1', null, null, '41a07526271f42c3a94cb9cb0215e0cf');

-- ----------------------------
-- Table structure for sys_picture
-- ----------------------------
DROP TABLE IF EXISTS `sys_picture`;
CREATE TABLE `sys_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '作品,纹身师,',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `target_id` bigint(20) NOT NULL COMMENT '目标id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='系统图片';

-- ----------------------------
-- Records of sys_picture
-- ----------------------------
INSERT INTO `sys_picture` VALUES ('1', '1', 'http://prototype.ynqiyue.com/subculture/App/images/%E9%A6%96%E9%A1%B5/u322.svg', '1');
INSERT INTO `sys_picture` VALUES ('21', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/03887615-d170-46c8-8f06-a56dac9daa97.png', '5');
INSERT INTO `sys_picture` VALUES ('33', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/981cd386-aa4c-44b1-9cea-5a737f8169fc.png', '6');
INSERT INTO `sys_picture` VALUES ('34', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/4a7031ef-afd9-4aa5-9396-546be8f7715a.png', '6');
INSERT INTO `sys_picture` VALUES ('36', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/46b59156-ef05-407b-bcb8-8fbe3d0fc7e9.png', '7');
INSERT INTO `sys_picture` VALUES ('40', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/445dc5d2-6be6-4016-928c-153c7909e3fc.png', '8');
INSERT INTO `sys_picture` VALUES ('41', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/72f5429e-3f85-40fd-8406-ac7b69952380.png', '8');
INSERT INTO `sys_picture` VALUES ('48', '1', '/pic1', '5');
INSERT INTO `sys_picture` VALUES ('49', '1', '/pic2', '5');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `state` tinyint(4) NOT NULL COMMENT '状态',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否系统内置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', '1', '1', 'admin', '2022-12-17 15:39:13', null, null, null, '0');
INSERT INTO `sys_role` VALUES ('2', '商家', 'merchants', '10', '1', 'admin', '2022-12-17 15:41:34', 'admin', '2022-12-22 11:20:58', '', '1');
INSERT INTO `sys_role` VALUES ('3', '审核管理员', 'audit_admin', '20', '1', 'admin', '2022-12-17 15:42:08', 'admin', '2022-12-19 18:30:53', '', '0');
INSERT INTO `sys_role` VALUES ('4', '测试角色', 'test_role', '1', '1', 'admin', '2022-12-28 10:17:57', 'admin', '2022-12-28 10:21:11', '', '0');

-- ----------------------------
-- Table structure for sys_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `auth_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色-权限';

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES ('1', '1', '1');
INSERT INTO `sys_role_auth` VALUES ('2', '1', '2');
INSERT INTO `sys_role_auth` VALUES ('3', '1', '3');
INSERT INTO `sys_role_auth` VALUES ('4', '1', '4');
INSERT INTO `sys_role_auth` VALUES ('5', '1', '5');
INSERT INTO `sys_role_auth` VALUES ('6', '1', '6');
INSERT INTO `sys_role_auth` VALUES ('7', '1', '7');
INSERT INTO `sys_role_auth` VALUES ('8', '1', '8');
INSERT INTO `sys_role_auth` VALUES ('9', '1', '9');
INSERT INTO `sys_role_auth` VALUES ('10', '1', '10');
INSERT INTO `sys_role_auth` VALUES ('11', '1', '11');
INSERT INTO `sys_role_auth` VALUES ('12', '1', '12');
INSERT INTO `sys_role_auth` VALUES ('13', '1', '13');
INSERT INTO `sys_role_auth` VALUES ('14', '1', '14');
INSERT INTO `sys_role_auth` VALUES ('15', '1', '15');
INSERT INTO `sys_role_auth` VALUES ('16', '1', '16');
INSERT INTO `sys_role_auth` VALUES ('17', '1', '17');
INSERT INTO `sys_role_auth` VALUES ('18', '1', '18');
INSERT INTO `sys_role_auth` VALUES ('19', '1', '19');
INSERT INTO `sys_role_auth` VALUES ('20', '1', '20');
INSERT INTO `sys_role_auth` VALUES ('21', '1', '21');
INSERT INTO `sys_role_auth` VALUES ('22', '1', '22');
INSERT INTO `sys_role_auth` VALUES ('47', '3', '10');
INSERT INTO `sys_role_auth` VALUES ('48', '3', '8');
INSERT INTO `sys_role_auth` VALUES ('49', '3', '25');
INSERT INTO `sys_role_auth` VALUES ('50', '3', '20');
INSERT INTO `sys_role_auth` VALUES ('51', '3', '21');
INSERT INTO `sys_role_auth` VALUES ('52', '3', '22');
INSERT INTO `sys_role_auth` VALUES ('68', '2', '5');
INSERT INTO `sys_role_auth` VALUES ('69', '2', '7');
INSERT INTO `sys_role_auth` VALUES ('70', '2', '30');
INSERT INTO `sys_role_auth` VALUES ('71', '2', '32');
INSERT INTO `sys_role_auth` VALUES ('72', '2', '8');
INSERT INTO `sys_role_auth` VALUES ('73', '2', '25');
INSERT INTO `sys_role_auth` VALUES ('74', '2', '26');
INSERT INTO `sys_role_auth` VALUES ('75', '2', '27');
INSERT INTO `sys_role_auth` VALUES ('76', '2', '28');
INSERT INTO `sys_role_auth` VALUES ('77', '2', '29');
INSERT INTO `sys_role_auth` VALUES ('78', '2', '10');
INSERT INTO `sys_role_auth` VALUES ('79', '2', '14');
INSERT INTO `sys_role_auth` VALUES ('80', '2', '35');
INSERT INTO `sys_role_auth` VALUES ('81', '2', '36');
INSERT INTO `sys_role_auth` VALUES ('82', '2', '37');
INSERT INTO `sys_role_auth` VALUES ('83', '2', '39');
INSERT INTO `sys_role_auth` VALUES ('96', '4', '1');
INSERT INTO `sys_role_auth` VALUES ('97', '4', '2');
INSERT INTO `sys_role_auth` VALUES ('98', '4', '3');
INSERT INTO `sys_role_auth` VALUES ('99', '4', '4');
INSERT INTO `sys_role_auth` VALUES ('100', '4', '23');
INSERT INTO `sys_role_auth` VALUES ('101', '4', '24');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `state` tinyint(4) NOT NULL COMMENT '状态',
  `system_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否系统内置',
  `creator` varchar(50) NOT NULL COMMENT '创建账号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `merchant_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否商家账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$IPnALJm4UjWekCNwlwNRq.lZSbjVS3YjH2gloSZVwr9aPq5g2OVAC', '', '系统管理员', '', '', '1', '1', '0', 'admin', '2022-12-17 15:44:57', 'admin', '2022-12-19 16:50:55', '', '0');
INSERT INTO `sys_user` VALUES ('2', 'audit', '$2a$10$.IpZq5BTl9LRS3/sbVihEOhzpeBeI.9.rOxMz7aM7vmtaXf2zApRa', null, '审核管理员', null, null, null, '1', '0', 'admin', '2022-12-17 15:45:39', null, null, null, '0');
INSERT INTO `sys_user` VALUES ('3', 'merchants', '$2a$10$JA04ZCPfo9Hymf6/8uehmeigau8UvGnoupu2ZqNJ8/XZ7sKvISVmG', null, null, null, null, null, '1', '0', 'admin', '2022-12-19 17:04:32', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('4', 'test', '$2a$10$TAVLfRiiP1Em5s/QtjOO5uT0J/GBPHUAM4Ph4gosW/HsdDYFcvyDu', '', '', '', '', '1', '1', '0', 'admin', '2022-12-28 10:21:33', 'admin', '2022-12-28 10:40:10', '123456', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '2', '3');
INSERT INTO `sys_user_role` VALUES ('7', '1', '1');
INSERT INTO `sys_user_role` VALUES ('8', '3', '2');
INSERT INTO `sys_user_role` VALUES ('17', '4', '4');

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '活动名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `city` varchar(50) NOT NULL COMMENT '城市',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `content_id` bigint(20) NOT NULL COMMENT '图片详情',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `lng` decimal(20,7) NOT NULL COMMENT '经度',
  `lat` decimal(20,7) NOT NULL COMMENT '纬度',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id(预留)',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='活动';

-- ----------------------------
-- Records of t_activity
-- ----------------------------
INSERT INTO `t_activity` VALUES ('1', '活动1', '/p', 'km', 'kmkm', '5', '2022-11-30 09:04:54', '2022-12-12 09:04:57', '102.7200000', '25.0200000', null, '滑板');
INSERT INTO `t_activity` VALUES ('2', '活动2', '/爬', 'kk', 'kkkk', '5', '2022-11-30 09:05:40', '2022-11-30 09:05:42', '102.7300000', '25.0300000', null, '飞盘');
INSERT INTO `t_activity` VALUES ('4', '1', 'http://rmm29sxv4.hn-bkt.clouddn.com/33ac3010-7915-4c12-a27e-281660cdae9b.png', '云南省昆明市', '云南省昆明市呈贡区七甸街道兴进路大哨工业区', '12', '2022-12-30 00:00:00', '2022-12-31 00:00:00', '102.9756670', '24.9544970', '0', '滑板');

-- ----------------------------
-- Table structure for t_chat_group
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_group`;
CREATE TABLE `t_chat_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '群主',
  `admin_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '管理员',
  `name` varchar(50) NOT NULL COMMENT '群名称',
  `photo` varchar(200) DEFAULT NULL COMMENT '群头像',
  `im_group_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='群聊';

-- ----------------------------
-- Records of t_chat_group
-- ----------------------------
INSERT INTO `t_chat_group` VALUES ('2', '1', '1', 'zyh创建的群聊2', null, '');

-- ----------------------------
-- Table structure for t_chat_users
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_users`;
CREATE TABLE `t_chat_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL COMMENT '群id',
  `im_group_id` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `im_user_id` varchar(255) NOT NULL,
  `role` tinyint(4) NOT NULL COMMENT '普通用户,群主,管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='群聊-用户';

-- ----------------------------
-- Records of t_chat_users
-- ----------------------------
INSERT INTO `t_chat_users` VALUES ('3', '2', '', '1', '', '2');
INSERT INTO `t_chat_users` VALUES ('4', '2', '', '4', '', '1');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchants_id` bigint(20) NOT NULL COMMENT '商家id',
  `name` varchar(50) NOT NULL COMMENT '课程名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `price` decimal(22,2) NOT NULL COMMENT '价格',
  `numbers` int(11) NOT NULL DEFAULT '0' COMMENT '总人数',
  `content_id` bigint(20) NOT NULL COMMENT '内容',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `signup_num` int(11) NOT NULL DEFAULT '0' COMMENT '已报名人数',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='课程';

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1', '1', '课程1', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '100.00', '10', '16', '2022-11-30 12:27:36', '2022-11-30 12:27:38', '1', null, '滑板', '1', null);
INSERT INTO `t_course` VALUES ('2', '2', '客户才能', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '10.00', '20', '16', '2022-11-30 12:28:06', '2022-11-30 12:28:08', '1', '0', '滑板', '1', '222');
INSERT INTO `t_course` VALUES ('4', '3', '课程111', 'http://rmm29sxv4.hn-bkt.clouddn.com/5918f311-ee44-47fc-ba92-841851f55ec5.png', '10.00', '2', '20', '2022-12-22 00:00:00', '2022-12-23 00:00:00', '1', null, '滑板', '1', '不通过');

-- ----------------------------
-- Table structure for t_course_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_course_teacher`;
CREATE TABLE `t_course_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `teacher_id` bigint(20) NOT NULL COMMENT '导师id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_course_teacher
-- ----------------------------
INSERT INTO `t_course_teacher` VALUES ('2', '1', '1');
INSERT INTO `t_course_teacher` VALUES ('5', '2', '3');
INSERT INTO `t_course_teacher` VALUES ('9', '4', '4');
INSERT INTO `t_course_teacher` VALUES ('10', '4', '5');

-- ----------------------------
-- Table structure for t_integral_task
-- ----------------------------
DROP TABLE IF EXISTS `t_integral_task`;
CREATE TABLE `t_integral_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '任务名称',
  `integral` int(11) NOT NULL COMMENT '积分值',
  `overlay_value` int(11) NOT NULL COMMENT '累加值(连续签到累加送积分)',
  `max_value` int(11) NOT NULL COMMENT '最大值(连续签到最多送)',
  `task_num` int(11) NOT NULL COMMENT '任务次数/每天',
  `task_total` int(11) NOT NULL DEFAULT '0' COMMENT '任务总量(全部视频量)',
  `expire_type` tinyint(4) NOT NULL COMMENT '有效期(无限期,有限期)',
  `expire_date` date DEFAULT NULL COMMENT '失效时间',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(启用,禁用,过期)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='积分任务';

-- ----------------------------
-- Records of t_integral_task
-- ----------------------------
INSERT INTO `t_integral_task` VALUES ('1', 'signin', '签到任务', '2', '1', '4', '1', '1', '2', null, '1');
INSERT INTO `t_integral_task` VALUES ('2', 'share', '分享任务', '1', '1', '1', '2', '1', '2', '2022-12-23', '1');
INSERT INTO `t_integral_task` VALUES ('3', 'watch_one_ad', '观看单个广告', '1', '1', '1', '2', '1', '2', '2022-12-16', '1');
INSERT INTO `t_integral_task` VALUES ('4', 'watch_all_ad', '观看所有广告', '5', '1', '1', '1', '4', '2', '2022-12-31', '1');

-- ----------------------------
-- Table structure for t_merchants
-- ----------------------------
DROP TABLE IF EXISTS `t_merchants`;
CREATE TABLE `t_merchants` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `service` varchar(255) NOT NULL COMMENT '服务',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(启用/禁用)',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `phone` char(11) NOT NULL COMMENT '联系人电话',
  `email` varchar(50) DEFAULT NULL COMMENT '联系人邮箱',
  `business_license` varchar(200) NOT NULL COMMENT '营业执照',
  `lng` decimal(20,7) NOT NULL COMMENT '经度',
  `lat` decimal(20,7) NOT NULL COMMENT '纬度',
  `open_times` varchar(50) DEFAULT NULL COMMENT '营业时间',
  `content_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '图文内容',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='商户';

-- ----------------------------
-- Records of t_merchants
-- ----------------------------
INSERT INTO `t_merchants` VALUES ('1', '商户1', '/pic', 'km', 'fuwu', '2', 'zyh', '15288192183', 'qq.com', 'license', '102.7200000', '25.0200000', '', '5', '1111');
INSERT INTO `t_merchants` VALUES ('2', '商户2', '/pic2', 'km2', 'fuwu2', '1', 'zyh2', '153', 'qq.com2', 'license2', '102.7300000', '25.0300000', '', '5', null);
INSERT INTO `t_merchants` VALUES ('3', '商户1', 'http://rmm29sxv4.hn-bkt.clouddn.com/25d92cc6-8bb7-4955-a021-3689dd9d6d72.png', '云南省昆明市宜良县汤池街道阳宗海', '服务', '2', 'farkle', '15288192183', '11@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/6aaf8feb-6798-4cc6-ae93-6454c113fa7f.png', '103.0093130', '24.9171390', '00:11-23:11', '21', '222');
INSERT INTO `t_merchants` VALUES ('4', '商户2', 'http://rmm29sxv4.hn-bkt.clouddn.com/4a9f6da8-9043-431b-ad76-c3058fb037a1.png', '云南省昆明市呈贡区洛羊街道鸿运大道辅路中铁十六局', '1', '2', '2', '11111111111', '4@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/e542dd14-7a27-491d-b0ea-2c57bb4b8e33.png', '102.8252920', '24.9096660', '01:13-02:13', '7', ' ');
INSERT INTO `t_merchants` VALUES ('5', '1', 'http://rmm29sxv4.hn-bkt.clouddn.com/2615c7e3-b685-4c82-8f70-f05a48e284d0.png', '云南省昆明市官渡区官渡街道云南艺术家园区C区', '1', '1', '1', '11111111111', null, 'http://rmm29sxv4.hn-bkt.clouddn.com/31a54d10-5444-4836-9d3c-59ca513ec803.png', '102.7284750', '24.9370650', '01:15-02:15', '8', null);
INSERT INTO `t_merchants` VALUES ('6', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/dd62f031-9159-41b7-bdef-0b1f37eb0382.png', '云南省昆明市呈贡区七甸街道松马段', '2', '2', '2', '22222222222', null, 'http://rmm29sxv4.hn-bkt.clouddn.com/26a40324-059d-4d84-856e-760102d376c3.png', '102.9076890', '24.9059290', '01:24-02:24', '9', '11');
INSERT INTO `t_merchants` VALUES ('7', '33', 'http://rmm29sxv4.hn-bkt.clouddn.com/8d5ca677-ae71-44d6-835b-e9e9e78a9d3c.png', '云南省昆明市西山区福海街道建水紫陶(金荷路店)长竹沟新村', '33', '2', '3', '33333333333', '3@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/5885e825-c58d-4198-a7a4-fd19b37ba435.png', '102.6749170', '24.9918440', '01:38-02:38', '10', '111');

-- ----------------------------
-- Table structure for t_party
-- ----------------------------
DROP TABLE IF EXISTS `t_party`;
CREATE TABLE `t_party` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `brief_introduction` varchar(255) NOT NULL COMMENT '简介',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `content_id` bigint(20) NOT NULL COMMENT '内容',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_party
-- ----------------------------
INSERT INTO `t_party` VALUES ('3', 'party1x', 'http://rmm29sxv4.hn-bkt.clouddn.com/ef52fc32-a825-4445-a4a4-83f51551972a.png', '1111xx', '11111111111x', '16', '3', '2022-12-12 10:46:26');
INSERT INTO `t_party` VALUES ('4', 'party', 'http://rmm29sxv4.hn-bkt.clouddn.com/2820b5b7-eca4-4d61-bb6e-b5a2f6029762.png', '简介简介休息休息', 'km', '18', '1', '2022-12-22 10:33:13');
INSERT INTO `t_party` VALUES ('5', 'party-null', 'http://rmm29sxv4.hn-bkt.clouddn.com/5d4ac361-1737-4b51-b959-c6a7269e3f4a.png', 'nullnull', 'kmkm', '19', null, '2022-12-22 10:34:12');

-- ----------------------------
-- Table structure for t_site
-- ----------------------------
DROP TABLE IF EXISTS `t_site`;
CREATE TABLE `t_site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `lng` decimal(20,7) NOT NULL COMMENT '经度',
  `lat` decimal(20,7) NOT NULL COMMENT '纬度',
  `open_type` tinyint(4) NOT NULL COMMENT '开放类型(全天/时段)',
  `start_time` varchar(10) DEFAULT NULL,
  `end_time` varchar(10) DEFAULT NULL,
  `recommended` double NOT NULL COMMENT '推荐指数',
  `content_id` bigint(20) NOT NULL COMMENT '内容id',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id(备用)',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `city` varchar(50) NOT NULL COMMENT '城市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='场地';

-- ----------------------------
-- Records of t_site
-- ----------------------------
INSERT INTO `t_site` VALUES ('3', '场地1x', 'http://rmm29sxv4.hn-bkt.clouddn.com/62689f37-cee8-4092-84d7-ce0a112e8e02.png', '云南省昆明市呈贡区七甸街道松茂水库段', '102.9063160', '24.8922270', '2', '02:01', '11:01', '4.5', '13', '0', '陆冲', '云南省昆明市');
INSERT INTO `t_site` VALUES ('4', '222222', 'http://rmm29sxv4.hn-bkt.clouddn.com/f2ebd90f-494f-4a01-8c9c-55ef3e6276ed.png', '云南省昆明市宜良县汤池街道煤碳箐', '103.0463920', '24.9320830', '1', null, null, '3', '14', null, '滑板', '云南省昆明市');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `tag` varchar(50) NOT NULL COMMENT '标签',
  `sort` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(50) NOT NULL COMMENT '创建用户账号',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='app用户-标签';

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('17', '0', '美学', '1', '2022-12-21 14:01:56', '2022-12-21 14:02:07', 'admin', 'admin');
INSERT INTO `t_tag` VALUES ('18', '0', '滑板', '2', '2022-12-21 14:03:21', null, 'admin', null);
INSERT INTO `t_tag` VALUES ('19', '0', '音乐', null, '2022-12-21 14:03:29', null, 'admin', null);
INSERT INTO `t_tag` VALUES ('20', '17', 'Goth', '1', '2022-12-21 14:04:37', null, 'admin', null);
INSERT INTO `t_tag` VALUES ('21', '17', '工人阶级哥特', null, '2022-12-21 14:04:50', null, 'admin', null);

-- ----------------------------
-- Table structure for t_tattoo
-- ----------------------------
DROP TABLE IF EXISTS `t_tattoo`;
CREATE TABLE `t_tattoo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `phone` char(11) NOT NULL COMMENT '电话',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `content` text NOT NULL COMMENT '介绍',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `fulltext_name_content` (`name`,`content`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='文身师';

-- ----------------------------
-- Records of t_tattoo
-- ----------------------------
INSERT INTO `t_tattoo` VALUES ('5', 'zyhx', 'http://rmm29sxv4.hn-bkt.clouddn.com/666479f4-d568-49a9-ab23-e1d11ca77ade.png', '21111111111', '1', '222222222222x', '3333333333333x', '5', '2022-12-11 17:04:38');
INSERT INTO `t_tattoo` VALUES ('6', '1', 'http://rmm29sxv4.hn-bkt.clouddn.com/b211765b-1398-4a12-83d2-deedcbe30a37.png', '11111111111', '1', '1111', '11111', '1', '2022-12-11 19:30:45');
INSERT INTO `t_tattoo` VALUES ('7', '2', 'http://rmm29sxv4.hn-bkt.clouddn.com/ffdd5405-43ee-4b1c-9e77-8fdfa32237d2.png', '11111111111', '1', '1', '1', '1', '2022-12-11 19:31:29');
INSERT INTO `t_tattoo` VALUES ('8', '4x', 'http://rmm29sxv4.hn-bkt.clouddn.com/b384bad1-a60d-4ab8-86d5-28e4ce15c3d7.png', '55555555555', '2', '5', '5', '5', '2022-12-17 21:25:14');

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `photo` varchar(200) NOT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `skills` varchar(255) NOT NULL COMMENT '技能点(逗号分隔)',
  `merchats_id` bigint(20) NOT NULL COMMENT '商家id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `phone` char(11) NOT NULL COMMENT '联系电话',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '启用状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='导师';

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES ('2', 't2', 'http://rmm29sxv4.hn-bkt.clouddn.com/60cc509c-7464-4dce-871e-cc3fa4c597a6.png', '2', '3,4', '0', '2022-12-11 10:18:16', '12345678999', '1');
INSERT INTO `t_teacher` VALUES ('3', 'zyh2', 'http://rmm29sxv4.hn-bkt.clouddn.com/60cc509c-7464-4dce-871e-cc3fa4c597a6.png', '1', '1,3,4', '0', '2022-12-11 11:53:42', '15288192112', '1');
INSERT INTO `t_teacher` VALUES ('5', '导师2', 'http://rmm29sxv4.hn-bkt.clouddn.com/6da07df2-1699-41df-b84a-c5f86710f195.png', '2', '1,3', '3', '2022-12-22 11:16:22', '22222222222', '1');

-- ----------------------------
-- Table structure for t_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic` varchar(50) NOT NULL COMMENT '话题',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_topic` (`topic`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='话题';

-- ----------------------------
-- Records of t_topic
-- ----------------------------
INSERT INTO `t_topic` VALUES ('1', '游戏', '2022-11-29 00:02:08', '1');
INSERT INTO `t_topic` VALUES ('2', '音乐', '2022-11-29 00:03:07', '1');
INSERT INTO `t_topic` VALUES ('3', '美食', '2022-11-29 00:03:12', '1');
INSERT INTO `t_topic` VALUES ('4', 't1', '2022-12-21 14:46:02', '1');

-- ----------------------------
-- Table structure for t_voucher
-- ----------------------------
DROP TABLE IF EXISTS `t_voucher`;
CREATE TABLE `t_voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `price` decimal(22,2) DEFAULT NULL COMMENT '价值',
  `integral` int(11) NOT NULL COMMENT '兑换所需积分',
  `numbers` int(11) NOT NULL COMMENT '库存',
  `expire_date` date NOT NULL COMMENT '有效期',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(正常/过期/下架)',
  `content` varchar(500) NOT NULL COMMENT '使用说明',
  `shelve_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  `total_numbers` int(11) NOT NULL COMMENT '总量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='代金券';

-- ----------------------------
-- Records of t_voucher
-- ----------------------------
INSERT INTO `t_voucher` VALUES ('3', '10员兑换券', 'http://rmm29sxv4.hn-bkt.clouddn.com/c0e7c340-4349-4c10-b274-f605f49213e8.png', null, '100', '10', '2022-12-12', '1', '111111111', '2022-12-12 22:33:42', '10');
INSERT INTO `t_voucher` VALUES ('5', '1元', 'http://rmm29sxv4.hn-bkt.clouddn.com/577a41dc-794d-4767-bfc9-1742a0d84f85.png', null, '1', '8', '2023-12-30', '1', '111111111111111111111', '2022-12-22 13:45:38', '10');

-- ----------------------------
-- Table structure for t_works_comments
-- ----------------------------
DROP TABLE IF EXISTS `t_works_comments`;
CREATE TABLE `t_works_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `works_id` bigint(20) NOT NULL COMMENT '作品id',
  `content` varchar(500) NOT NULL COMMENT '评论',
  `thumbs_up_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `has_read` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `comments_user_id` bigint(20) NOT NULL COMMENT '被评论用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='作品-评论';

-- ----------------------------
-- Records of t_works_comments
-- ----------------------------
INSERT INTO `t_works_comments` VALUES ('1', '0', '1', '1', '不错', '0', '2022-11-28 17:12:52', '0', '1');
INSERT INTO `t_works_comments` VALUES ('2', '1', '1', '1', '真的不错', '0', '2022-11-28 17:56:45', '0', '1');
INSERT INTO `t_works_comments` VALUES ('3', '1', '1', '1', '1', '0', '2022-11-28 17:56:54', '0', '1');
INSERT INTO `t_works_comments` VALUES ('4', '0', '1', '0', '123', '0', '2022-11-28 23:12:03', '0', '1');
INSERT INTO `t_works_comments` VALUES ('5', '0', '1', '1', '123', '0', '2022-11-28 23:13:06', '0', '1');
INSERT INTO `t_works_comments` VALUES ('6', '5', '1', '1', '456', '0', '2022-11-28 23:23:20', '0', '1');
INSERT INTO `t_works_comments` VALUES ('7', '5', '1', '1', '789', '1', '2022-11-28 23:24:10', '0', '1');
INSERT INTO `t_works_comments` VALUES ('8', '0', '1', '1', '1来评论了', '0', '2022-12-04 00:05:19', '1', '1');
INSERT INTO `t_works_comments` VALUES ('9', '0', '4', '1', '5来评论了', '0', '2022-12-04 00:05:50', '1', '1');
INSERT INTO `t_works_comments` VALUES ('10', '9', '4', '1', '5的子评论', '0', '2022-12-04 00:08:04', '1', '4');
INSERT INTO `t_works_comments` VALUES ('11', '9', '2', '1', '3也来个子评论', '0', '2022-12-04 00:09:44', '0', '4');
INSERT INTO `t_works_comments` VALUES ('12', '0', '1', '4', '评论1', '0', '2022-12-21 16:43:09', '0', '4');
INSERT INTO `t_works_comments` VALUES ('13', '0', '1', '4', '评论2', '0', '2022-12-21 16:43:18', '0', '4');
INSERT INTO `t_works_comments` VALUES ('14', '12', '1', '4', '回复12-1', '0', '2022-12-21 16:46:24', '1', '1');
INSERT INTO `t_works_comments` VALUES ('15', '12', '1', '4', '回复12-2', '0', '2022-12-21 16:46:31', '1', '1');
INSERT INTO `t_works_comments` VALUES ('16', '0', '1', '4', '评论2', '0', '2022-12-21 16:46:40', '0', '4');
INSERT INTO `t_works_comments` VALUES ('17', '12', '4', '4', '回复12-3', '1', '2022-12-21 17:07:10', '1', '1');

-- ----------------------------
-- Table structure for t_works_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_works_topic`;
CREATE TABLE `t_works_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `works_id` bigint(20) NOT NULL COMMENT '作品id',
  `topic_id` bigint(20) DEFAULT NULL COMMENT '话题id',
  `topic` varchar(50) NOT NULL COMMENT '话题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='作品-话题(方便搜索)';

-- ----------------------------
-- Records of t_works_topic
-- ----------------------------
INSERT INTO `t_works_topic` VALUES ('1', '1', '2', '音乐');
INSERT INTO `t_works_topic` VALUES ('20', '5', null, 't1');
INSERT INTO `t_works_topic` VALUES ('21', '5', null, 't2');

-- ----------------------------
-- Table structure for user_activity
-- ----------------------------
DROP TABLE IF EXISTS `user_activity`;
CREATE TABLE `user_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户-报名活动';

-- ----------------------------
-- Records of user_activity
-- ----------------------------
INSERT INTO `user_activity` VALUES ('2', '2', '2');
INSERT INTO `user_activity` VALUES ('3', '1', '1');
INSERT INTO `user_activity` VALUES ('4', '1', '4');

-- ----------------------------
-- Table structure for user_attention
-- ----------------------------
DROP TABLE IF EXISTS `user_attention`;
CREATE TABLE `user_attention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `attention_user_id` bigint(20) NOT NULL COMMENT '被关注用户id',
  `attention_type` tinyint(4) NOT NULL COMMENT '关注类型(1:主动关注,10:被关注)',
  `new_attention` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否新增(针对被关注)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用户关注';

-- ----------------------------
-- Records of user_attention
-- ----------------------------
INSERT INTO `user_attention` VALUES ('1', '1', '2', '1', '1');
INSERT INTO `user_attention` VALUES ('2', '2', '1', '10', '1');
INSERT INTO `user_attention` VALUES ('5', '4', '1', '1', '1');
INSERT INTO `user_attention` VALUES ('6', '1', '4', '10', '1');
INSERT INTO `user_attention` VALUES ('7', '1', '4', '1', '1');
INSERT INTO `user_attention` VALUES ('8', '4', '1', '10', '1');

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `works_id` bigint(20) NOT NULL COMMENT '作品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='用户-收藏';

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES ('8', '1', '5');
INSERT INTO `user_collection` VALUES ('12', '1', '1');
INSERT INTO `user_collection` VALUES ('14', '1', '4');

-- ----------------------------
-- Table structure for user_course
-- ----------------------------
DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户-课程报名';

-- ----------------------------
-- Records of user_course
-- ----------------------------
INSERT INTO `user_course` VALUES ('1', '1', '1', '2022-12-12 16:41:31');
INSERT INTO `user_course` VALUES ('2', '1', '4', '2022-12-22 11:36:03');

-- ----------------------------
-- Table structure for user_integrals
-- ----------------------------
DROP TABLE IF EXISTS `user_integrals`;
CREATE TABLE `user_integrals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `task_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '任务id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `integral` int(11) NOT NULL COMMENT '积分',
  `payments` tinyint(4) NOT NULL COMMENT '收支',
  `source` varchar(50) NOT NULL COMMENT '来源',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COMMENT='用户-积分明细';

-- ----------------------------
-- Records of user_integrals
-- ----------------------------
INSERT INTO `user_integrals` VALUES ('41', '1', '3', null, '10', '1', '观看单个广告', '2022-12-02 16:24:46');
INSERT INTO `user_integrals` VALUES ('42', '1', '3', null, '10', '1', '观看单个广告', '2022-12-02 16:24:47');
INSERT INTO `user_integrals` VALUES ('43', '1', '4', null, '100', '1', '观看所有广告', '2022-12-02 16:30:08');
INSERT INTO `user_integrals` VALUES ('44', '1', '0', '2', '11', '2', '11元兑换', '2022-12-03 16:19:32');
INSERT INTO `user_integrals` VALUES ('45', '1', '1', null, '2', '1', '每日签到', '2022-12-22 13:15:51');
INSERT INTO `user_integrals` VALUES ('46', '1', '1', null, '3', '1', '每日签到', '2022-12-22 13:17:08');
INSERT INTO `user_integrals` VALUES ('47', '1', '1', null, '4', '1', '每日签到', '2022-12-22 13:17:52');
INSERT INTO `user_integrals` VALUES ('48', '1', '1', null, '4', '1', '每日签到', '2022-12-22 13:18:21');
INSERT INTO `user_integrals` VALUES ('49', '1', '1', null, '2', '1', '每日签到', '2022-12-22 13:18:52');
INSERT INTO `user_integrals` VALUES ('50', '1', '2', '1', '1', '1', '分享任务', '2022-12-22 13:21:07');
INSERT INTO `user_integrals` VALUES ('51', '1', '2', '1', '1', '1', '分享任务', '2022-12-22 13:21:24');
INSERT INTO `user_integrals` VALUES ('52', '1', '3', null, '1', '1', '观看单个广告', '2022-12-22 13:22:52');
INSERT INTO `user_integrals` VALUES ('53', '1', '3', null, '1', '1', '观看单个广告', '2022-12-22 13:23:16');
INSERT INTO `user_integrals` VALUES ('54', '1', '4', null, '5', '1', '观看所有广告', '2022-12-22 13:24:16');
INSERT INTO `user_integrals` VALUES ('55', '1', '0', '5', '1', '2', '1元兑换', '2022-12-22 13:49:09');
INSERT INTO `user_integrals` VALUES ('56', '1', '0', '5', '1', '2', '1元兑换', '2022-12-22 13:49:11');

-- ----------------------------
-- Table structure for user_merchants_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_merchants_auth`;
CREATE TABLE `user_merchants_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `merchants_type` tinyint(4) NOT NULL COMMENT '商户类型(个人,个体工商户,企业)',
  `merchants_name` varchar(50) NOT NULL COMMENT '商户名称',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `phone` char(11) NOT NULL COMMENT '联系人电话',
  `email` varchar(50) NOT NULL COMMENT '联系人邮箱',
  `business_license` varchar(200) NOT NULL COMMENT '营业执照',
  `auth_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '认证状态(认证中/成功/失败)',
  `auth_message` varchar(200) DEFAULT NULL COMMENT '审核消息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间(审核认证时间)',
  `auth_time` datetime DEFAULT NULL COMMENT '认证时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用户-商户认证';

-- ----------------------------
-- Records of user_merchants_auth
-- ----------------------------
INSERT INTO `user_merchants_auth` VALUES ('3', '2', '1', 'merchantsName', 'contactName', '15288192183', '1227665143@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '3', '3333', '2022-12-13 15:18:50', '2022-12-13 16:13:18');
INSERT INTO `user_merchants_auth` VALUES ('4', '2', '1', 'merchantsName', 'contactName', '15288192183', '1227665143@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '3', '休想', '2022-12-13 15:19:22', '2022-12-13 16:46:03');
INSERT INTO `user_merchants_auth` VALUES ('5', '1', '1', 'merchantsName', 'contactName', '15288192183', '1227665143@qq.com', 'http://rmm29sxv4.hn-bkt.clouddn.com/53e06b22-1b5e-436f-baa4-273dda3a28ff.png', '3', 'xxxxx', '2022-12-13 15:19:49', '2022-12-13 16:46:06');
INSERT INTO `user_merchants_auth` VALUES ('6', '1', '1', 'merchantsName', 'contactName', '15288192183', '1227665143@qq.com', 'https://orgnext.modao.cc/res-img/org/natr/1.jpg', '3', null, '2022-12-13 15:20:06', '2022-12-13 16:46:08');
INSERT INTO `user_merchants_auth` VALUES ('7', '1', '3', 'merchantsName', 'contactName', '11111111111', 'email@qq.com', 'businessLicense', '3', '111', '2022-12-22 14:20:09', '2022-12-22 14:20:19');
INSERT INTO `user_merchants_auth` VALUES ('8', '1', '3', 'merchantsName', 'contactName', '11111111111', 'email@qq.com', 'businessLicense', '2', null, '2022-12-22 14:20:34', '2022-12-22 14:20:54');

-- ----------------------------
-- Table structure for user_notice
-- ----------------------------
DROP TABLE IF EXISTS `user_notice`;
CREATE TABLE `user_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `type` bigint(20) NOT NULL COMMENT '通知类型(上架,审核)',
  `content` varchar(255) NOT NULL COMMENT '通知内容',
  `has_read` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='用户-通知';

-- ----------------------------
-- Records of user_notice
-- ----------------------------
INSERT INTO `user_notice` VALUES ('1', '1', '3', 'farkle关注了您', '1', '2022-12-03 23:20:23');
INSERT INTO `user_notice` VALUES ('2', '4', '3', 'zyh关注了您', '0', '2022-12-03 23:42:26');
INSERT INTO `user_notice` VALUES ('3', '2', '3', 'zyh关注了您', '0', '2022-12-21 14:11:37');
INSERT INTO `user_notice` VALUES ('4', '3', '3', 'zyh关注了您', '0', '2022-12-21 14:11:37');
INSERT INTO `user_notice` VALUES ('5', '1', '3', 'farkle关注了您', '0', '2022-12-21 14:37:13');
INSERT INTO `user_notice` VALUES ('6', '4', '3', 'znew关注了您', '0', '2022-12-21 14:53:49');
INSERT INTO `user_notice` VALUES ('7', '4', '3', 'znew关注了您', '0', '2022-12-21 17:18:09');
INSERT INTO `user_notice` VALUES ('8', '4', '3', 'znew关注了您', '0', '2022-12-21 17:36:03');
INSERT INTO `user_notice` VALUES ('9', '2', '3', 'znew关注了您', '0', '2022-12-21 17:36:18');
INSERT INTO `user_notice` VALUES ('10', '2', '3', 'znew关注了您', '0', '2022-12-21 17:40:56');
INSERT INTO `user_notice` VALUES ('11', '4', '3', 'znew关注了您', '0', '2022-12-21 17:41:01');
INSERT INTO `user_notice` VALUES ('12', '1', '3', 'farkle关注了您', '1', '2022-12-21 17:41:54');
INSERT INTO `user_notice` VALUES ('13', '4', '2', '您的作品[title]未通过审核,原因:没有图片', '0', '2022-12-22 10:10:03');
INSERT INTO `user_notice` VALUES ('14', '4', '2', '您的作品[title]已通过审核', '0', '2022-12-22 10:12:15');
INSERT INTO `user_notice` VALUES ('15', '4', '1', '您的作品[title]被下架,原因:还是没有图片', '0', '2022-12-22 10:12:40');
INSERT INTO `user_notice` VALUES ('16', '4', '3', 'znew关注了您', '0', '2022-12-22 10:14:59');

-- ----------------------------
-- Table structure for user_signin
-- ----------------------------
DROP TABLE IF EXISTS `user_signin`;
CREATE TABLE `user_signin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `signin_date` date NOT NULL COMMENT '签到日期',
  `signin_time` datetime NOT NULL COMMENT '签到时间',
  `integral` int(11) NOT NULL COMMENT '积分',
  `continue_num` int(11) NOT NULL COMMENT '连续签到次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='用户-签到明细';

-- ----------------------------
-- Records of user_signin
-- ----------------------------
INSERT INTO `user_signin` VALUES ('13', '1', '2022-12-22', '2022-12-22 13:15:52', '2', '0');
INSERT INTO `user_signin` VALUES ('14', '1', '2022-12-23', '2022-12-23 13:17:07', '3', '1');
INSERT INTO `user_signin` VALUES ('15', '1', '2022-12-24', '2022-12-24 13:17:47', '4', '2');
INSERT INTO `user_signin` VALUES ('16', '1', '2022-12-25', '2022-12-25 13:18:13', '4', '3');
INSERT INTO `user_signin` VALUES ('17', '1', '2022-12-27', '2022-12-27 13:18:41', '2', '0');

-- ----------------------------
-- Table structure for user_tag
-- ----------------------------
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `tag` varchar(50) NOT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COMMENT='用户-标签';

-- ----------------------------
-- Records of user_tag
-- ----------------------------
INSERT INTO `user_tag` VALUES ('5', '2', 'Goth');
INSERT INTO `user_tag` VALUES ('30', '1', '1');
INSERT INTO `user_tag` VALUES ('31', '1', '2');
INSERT INTO `user_tag` VALUES ('32', '4', 'Goth');

-- ----------------------------
-- Table structure for user_thumbs_up
-- ----------------------------
DROP TABLE IF EXISTS `user_thumbs_up`;
CREATE TABLE `user_thumbs_up` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '点赞用户',
  `thumbsup_user_id` bigint(20) NOT NULL COMMENT '被赞用户',
  `type` tinyint(4) NOT NULL COMMENT '作品/评论',
  `target_id` bigint(20) NOT NULL COMMENT '点赞内容',
  `has_read` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(255) NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='用户-点赞';

-- ----------------------------
-- Records of user_thumbs_up
-- ----------------------------
INSERT INTO `user_thumbs_up` VALUES ('1', '1', '1', '2', '7', '1', '2022-11-28 23:26:14', '');
INSERT INTO `user_thumbs_up` VALUES ('2', '1', '1', '1', '1', '1', '2022-11-28 23:27:14', '');
INSERT INTO `user_thumbs_up` VALUES ('3', '1', '1', '1', '1', '1', '2022-12-03 23:16:09', ' 10分钟芝士卷, note baidu ');
INSERT INTO `user_thumbs_up` VALUES ('4', '4', '1', '1', '1', '1', '2022-12-03 23:19:10', ' 10分钟芝士卷, note baidu ');
INSERT INTO `user_thumbs_up` VALUES ('5', '1', '4', '1', '4', '0', '2022-12-21 16:51:23', 'title');
INSERT INTO `user_thumbs_up` VALUES ('6', '1', '4', '1', '4', '0', '2022-12-21 16:51:23', 'title');
INSERT INTO `user_thumbs_up` VALUES ('7', '1', '4', '2', '17', '0', '2022-12-21 17:07:27', '回复12-3');
INSERT INTO `user_thumbs_up` VALUES ('8', '1', '4', '1', '5', '0', '2022-12-22 14:08:23', 'title');
INSERT INTO `user_thumbs_up` VALUES ('9', '1', '4', '1', '5', '0', '2022-12-22 14:11:12', 'title');

-- ----------------------------
-- Table structure for user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `user_voucher`;
CREATE TABLE `user_voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `code` varchar(50) DEFAULT NULL COMMENT '代金券码',
  `voucher_id` bigint(20) NOT NULL COMMENT '代金券id',
  `voucher_name` varchar(50) NOT NULL COMMENT '代金券名称',
  `voucher_price` decimal(22,2) DEFAULT NULL COMMENT '代金券价值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间(兑换时间)',
  `verification_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '核销状态(待核销/已核销)',
  `voucher_integral` int(11) NOT NULL COMMENT '所需积分',
  `content` varchar(500) NOT NULL COMMENT '使用说明',
  `expire_date` date NOT NULL COMMENT '有效期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户-代金券(兑换记录)';

-- ----------------------------
-- Records of user_voucher
-- ----------------------------
INSERT INTO `user_voucher` VALUES ('1', '1', '55332622', '2', '11元', '11.00', '2022-12-03 16:19:32', '2', '11', '休息休息', '2022-12-03');
INSERT INTO `user_voucher` VALUES ('2', '1', 'xx', '2', '11员', null, '2022-12-13 12:01:14', '3', '11', '6666', '2022-12-29');
INSERT INTO `user_voucher` VALUES ('3', '1', null, '5', '1元', null, '2022-12-22 13:49:09', '1', '1', '111111111111111111111', '2023-12-30');
INSERT INTO `user_voucher` VALUES ('4', '1', '12046490', '5', '1元', null, '2022-12-22 13:49:11', '2', '1', '111111111111111111111', '2023-12-30');

-- ----------------------------
-- Table structure for user_wacthad
-- ----------------------------
DROP TABLE IF EXISTS `user_wacthad`;
CREATE TABLE `user_wacthad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `ad` varchar(255) DEFAULT NULL COMMENT '广告',
  `create_date` date NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `completed` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COMMENT='观看广告';

-- ----------------------------
-- Records of user_wacthad
-- ----------------------------
INSERT INTO `user_wacthad` VALUES ('40', '1', 'ad2', '2022-12-02', '2022-12-02 16:24:46', '1');
INSERT INTO `user_wacthad` VALUES ('41', '1', 'ad2', '2022-12-02', '2022-12-02 16:24:47', '1');
INSERT INTO `user_wacthad` VALUES ('42', '1', 'ad2', '2022-12-02', '2022-12-02 16:24:48', '1');
INSERT INTO `user_wacthad` VALUES ('43', '1', 'ad2', '2022-12-02', '2022-12-02 16:25:36', '1');
INSERT INTO `user_wacthad` VALUES ('50', '1', 'ad2', '2022-12-02', '2022-12-02 16:29:50', '1');
INSERT INTO `user_wacthad` VALUES ('51', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:31', '0');
INSERT INTO `user_wacthad` VALUES ('52', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:49', '0');
INSERT INTO `user_wacthad` VALUES ('53', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:50', '0');
INSERT INTO `user_wacthad` VALUES ('54', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:50', '0');
INSERT INTO `user_wacthad` VALUES ('55', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:51', '0');
INSERT INTO `user_wacthad` VALUES ('56', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:51', '0');
INSERT INTO `user_wacthad` VALUES ('57', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:51', '0');
INSERT INTO `user_wacthad` VALUES ('58', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:51', '0');
INSERT INTO `user_wacthad` VALUES ('59', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:52', '0');
INSERT INTO `user_wacthad` VALUES ('60', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:52', '0');
INSERT INTO `user_wacthad` VALUES ('61', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:52', '0');
INSERT INTO `user_wacthad` VALUES ('62', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:52', '0');
INSERT INTO `user_wacthad` VALUES ('63', '1', 'ad2', '2022-12-02', '2022-12-02 16:30:52', '0');
INSERT INTO `user_wacthad` VALUES ('64', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:35', '0');
INSERT INTO `user_wacthad` VALUES ('65', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:36', '0');
INSERT INTO `user_wacthad` VALUES ('66', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:36', '0');
INSERT INTO `user_wacthad` VALUES ('67', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:36', '0');
INSERT INTO `user_wacthad` VALUES ('68', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:36', '0');
INSERT INTO `user_wacthad` VALUES ('69', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:36', '0');
INSERT INTO `user_wacthad` VALUES ('70', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('71', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('72', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('73', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('74', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('75', '1', 'ad2', '2022-12-02', '2022-12-02 16:33:37', '0');
INSERT INTO `user_wacthad` VALUES ('76', '1', '/ad', '2022-12-22', '2022-12-22 13:22:52', '1');
INSERT INTO `user_wacthad` VALUES ('77', '1', '/ad2', '2022-12-22', '2022-12-22 13:23:16', '1');
INSERT INTO `user_wacthad` VALUES ('78', '1', '/ad2', '2022-12-22', '2022-12-22 13:23:56', '1');
INSERT INTO `user_wacthad` VALUES ('79', '1', '/ad2', '2022-12-22', '2022-12-22 13:24:15', '1');

-- ----------------------------
-- Table structure for user_works
-- ----------------------------
DROP TABLE IF EXISTS `user_works`;
CREATE TABLE `user_works` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `picture` varchar(200) NOT NULL COMMENT '图片',
  `video` varchar(200) DEFAULT NULL COMMENT '视频',
  `content` varchar(2000) NOT NULL COMMENT '内容',
  `lng` decimal(20,7) DEFAULT NULL COMMENT '经度',
  `lat` decimal(20,7) DEFAULT NULL COMMENT '纬度',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(正常,审核中,审核失败,下架)',
  `audit_message` varchar(200) DEFAULT NULL COMMENT '审核消息',
  `thumbs_up_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `comments_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
  `topic` varchar(255) DEFAULT NULL COMMENT '话题(逗号分隔,方便展示)',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `full_text_title_topic` (`title`,`topic`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='用户-作品';

-- ----------------------------
-- Records of user_works
-- ----------------------------
INSERT INTO `user_works` VALUES ('1', '1', ' 10分钟芝士卷, note baidu ', 'http://prototype.ynqiyue.com/subculture/App/images/%E9%A6%96%E9%A1%B5/u322.svg', 'http://rmm29sxv4.hn-bkt.clouddn.com/data.mp4', '4', '102.7100000', '25.0100000', '昆明', 'lkk', '2022-11-27 16:42:26', '2022-11-28 16:26:22', '1', '1', '3', '7', '美食');
INSERT INTO `user_works` VALUES ('2', '1', ' 10分钟芝  士卷, note baid', 'http://prototype.ynqiyue.com/subculture/App/images/%E9%A6%96%E9%A1%B5/u322.svg', '', '0', '102.7200000', '25.0200000', null, null, '2022-11-28 09:19:12', '2022-11-28 16:26:25', '4', '没有城市', '0', '0', '美食');
INSERT INTO `user_works` VALUES ('5', '4', 'title', '/pic1', '/view', 'content', '102.7300000', '25.0300000', 'city', 'address', '2022-12-22 10:09:44', '2022-12-22 10:12:03', '1', '还是没有图片', '2', '0', 't1,t2');
