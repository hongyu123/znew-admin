SET FOREIGN_KEY_CHECKS = 0;
SET NAMES utf8mb4;
-- sys_admin_log DDL
CREATE TABLE `sys_admin_log` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`title` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "标题",
`method` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "方法名",
`account` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "管理员账号",
`request_url` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "请求url",
`request_ip` VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`request_body` VARCHAR(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "请求体",
`request_header` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "请求头",
`response` VARCHAR(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "响应",
`state` TINYINT NOT NULL DEFAULT 1 Comment "状态(1正常,0异常)",
`message` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "异常信息",
`times` BIGINT NOT NULL Comment "耗时",
`request_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP Comment "请求时间",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic COMMENT = "admin日志";
-- sys_api_log DDL
CREATE TABLE `sys_api_log` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`method` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "方法名",
`user_id` BIGINT NULL Comment "用户id",
`request_url` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "请求url",
`request_ip` VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`request_body` VARCHAR(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "请求体",
`request_header` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "请求头",
`response` VARCHAR(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "响应",
`state` TINYINT NOT NULL DEFAULT 1 Comment "状态(1正常,0异常)",
`message` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "异常信息",
`times` BIGINT NOT NULL Comment "耗时",
`request_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP Comment "请求时间",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic COMMENT = "api日志";
-- sys_auth DDL
CREATE TABLE `sys_auth` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`parent_id` BIGINT NOT NULL DEFAULT 0 Comment "父id",
`title` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "名称(菜单名)",
`code` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "权限编码",
`name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "前端权限编码(路由名/按钮名)",
`auth_type` TINYINT NOT NULL Comment "权限类型(菜单,目录,按钮,权限)",
`sort` INT NULL Comment "排序",
`icon` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "菜单图标",
`path` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "路由地址",
`component` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "组件路径",
`frame_flag` TINYINT NOT NULL DEFAULT 0 Comment "是否外链",
`frame_url` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "外链地址",
`cache_flag` TINYINT NOT NULL DEFAULT 1 Comment "是否缓存",
`show_flag` TINYINT NOT NULL DEFAULT 1 Comment "是否显示",
`state` TINYINT NOT NULL Comment "状态",
`create_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "创建账号",
`create_time` DATETIME NOT NULL Comment "创建时间",
`update_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "更新账号",
`update_time` DATETIME NULL Comment "更新时间",
`remark` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "备注",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 63 ROW_FORMAT = Dynamic COMMENT = "系统权限";
-- sys_config DDL
CREATE TABLE `sys_config` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`key` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`value` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`comment` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "备注",
UNIQUE INDEX `uk_key`(`key` ASC) USING BTREE,
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic COMMENT = "系统配置";
-- sys_content DDL
CREATE TABLE `sys_content` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`content` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "图文详情",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 26 ROW_FORMAT = Dynamic COMMENT = "图文内容";
-- sys_data_scope DDL
CREATE TABLE `sys_data_scope` (`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`config_type` TINYINT UNSIGNED NOT NULL Comment "配置类型(1用户 2组织机构)",
`config_id` BIGINT UNSIGNED NOT NULL Comment "用户/机构id",
`config_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "用户/机构名称",
`data_key` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "数据key",
`data_scope` TINYINT UNSIGNED NOT NULL Comment "enum_DataScope_数据权限",
`custom_ids` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "自定义数据权限ids",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 5 ROW_FORMAT = Dynamic COMMENT = "数据权限表";
-- sys_demo DDL
CREATE TABLE `sys_demo` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "名称(文本框)",
`age` INT NULL Comment "年龄(数字框)",
`score` DECIMAL(10,2) NULL Comment "分数(小数)",
`gender` TINYINT NULL Comment "enum_Gender_性别(下拉框)",
`state` TINYINT NOT NULL DEFAULT 1 Comment "启用状态(单选框)",
`interest` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "兴趣(多选框)",
`deleted` TINYINT NOT NULL DEFAULT 0 Comment "逻辑删除",
`birth` DATE NULL Comment "生日(日期)",
`regist_time` DATETIME NOT NULL Comment "注册时间(日期时间)",
`avatar` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "头像(图片上传)",
`video` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "视频(视频上传)",
`photos` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "照片(多图上传)",
`attachment` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "附件(文件上传)",
`introduction` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "简介(文本域)",
`detail` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "详情(富文本)",
`phone` CHAR(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "手机",
`location` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "地址",
`lng` DECIMAL(22,6) NULL Comment "经度",
`lat` DECIMAL(22,6) NULL Comment "纬度",
`file_input` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "文件输入",
`user_id` BIGINT NULL,
`org_id` BIGINT NULL,
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 19 ROW_FORMAT = Dynamic COMMENT = "系统示例表";
-- sys_dictionary DDL
CREATE TABLE `sys_dictionary` (`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`pid` BIGINT UNSIGNED NOT NULL DEFAULT 0 Comment "分类父id",
`leaf_flag` TINYINT UNSIGNED NOT NULL Comment "叶子节点(叶子节点存字典值,非叶子节点存字典类别)",
`children_num` INT UNSIGNED NOT NULL DEFAULT 0 Comment "子节点数量",
`state` TINYINT UNSIGNED NOT NULL Comment "enum_EnableState_状态",
`dict_key` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`dict_value` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`sort` INT UNSIGNED NOT NULL DEFAULT 0 Comment "排序值",
`remark` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "备注",
`create_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "创建账号",
`create_time` DATETIME NOT NULL Comment "创建时间",
`update_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "更新账号",
`update_time` DATETIME NULL Comment "更新时间",
UNIQUE INDEX `sys_dictionary_un`(`dict_key` ASC) USING BTREE,
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 12 ROW_FORMAT = Dynamic COMMENT = "字典表";
-- sys_gen_column DDL
CREATE TABLE `sys_gen_column` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`table_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "表名",
`column_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "字段名",
`column_remark` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "字段注释",
`label` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "显示标题",
`property` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "属性名",
`form_type` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "表单类型",
`list_flag` TINYINT NOT NULL DEFAULT 0 Comment "是否列表",
`search_flag` TINYINT NOT NULL DEFAULT 0 Comment "是否搜索",
`required` TINYINT NOT NULL Comment "是否必填",
`maxlength` INT NULL Comment "字符串长度",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 281 ROW_FORMAT = Dynamic COMMENT = "表单生成字段";
-- sys_gen_table DDL
CREATE TABLE `sys_gen_table` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`table_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "表名",
`table_remark` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "表注释",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 19 ROW_FORMAT = Dynamic COMMENT = "表单生成记录";
-- sys_login_log DDL
CREATE TABLE `sys_login_log` (`id` BIGINT NOT NULL AUTO_INCREMENT Comment "访问ID",
`account` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' Comment "用户账号",
`ip` VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' Comment "登录IP",
`location` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' Comment "登录地点",
`browser` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' Comment "浏览器类型",
`os` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' Comment "操作系统",
`message` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' Comment "提示消息",
`login_time` DATETIME NOT NULL Comment "登录时间",
`state` TINYINT NOT NULL DEFAULT 1 Comment "状态(1成功0失败)",
`online_flag` TINYINT NOT NULL DEFAULT 1 Comment "在线状态",
`logout_type` TINYINT NULL Comment "登出类型",
`logout_time` DATETIME NULL Comment "登出时间",
`token` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 152 ROW_FORMAT = Dynamic COMMENT = "系统登录日志";
-- sys_organization DDL
CREATE TABLE `sys_organization` (`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT Comment "部门id",
`pid` BIGINT UNSIGNED NOT NULL DEFAULT 0 Comment "父部门id",
`ancestors` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "祖先路径",
`type` TINYINT UNSIGNED NOT NULL Comment "类型(1组织机构,2部门)",
`name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "部门名称",
`sort` INT UNSIGNED NOT NULL DEFAULT 0 Comment "显示顺序",
`link_name` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "联系人",
`link_phone` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "联系电话",
`link_email` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "联系邮箱",
`state` TINYINT UNSIGNED NOT NULL Comment "enum_EnableState_状态",
`create_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "创建账号",
`create_time` DATETIME NOT NULL Comment "创建时间",
`update_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "更新账号",
`update_time` DATETIME NULL Comment "更新时间",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 8 ROW_FORMAT = Dynamic COMMENT = "组织机构";
-- sys_role DDL
CREATE TABLE `sys_role` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "角色名",
`code` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "角色编码",
`sort` INT NULL Comment "排序",
`state` TINYINT NOT NULL Comment "状态",
`system_flag` TINYINT NOT NULL DEFAULT 0 Comment "是否系统内置",
`remark` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "备注",
`create_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "创建账号",
`create_time` DATETIME NOT NULL Comment "创建时间",
`update_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "更新账号",
`update_time` DATETIME NULL Comment "更新时间",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 6 ROW_FORMAT = Dynamic COMMENT = "系统角色";
-- sys_role_auth DDL
CREATE TABLE `sys_role_auth` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`role_id` BIGINT NOT NULL Comment "角色id",
`auth_id` BIGINT NOT NULL Comment "权限id",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 38 ROW_FORMAT = Dynamic COMMENT = "系统角色-权限";
-- sys_upload DDL
CREATE TABLE `sys_upload` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`md5` VARCHAR(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`url` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`path` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "文件路径",
`name` VARCHAR(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "文件名",
`file_suffix` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "文件后缀",
`upload_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP Comment "上传时间",
`file_size` BIGINT NULL Comment "文件大小",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 22 ROW_FORMAT = Dynamic COMMENT = "系统文件上传";
-- sys_user DDL
CREATE TABLE `sys_user` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`account` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "账号",
`password` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "密码",
`org_id` BIGINT UNSIGNED NULL Comment "组织机构",
`phone` CHAR(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "手机号码",
`nickname` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "昵称",
`avatar` VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "头像",
`email` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "邮箱",
`gender` TINYINT NULL Comment "性别",
`state` TINYINT NOT NULL Comment "状态",
`system_flag` TINYINT NOT NULL DEFAULT 0 Comment "是否系统内置",
`create_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL Comment "创建账号",
`create_time` DATETIME NOT NULL Comment "创建时间",
`update_user` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "更新账号",
`update_time` DATETIME NULL Comment "更新时间",
`remark` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL Comment "备注",
UNIQUE INDEX `uk_account`(`account` ASC) USING BTREE,
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 6 ROW_FORMAT = Dynamic COMMENT = "系统用户";
-- sys_user_role DDL
CREATE TABLE `sys_user_role` (`id` BIGINT NOT NULL AUTO_INCREMENT,
`user_id` BIGINT NOT NULL Comment "用户id",
`role_id` BIGINT NOT NULL Comment "角色id",
PRIMARY KEY (`id`)) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci AUTO_INCREMENT = 25 ROW_FORMAT = Dynamic COMMENT = "系统用户-角色";
-- sys_auth DML
INSERT INTO `sys_auth` (`id`,`parent_id`,`title`,`code`,`name`,`auth_type`,`sort`,`icon`,`path`,`component`,`frame_flag`,`frame_url`,`cache_flag`,`show_flag`,`state`,`create_user`,`create_time`,`update_user`,`update_time`,`remark`) VALUES (1,0,'系统管理','','sys',2,1,'ep:platform','/sys',NULL,0,'',1,1,1,'admin','2022-12-17 14:44:47','admin','2025-03-18 23:52:51',''),(2,1,'用户管理','/sysUser/**','sysUser',1,1,'ep:user-filled','/sys/sysUser','/sys/sysUser/index',0,'',1,1,1,'admin','2022-12-17 14:52:17','admin','2025-03-18 23:58:11',''),(3,1,'角色管理','/sysRole/**','sysRole',1,10,'ep:collection-tag','/sys/sysRole','/sys/sysRole/index',0,'',1,1,1,'admin','2022-12-17 14:56:21','admin','2025-03-18 23:59:53',''),(4,1,'菜单权限','/sysAuth/**','sysAuth',1,20,'ep:unlock','/sys/sysAuth','/sys/sysAuth/index',0,'',1,1,1,'admin','2022-12-17 14:58:45','admin','2025-03-19 00:14:20',''),(23,1,'系统日志','/sysAdminLog/**','sysAdminLog',1,30,'ep:operation','/sys/sysAdminLog','/sys/sysAdminLog/index',0,'',1,1,1,'admin','2022-12-17 21:39:57','admin','2025-03-19 00:01:13',''),(24,1,'登录日志','/sysLoginLog/**','sysLoginLog',1,40,'ep:lock','/sys/sysLoginLog','/sys/sysLoginLog/index',0,'',1,1,1,'admin','2022-12-17 21:41:59','admin','2025-03-19 00:01:36',''),(45,1,'角色授权用户','/sysRole/users','sysUserRole',1,11,NULL,'/sys/sysUserRole/:roleId','/sys/sysRole/users',0,'',1,0,1,'admin','2022-12-28 15:10:23','admin','2022-12-28 15:30:12',''),(46,1,'代码生成','/gen/**','codeGen',1,50,NULL,'/sys/gen','/sys/gen/index',0,NULL,1,1,1,'admin','2023-01-03 14:34:48',NULL,NULL,NULL),(47,1,'表单生成','/gen/**','formGen',1,60,NULL,'/sys/formGen','/sys/gen/form',0,'',1,0,1,'admin','2023-01-03 14:35:29','admin','2023-01-05 21:55:47',''),(48,0,'测试菜单',NULL,'testMenu',2,20,NULL,'/test',NULL,0,'',1,1,1,'admin','2023-01-04 10:23:46','admin','2023-01-04 10:24:01',''),(49,48,'系统示例','sysDemo:page','demo',1,1,'','/test/demo','/sys/sysDemo/index',0,'',1,1,1,'admin','2023-01-04 10:25:00','admin','2025-03-23 15:42:46',''),(50,1,'表单生成记录','/gen/**','formGenRecord',1,70,'','/sys/formGenRecord','/sys/gen/formRecord',0,'',1,1,1,'admin','2023-01-05 21:59:53','admin','2025-03-30 16:30:30',''),(54,49,'新增','sysDemo:add','add',3,1,'','','',0,'',1,1,1,'admin','2023-01-13 22:24:02','admin','2025-03-23 15:43:11',''),(55,49,'编辑','sysDemo:edit','edit',3,2,'','','',0,'',1,1,1,'admin','2023-01-13 22:24:19','admin','2025-03-23 15:43:23',''),(56,49,'删除','sysDemo:del','del',3,3,'','','',0,'',1,1,1,'admin','2023-01-13 22:28:44','admin','2025-03-23 15:43:49',''),(57,49,'查看','sysDemo:view','view',3,4,'','','',0,'',1,1,1,'admin','2023-01-13 22:32:31','admin','2025-03-23 15:44:26',''),(60,1,'组织机构','','sysOrganization',1,0,'ri:organization-chart','/sys/sysOrganization','/sys/sysOrganization/index',0,'',1,1,1,'admin','2025-03-22 20:12:31','admin','2025-03-23 17:30:57',''),(61,1,'数据字典','','sysDictionary',1,25,'ep:document','/sys/sysDictionary','/sys/sysDictionary/index',0,'',1,1,1,'admin','2025-03-29 15:49:24','admin','2025-03-29 21:55:51',''),(62,1,'数据权限配置',NULL,'sysDataScope',1,26,'ep:circle-check','/sys/sysDataScope','/sys/sysDataScope/index',0,NULL,1,1,1,'admin','2025-03-30 15:37:37',NULL,NULL,NULL);
-- sys_config DML
INSERT INTO `sys_config` (`id`,`key`,`value`,`comment`) VALUES (1,'phone','123456',NULL);
-- sys_content DML
INSERT INTO `sys_content` (`id`,`content`) VALUES (1,'<h1 style="text-align: center;">“数据二十条”新政出台 促进数据赋能实体经济</h1><p>来源：央视网 | 2022年12月20日 10:36:53</p><p style="text-indent: 2em; text-align: start;"><strong>央视网消息：</strong>近日，中共中央、国务院对外发布了《关于构建数据基础制度更好发挥数据要素作用的意见》，又称“数据二十条”。“数据二十条”提出构建数据产权、流通交易、收益分配、安全治理等制度，初步形成我国数据基础制度的“四梁八柱”。“数据二十条”的出台，有利于充分激活数据要素价值，赋能实体经济，推动高质量发展。</p><p style="text-indent: 2em; text-align: start;">本次出台的“数据二十条”，构建了数据产权、流通交易、收益分配、安全治理等4项制度，共计20条政策措施。</p>'),(2,'<h2 style="text-indent: 2em; text-align: center;"><strong>新华社北京12月19日电题：提振信心 笃定前行——广大干部群众畅谈学习贯彻中央经济工作会议精神</strong></h2><p style="text-indent: 2em; text-align: center;">新华社记者刘开雄、刘羽佳</p><p style="text-indent: 2em; text-align: justify;">连日来，全国各地、各行各业都在认真学习领会中央经济工作会议精神。广大干部群众表示，以习近平同志为核心的党中央关于经济工作的决策部署让大家对未来中国经济发展充满信心。要持续深入学习领会会议精神，纲举目张做好工作，将中央经济工作会议的各项部署落到实处。</p>'),(3,'<h1 style="text-align: center;">学习时节丨心系澳门发展，总书记这些话直抵人心</h1><p><span style="color: rgb(130, 130, 130); font-size: 14px;">2022-12-20 07:38</span> <span style="color: rgb(130, 130, 130); font-size: 14px;">来源：南方网</span> </p><p style="text-align: left;">　　党的十八大以来，习近平总书记一直心系澳门发展，殷殷寄语、深情勉励，为澳门保持繁荣稳定指明了前进方向、提供了不竭动力。今天（12月20日），是澳门回归祖国23周年纪念日。让我们一同重温总书记对澳门的殷切期许，感受直抵人心的力量。</p><p><br></p>'),(26,'<p>1111111111112</p>');
-- sys_data_scope DML
INSERT INTO `sys_data_scope` (`id`,`config_type`,`config_id`,`config_name`,`data_key`,`data_scope`,`custom_ids`) VALUES (1,2,5,'技术部','sys_demo',1,''),(2,2,3,'云南鸿运有限公司','sys_demo',4,'[5,6]'),(3,1,1,'系统管理员','sys_demo',1,'');
-- sys_demo DML
INSERT INTO `sys_demo` (`id`,`name`,`age`,`score`,`gender`,`state`,`interest`,`deleted`,`birth`,`regist_time`,`avatar`,`video`,`photos`,`attachment`,`introduction`,`detail`,`phone`,`location`,`lng`,`lat`,`file_input`,`user_id`,`org_id`) VALUES (4,'farkle',20,100.00,1,1,'["2","1"]',0,'2025-03-12','2025-03-11 21:25:13','/202503/85128da8-70a5-467c-a379-29a649cab949.gif',NULL,'[{"id":13,"name":"QR57a.jpg","url":"/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg"},{"id":14,"name":"QRBHS.jpg","url":"/202503/6a5cbff9-266b-4dd5-bba2-a0f443eff18b.jpg"}]','[{"id":17,"name":"hello.txt","url":"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt"},{"id":16,"name":"test.txt","url":"/202503/e03f4fa8-c518-45fe-a0d8-89fe76d62e09.txt"}]','简介xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','<p>详情<img src="http://localhost:8080/upload/202503/996db02c-0a2c-4588-ae6a-6f51d8e39881.jpeg" alt="" data-href="" style=""/></p>','15288192183',NULL,NULL,NULL,NULL,NULL,1),(5,'zzzzz',1,2.00,2,1,'["1","2"]',0,'2023-01-06','2023-01-05 13:36:25','/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg','http://127.0.0.1:8081/upload/202301/4bc82364-2daa-4977-b982-25b1c67453d4.mp4','[{"id":15,"name":"QRqMK.jpg","url":"/202503/3022ce7a-1f54-4c65-a268-238b11b89eb4.jpg"}]','[{"id":17,"name":"hello.txt","url":"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt"}]','简介xxx','<p><span style="color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;">详情123</span></p>','11111111111','云南省昆明市呈贡区七甸街道白泥洞',NULL,NULL,'http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt',NULL,3),(6,'farkle_4',20,100.00,1,1,'["2","1"]',0,'2025-03-12','2025-03-11 21:25:13','/202503/85128da8-70a5-467c-a379-29a649cab949.gif',NULL,'','','简介xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','','15288192183',NULL,NULL,NULL,NULL,NULL,4);
-- sys_dictionary DML
INSERT INTO `sys_dictionary` (`id`,`pid`,`leaf_flag`,`children_num`,`state`,`dict_key`,`dict_value`,`sort`,`remark`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES (1,0,0,1,1,'sys_dict','系统字典',0,'','admin','2025-03-29 16:07:52','admin','2025-03-29 21:43:39'),(4,1,0,0,1,'sys_dict_1','系统字典1',0,'','admin','2025-03-29 16:21:41','admin','2025-03-29 21:43:50');
-- sys_gen_column DML
INSERT INTO `sys_gen_column` (`id`,`table_name`,`column_name`,`column_remark`,`label`,`property`,`form_type`,`list_flag`,`search_flag`,`required`,`maxlength`) VALUES (150,'app_article','type','','文章类型','type','select',1,1,1,0),(151,'app_article','title','','标题','title','input',1,1,1,255),(152,'app_article','picture','','图片','picture','picture',0,0,0,255),(153,'app_article','introduction','','简介','introduction','input',1,0,0,255),(154,'app_article','content_type','','内容类型','contentType','radio',0,0,1,0),(155,'app_article','content_id','','图文详情','contentId','richtext',0,0,1,0),(156,'app_article','link_url','','超链接','linkUrl','input',0,0,1,255),(157,'app_article','location','','文章位置','location','input',0,0,0,255),(163,'app_version','device','','设备','device','radio',1,1,1,0),(164,'app_version','version','','版本','version','input',1,1,1,20),(165,'app_version','description','','描述','description','input',1,0,1,255),(166,'app_version','download_url','','apk下载url','downloadUrl','input',1,0,1,200),(167,'app_version','force_update','','是否强制更新','forceUpdate','radio',1,1,1,0),(168,'app_user','nickname','','昵称','nickname','input',1,1,1,100),(169,'app_user','avatar','','头像','avatar','picture',1,0,0,200),(170,'app_user','phone','','手机号码','phone','phone',1,1,1,11),(171,'app_user','gender','','性别','gender','select',1,0,0,0),(172,'app_user','birth','','生日','birth','date',1,0,0,0),(173,'app_user','address','','地址','address','input',1,0,0,50),(174,'app_user','integral','','积分','integral','number',0,0,0,0),(175,'app_user','balance','','余额','balance','number',0,0,0,0),(176,'app_user','role','','角色','role','select',0,0,0,0),(177,'app_user','enable_state','','启用状态','enableState','select',1,1,1,0),(178,'app_user','comment','','备注','comment','input',1,0,0,255),(198,'sys_demo','name','','名称','name','input',1,0,1,50),(199,'sys_demo','age','','年龄','age','number',1,0,0,NULL),(200,'sys_demo','score','','分数','score','number',1,0,0,NULL),(201,'sys_demo','gender','Gender','性别','gender','enum',1,0,0,NULL),(202,'sys_demo','state','','启用状态','state','number',1,0,0,NULL),(203,'sys_demo','interest','','兴趣','interest','input',1,0,0,255),(204,'sys_demo','birth','','生日','birth','date',1,0,0,NULL),(205,'sys_demo','regist_time','','注册时间','registTime','datetime',1,0,1,NULL),(206,'sys_demo','avatar','','头像','avatar','input',1,0,1,200),(207,'sys_demo','video','','视频','video','input',1,0,0,200),(208,'sys_demo','photos','','照片','photos','input',1,0,0,500),(209,'sys_demo','attachment','','附件','attachment','input',1,0,0,500),(210,'sys_demo','introduction','','简介','introduction','input',1,0,1,255),(211,'sys_demo','detail','','详情','detail','input',1,0,0,500),(212,'sys_demo','phone','','手机','phone','input',1,0,1,11),(213,'sys_demo','location','','地址','location','input',1,0,0,200),(214,'sys_demo','lng','','经度','lng','number',1,0,0,NULL),(215,'sys_demo','lat','','纬度','lat','number',1,0,0,NULL),(216,'sys_demo','file_input','','文件输入','fileInput','input',1,0,0,200),(241,'sys_organization','pid','','父部门id','pid','number',0,0,0,NULL),(242,'sys_organization','type','','类型','type','radio',1,0,1,NULL),(243,'sys_organization','name','','部门名称','name','input',1,1,1,50),(244,'sys_organization','sort','','显示顺序','sort','number',1,0,0,NULL),(245,'sys_organization','link_name','','联系人','linkName','input',1,0,0,20),(246,'sys_organization','link_phone','','联系电话','linkPhone','input',1,0,0,20),(247,'sys_organization','link_email','','联系邮箱','linkEmail','input',1,0,0,50),(248,'sys_organization','state','EnableState','状态','state','enum',1,0,1,NULL),(249,'sys_organization','creator','','创建账号','creator','input',0,0,1,50),(250,'sys_organization','create_time','','创建时间','createTime','datetime',0,0,1,NULL),(251,'sys_organization','updator','','更新账号','updator','input',0,0,0,50),(252,'sys_organization','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(253,'sys_role','name','','角色名','name','input',1,0,1,50),(254,'sys_role','code','','角色编码','code','input',1,0,0,50),(255,'sys_role','sort','','排序','sort','number',1,0,0,NULL),(256,'sys_role','state','','状态','state','radio',1,0,1,NULL),(257,'sys_role','system_flag','','是否系统内置','systemFlag','radio',0,0,0,NULL),(258,'sys_role','remark','','备注','remark','input',1,0,0,255),(259,'sys_role','create_user','','创建账号','createUser','input',0,0,1,50),(260,'sys_role','create_time','','创建时间','createTime','datetime',1,0,1,NULL),(261,'sys_role','update_user','','更新账号','updateUser','input',0,0,0,50),(262,'sys_role','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(263,'sys_dictionary','pid','','分类父id','pid','number',0,0,0,NULL),(264,'sys_dictionary','leaf_flag','','叶子节点','leafFlag','number',0,0,1,NULL),(265,'sys_dictionary','children_num','','子节点数量','childrenNum','number',0,0,1,NULL),(266,'sys_dictionary','state','EnableState','状态','state','enum',1,1,1,NULL),(267,'sys_dictionary','dict_key','','dict_key','dictKey','input',1,1,1,200),(268,'sys_dictionary','dict_value','','dict_value','dictValue','input',1,1,1,200),(269,'sys_dictionary','sort','','排序值','sort','number',1,0,0,NULL),(270,'sys_dictionary','remark','','备注','remark','input',1,0,0,200),(271,'sys_dictionary','create_user','','创建账号','createUser','input',0,0,1,50),(272,'sys_dictionary','create_time','','创建时间','createTime','datetime',0,0,1,NULL),(273,'sys_dictionary','update_user','','更新账号','updateUser','input',0,0,0,50),(274,'sys_dictionary','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(275,'sys_data_scope','config_type','','配置类型','configType','radio',1,1,1,NULL),(276,'sys_data_scope','config_id','','用户/机构id','configId','number',1,0,1,NULL),(277,'sys_data_scope','config_name','','用户/结构名称','configName','input',1,1,0,100),(278,'sys_data_scope','data_key','','数据key','dataKey','input',1,1,0,100),(279,'sys_data_scope','data_scope','DataScope','数据权限','dataScope','enum',1,0,1,NULL),(280,'sys_data_scope','custom_ids','','自定义数据权限ids','customIds','input',1,0,0,100);
-- sys_gen_table DML
INSERT INTO `sys_gen_table` (`id`,`table_name`,`table_remark`) VALUES (1,'sys_demo','系统示例表'),(7,'app_article','app文章'),(8,'app_version','app版本管理'),(9,'app_version','app版本管理'),(10,'app_user','app用户'),(11,'sys_demo','系统示例表'),(12,'sys_demo','系统示例表'),(13,'sys_organization','组织机构'),(14,'sys_organization','组织机构'),(15,'sys_organization','组织机构'),(16,'sys_role','系统角色'),(17,'sys_dictionary','字典表'),(18,'sys_data_scope','数据权限表');
-- sys_organization DML
INSERT INTO `sys_organization` (`id`,`pid`,`ancestors`,`type`,`name`,`sort`,`link_name`,`link_phone`,`link_email`,`state`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES (1,0,'1',1,'鸿运集团',0,'','','',1,'admin','2025-03-16 21:57:28','admin','2025-03-23 15:14:50'),(3,1,'1,3',1,'云南鸿运有限公司',0,'1','2','3',1,'admin','2025-03-23 15:18:16','admin','2025-03-23 15:18:16'),(4,1,'1,4',1,'成都分公司',0,NULL,NULL,NULL,1,'admin','2025-03-23 15:18:56','admin','2025-03-23 15:18:56'),(5,3,'1,3,5',2,'技术部',1,'','','',1,'admin','2025-03-23 15:19:59','admin','2025-03-23 15:19:59'),(6,3,'1,3,6',2,'测试部',10,'','','',1,'admin','2025-03-23 15:20:19','admin','2025-03-23 15:20:25'),(7,4,'1,4,7',2,'市场部',0,'','','',1,'admin','2025-03-23 15:20:58','admin','2025-03-23 15:20:58');
-- sys_role DML
INSERT INTO `sys_role` (`id`,`name`,`code`,`sort`,`state`,`system_flag`,`remark`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES (1,'管理员','admin',1,1,0,NULL,'admin','2022-12-17 15:39:13',NULL,NULL),(5,'测试权限','test_auth',2,1,0,'','admin','2023-01-13 22:38:47','admin','2025-03-23 15:49:09');
-- sys_role_auth DML
INSERT INTO `sys_role_auth` (`id`,`role_id`,`auth_id`) VALUES (32,5,1),(33,5,60),(34,5,48),(35,5,49),(36,5,56),(37,5,57);
-- sys_user DML
INSERT INTO `sys_user` (`id`,`account`,`password`,`org_id`,`phone`,`nickname`,`avatar`,`email`,`gender`,`state`,`system_flag`,`create_user`,`create_time`,`update_user`,`update_time`,`remark`) VALUES (1,'admin','$2a$12$JR1RFAWTSJaxsgv7.w/9wOvY0ZtHjFaVordMx25slUQMDbvoNIUiS',NULL,'','系统管理员','http://localhost:8080/upload/202509/8b925790-0d11-491c-a9f7-ac4123dfc000.','',1,1,1,'admin','2022-12-17 15:44:57','admin','2025-09-04 01:06:31','xxxxxx'),(5,'test','$2a$12$IANymdvP6Nmbdaecovl6y.Ubf7xI.P97PWNQ/5VWDmhmJKqk6Nth2',3,'','','','',NULL,1,0,'admin','2023-01-13 22:39:16','admin','2025-09-04 01:05:09','');
-- sys_user_role DML
INSERT INTO `sys_user_role` (`id`,`user_id`,`role_id`) VALUES (24,5,5);
SET FOREIGN_KEY_CHECKS = 1;
