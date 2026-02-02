-- sys_admin_log DDL
CREATE SEQUENCE IF NOT EXISTS "sys_admin_log_id_seq";
CREATE TABLE "sys_admin_log" (
"id" int8 NOT NULL DEFAULT nextval('sys_admin_log_id_seq'::regclass),
"title" varchar(50) NOT NULL,
"method" varchar(255) NOT NULL,
"account" varchar(50) NOT NULL,
"request_url" varchar(255) NOT NULL,
"request_ip" varchar(128) NOT NULL,
"request_body" varchar(2000) NOT NULL,
"request_header" varchar(255),
"response" varchar(2000),
"state" int2 NOT NULL DEFAULT 1,
"message" varchar(255),
"times" int8 NOT NULL,
"request_time" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT "sys_admin_log_pkey" PRIMARY KEY ("id"));

-- sys_api_log DDL
CREATE SEQUENCE IF NOT EXISTS "sys_api_log_id_seq";
CREATE TABLE "sys_api_log" (
"id" int8 NOT NULL DEFAULT nextval('sys_api_log_id_seq'::regclass),
"method" varchar(255) NOT NULL,
"user_id" int8,
"request_url" varchar(255) NOT NULL,
"request_ip" varchar(128) NOT NULL,
"request_body" varchar(2000) NOT NULL,
"request_header" varchar(255),
"response" varchar(2000),
"state" int2 NOT NULL DEFAULT 1,
"message" varchar(255),
"times" int8 NOT NULL,
"request_time" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT "sys_api_log_pkey" PRIMARY KEY ("id"));

-- sys_auth DDL
CREATE SEQUENCE IF NOT EXISTS "sys_auth_id_seq";
CREATE TABLE "sys_auth" (
"id" int8 NOT NULL DEFAULT nextval('sys_auth_id_seq'::regclass),
"parent_id" int8 NOT NULL DEFAULT 0,
"title" varchar(50) NOT NULL,
"code" varchar(50),
"name" varchar(50) NOT NULL,
"auth_type" int2 NOT NULL,
"sort" int4,
"icon" varchar(50),
"path" varchar(255),
"component" varchar(255),
"frame_flag" int2 NOT NULL DEFAULT 0,
"frame_url" varchar(255),
"cache_flag" int2 NOT NULL DEFAULT 1,
"show_flag" int2 NOT NULL DEFAULT 1,
"state" int2 NOT NULL,
"create_user" varchar(50) NOT NULL,
"create_time" timestamptz(6) NOT NULL,
"update_user" varchar(50),
"update_time" timestamptz(6),
"remark" varchar(255),
CONSTRAINT "sys_auth_pkey" PRIMARY KEY ("id"));

-- sys_config DDL
CREATE SEQUENCE IF NOT EXISTS "sys_config_id_seq";
CREATE TABLE "sys_config" (
"id" int8 NOT NULL DEFAULT nextval('sys_config_id_seq'::regclass),
"key" varchar(200) NOT NULL,
"value" varchar(255) NOT NULL,
"comment" varchar(255),
CONSTRAINT "sys_config_pkey" PRIMARY KEY ("id"));

-- sys_content DDL
CREATE SEQUENCE IF NOT EXISTS "sys_content_id_seq";
CREATE TABLE "sys_content" (
"id" int8 NOT NULL DEFAULT nextval('sys_content_id_seq'::regclass),
"content" text NOT NULL,
CONSTRAINT "sys_content_pkey" PRIMARY KEY ("id"));

-- sys_data_scope DDL
CREATE SEQUENCE IF NOT EXISTS "sys_data_scope_id_seq";
CREATE TABLE "sys_data_scope" (
"id" int8 NOT NULL DEFAULT nextval('sys_data_scope_id_seq'::regclass),
"config_type" int2 NOT NULL,
"config_id" int8 NOT NULL,
"config_name" varchar(100),
"data_key" varchar(100),
"data_scope" int2 NOT NULL,
"custom_ids" varchar(100),
CONSTRAINT "sys_data_scope_pkey" PRIMARY KEY ("id"));

-- sys_demo DDL
CREATE TABLE "sys_demo" (
"id" int8 NOT NULL,
"name" varchar(50) NOT NULL,
"age" int4,
"score" numeric(10,2),
"gender" int2,
"state" int2 NOT NULL DEFAULT 1,
"interest" jsonb,
"deleted" int2 NOT NULL DEFAULT 0,
"birth" date,
"regist_time" timestamptz(6) NOT NULL,
"avatar" varchar(200) NOT NULL,
"video" varchar(200),
"photos" varchar(500),
"attachment" varchar(500),
"introduction" varchar(255) NOT NULL,
"detail" varchar(500),
"phone" bpchar(11) NOT NULL,
"location" varchar(200),
"lng" numeric(20,6),
"lat" numeric(20,6),
"file_input" varchar(200),
"user_id" int8,
"org_id" int8,
CONSTRAINT "sys_demo_pkey" PRIMARY KEY ("id"));

-- sys_dictionary DDL
CREATE SEQUENCE IF NOT EXISTS "sys_dictionary_id_seq";
CREATE TABLE "sys_dictionary" (
"id" int8 NOT NULL DEFAULT nextval('sys_dictionary_id_seq'::regclass),
"pid" int8 NOT NULL DEFAULT 0,
"leaf_flag" int2 NOT NULL,
"children_num" int4 NOT NULL DEFAULT 0,
"state" int2 NOT NULL,
"dict_key" varchar(200) NOT NULL,
"dict_value" varchar(200) NOT NULL,
"sort" int4 NOT NULL DEFAULT 0,
"remark" varchar(200),
"create_user" varchar(50) NOT NULL,
"create_time" timestamptz(6) NOT NULL,
"update_user" varchar(50),
"update_time" timestamptz(6),
CONSTRAINT "sys_dictionary_pkey" PRIMARY KEY ("id"));

-- sys_gen_column DDL
CREATE SEQUENCE IF NOT EXISTS "sys_gen_column_id_seq";
CREATE TABLE "sys_gen_column" (
"id" int8 NOT NULL DEFAULT nextval('sys_gen_column_id_seq'::regclass),
"table_name" varchar(100) NOT NULL,
"column_name" varchar(100) NOT NULL,
"column_remark" varchar(200),
"label" varchar(100),
"property" varchar(100) NOT NULL,
"form_type" varchar(100) NOT NULL,
"list_flag" int2 NOT NULL DEFAULT 0,
"search_flag" int2 NOT NULL DEFAULT 0,
"required" int2 NOT NULL,
"maxlength" int4,
CONSTRAINT "sys_gen_column_pkey" PRIMARY KEY ("id"));

-- sys_gen_table DDL
CREATE SEQUENCE IF NOT EXISTS "sys_gen_table_id_seq";
CREATE TABLE "sys_gen_table" (
"id" int8 NOT NULL DEFAULT nextval('sys_gen_table_id_seq'::regclass),
"table_name" varchar(100) NOT NULL,
"table_remark" varchar(200),
CONSTRAINT "sys_gen_table_pkey" PRIMARY KEY ("id"));

-- sys_login_log DDL
CREATE SEQUENCE IF NOT EXISTS "sys_login_log_id_seq";
CREATE TABLE "sys_login_log" (
"id" int8 NOT NULL DEFAULT nextval('sys_login_log_id_seq'::regclass),
"account" varchar(50) NOT NULL DEFAULT ''::character varying,
"ip" varchar(128) NOT NULL DEFAULT ''::character varying,
"location" varchar(255) DEFAULT ''::character varying,
"browser" varchar(50) DEFAULT ''::character varying,
"os" varchar(50) DEFAULT ''::character varying,
"message" varchar(255) NOT NULL DEFAULT ''::character varying,
"login_time" timestamptz(6) NOT NULL,
"state" int2 NOT NULL DEFAULT 1,
"online_flag" int2 NOT NULL DEFAULT 1,
"logout_type" int2,
"logout_time" timestamptz(6),
"token" varchar(50),
CONSTRAINT "sys_login_log_pkey" PRIMARY KEY ("id"));

-- sys_organization DDL
CREATE SEQUENCE IF NOT EXISTS "sys_organization_id_seq";
CREATE TABLE "sys_organization" (
"id" int8 NOT NULL DEFAULT nextval('sys_organization_id_seq'::regclass),
"pid" int8 NOT NULL DEFAULT 0,
"ancestors" varchar(200) NOT NULL,
"type" int2 NOT NULL,
"name" varchar(50) NOT NULL,
"sort" int4 NOT NULL DEFAULT 0,
"link_name" varchar(20),
"link_phone" varchar(20),
"link_email" varchar(50),
"state" int2 NOT NULL,
"create_user" varchar(50) NOT NULL,
"create_time" timestamptz(6) NOT NULL,
"update_user" varchar(50),
"update_time" timestamptz(6),
CONSTRAINT "sys_organization_pkey" PRIMARY KEY ("id"));

-- sys_role DDL
CREATE SEQUENCE IF NOT EXISTS "sys_role_id_seq";
CREATE TABLE "sys_role" (
"id" int8 NOT NULL DEFAULT nextval('sys_role_id_seq'::regclass),
"name" varchar(50) NOT NULL,
"code" varchar(50),
"sort" int4,
"state" int2 NOT NULL,
"system_flag" int2 NOT NULL DEFAULT 0,
"remark" varchar(255),
"create_user" varchar(50) NOT NULL,
"create_time" timestamptz(6) NOT NULL,
"update_user" varchar(50),
"update_time" timestamptz(6),
CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id"));

-- sys_role_auth DDL
CREATE SEQUENCE IF NOT EXISTS "sys_role_auth_id_seq";
CREATE TABLE "sys_role_auth" (
"id" int8 NOT NULL DEFAULT nextval('sys_role_auth_id_seq'::regclass),
"role_id" int8 NOT NULL,
"auth_id" int8 NOT NULL,
CONSTRAINT "sys_role_auth_pkey" PRIMARY KEY ("id"));

-- sys_upload DDL
CREATE SEQUENCE IF NOT EXISTS "sys_upload_id_seq";
CREATE TABLE "sys_upload" (
"id" int8 NOT NULL DEFAULT nextval('sys_upload_id_seq'::regclass),
"md5" varchar(60) NOT NULL,
"url" varchar(200) NOT NULL,
"path" varchar(100) NOT NULL,
"name" varchar(60),
"file_suffix" varchar(20),
"upload_time" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
"file_size" int8,
CONSTRAINT "sys_upload_pkey" PRIMARY KEY ("id"));

-- sys_user DDL
CREATE SEQUENCE IF NOT EXISTS "sys_user_id_seq";
CREATE TABLE "sys_user" (
"id" int8 NOT NULL DEFAULT nextval('sys_user_id_seq'::regclass),
"account" varchar(50) NOT NULL,
"password" varchar(100) NOT NULL,
"org_id" int8,
"phone" bpchar(11),
"nickname" varchar(50),
"avatar" varchar(200),
"email" varchar(50),
"gender" int2,
"state" int2 NOT NULL,
"system_flag" int2 NOT NULL DEFAULT 0,
"create_user" varchar(50) NOT NULL,
"create_time" timestamptz(6) NOT NULL,
"update_user" varchar(50),
"update_time" timestamptz(6),
"remark" varchar(255),
CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id"));

-- sys_user_role DDL
CREATE SEQUENCE IF NOT EXISTS "sys_user_role_id_seq";
CREATE TABLE "sys_user_role" (
"id" int8 NOT NULL DEFAULT nextval('sys_user_role_id_seq'::regclass),
"user_id" int8 NOT NULL,
"role_id" int8 NOT NULL,
CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("id"));

-- sys_admin_log Constraints
COMMENT ON TABLE "sys_admin_log" IS 'admin日志';
COMMENT ON COLUMN "sys_admin_log"."title" IS '标题';
COMMENT ON COLUMN "sys_admin_log"."method" IS '方法名';
COMMENT ON COLUMN "sys_admin_log"."account" IS '账号';
COMMENT ON COLUMN "sys_admin_log"."request_url" IS '请求url';
COMMENT ON COLUMN "sys_admin_log"."request_ip" IS '请求IP';
COMMENT ON COLUMN "sys_admin_log"."request_body" IS '请求体';
COMMENT ON COLUMN "sys_admin_log"."request_header" IS '请求头';
COMMENT ON COLUMN "sys_admin_log"."response" IS '响应';
COMMENT ON COLUMN "sys_admin_log"."state" IS '状态(1正常,0异常)';
COMMENT ON COLUMN "sys_admin_log"."message" IS '异常信息';
COMMENT ON COLUMN "sys_admin_log"."times" IS '耗时';
COMMENT ON COLUMN "sys_admin_log"."request_time" IS '请求时间';

-- sys_api_log Constraints
COMMENT ON TABLE "sys_api_log" IS '接口日志';
COMMENT ON COLUMN "sys_api_log"."method" IS '方法名';
COMMENT ON COLUMN "sys_api_log"."user_id" IS '用户id';
COMMENT ON COLUMN "sys_api_log"."request_url" IS '请求url';
COMMENT ON COLUMN "sys_api_log"."request_ip" IS '请求IP';
COMMENT ON COLUMN "sys_api_log"."request_body" IS '请求体';
COMMENT ON COLUMN "sys_api_log"."request_header" IS '请求头';
COMMENT ON COLUMN "sys_api_log"."response" IS '响应';
COMMENT ON COLUMN "sys_api_log"."state" IS '状态(1正常,0异常)';
COMMENT ON COLUMN "sys_api_log"."message" IS '异常信息';
COMMENT ON COLUMN "sys_api_log"."times" IS '耗时';
COMMENT ON COLUMN "sys_api_log"."request_time" IS '请求时间';

-- sys_auth Constraints
COMMENT ON TABLE "sys_auth" IS '系统权限';
COMMENT ON COLUMN "sys_auth"."parent_id" IS '父id';
COMMENT ON COLUMN "sys_auth"."title" IS '名称(菜单名)';
COMMENT ON COLUMN "sys_auth"."code" IS '权限编码';
COMMENT ON COLUMN "sys_auth"."name" IS '前端权限编码(路由名/按钮名)';
COMMENT ON COLUMN "sys_auth"."auth_type" IS '权限类型(菜单,目录,按钮,权限)';
COMMENT ON COLUMN "sys_auth"."sort" IS '排序';
COMMENT ON COLUMN "sys_auth"."icon" IS '菜单图标';
COMMENT ON COLUMN "sys_auth"."path" IS '路由地址';
COMMENT ON COLUMN "sys_auth"."component" IS '组件路径';
COMMENT ON COLUMN "sys_auth"."frame_flag" IS '是否外链';
COMMENT ON COLUMN "sys_auth"."frame_url" IS '外链地址';
COMMENT ON COLUMN "sys_auth"."cache_flag" IS '是否缓存';
COMMENT ON COLUMN "sys_auth"."show_flag" IS '是否显示';
COMMENT ON COLUMN "sys_auth"."state" IS '状态';
COMMENT ON COLUMN "sys_auth"."create_user" IS '创建账号';
COMMENT ON COLUMN "sys_auth"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_auth"."update_user" IS '更新账号';
COMMENT ON COLUMN "sys_auth"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_auth"."remark" IS '备注';

-- sys_config Constraints
COMMENT ON TABLE "sys_config" IS '系统配置';
CREATE UNIQUE INDEX "unique_key" ON "sys_config" USING btree ("key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS FIRST);
COMMENT ON COLUMN "sys_config"."comment" IS '备注';

-- sys_content Constraints
COMMENT ON TABLE "sys_content" IS '图文内容';
COMMENT ON COLUMN "sys_content"."content" IS '图文详情';

-- sys_data_scope Constraints
COMMENT ON TABLE "sys_data_scope" IS '数据权限表';
COMMENT ON COLUMN "sys_data_scope"."config_type" IS '配置类型(1用户 2组织机构)';
COMMENT ON COLUMN "sys_data_scope"."config_id" IS '用户/机构id';
COMMENT ON COLUMN "sys_data_scope"."config_name" IS '用户/机构名称';
COMMENT ON COLUMN "sys_data_scope"."data_key" IS '数据key';
COMMENT ON COLUMN "sys_data_scope"."data_scope" IS 'enum_DataScope_数据权限';
COMMENT ON COLUMN "sys_data_scope"."custom_ids" IS '自定义数据权限ids';

-- sys_demo Constraints
COMMENT ON TABLE "sys_demo" IS '系统示例表';
COMMENT ON COLUMN "sys_demo"."name" IS '名称(文本框)';
COMMENT ON COLUMN "sys_demo"."age" IS '年龄(数字框)';
COMMENT ON COLUMN "sys_demo"."score" IS '分数(小数)';
COMMENT ON COLUMN "sys_demo"."gender" IS 'enum_Gender_性别(下拉框)';
COMMENT ON COLUMN "sys_demo"."state" IS '启用状态(单选框)';
COMMENT ON COLUMN "sys_demo"."interest" IS '兴趣(多选框)';
COMMENT ON COLUMN "sys_demo"."deleted" IS '逻辑删除';
COMMENT ON COLUMN "sys_demo"."birth" IS '生日(日期)';
COMMENT ON COLUMN "sys_demo"."regist_time" IS '注册时间(日期时间)';
COMMENT ON COLUMN "sys_demo"."avatar" IS '头像(图片上传)';
COMMENT ON COLUMN "sys_demo"."video" IS '视频(视频上传)';
COMMENT ON COLUMN "sys_demo"."photos" IS '照片(多图上传)';
COMMENT ON COLUMN "sys_demo"."attachment" IS '附件(文件上传)';
COMMENT ON COLUMN "sys_demo"."introduction" IS '简介(文本域)';
COMMENT ON COLUMN "sys_demo"."detail" IS '详情(富文本)';
COMMENT ON COLUMN "sys_demo"."phone" IS '手机';
COMMENT ON COLUMN "sys_demo"."location" IS '地址';
COMMENT ON COLUMN "sys_demo"."lng" IS '经度';
COMMENT ON COLUMN "sys_demo"."lat" IS '纬度';
COMMENT ON COLUMN "sys_demo"."file_input" IS '文件输入';

-- sys_dictionary Constraints
COMMENT ON TABLE "sys_dictionary" IS '字典表';
CREATE UNIQUE INDEX "unique_dict_key" ON "sys_dictionary" USING btree ("dict_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS FIRST);
COMMENT ON COLUMN "sys_dictionary"."pid" IS '分类父id';
COMMENT ON COLUMN "sys_dictionary"."leaf_flag" IS '叶子节点(叶子节点存字典值,非叶子节点存字典类别)';
COMMENT ON COLUMN "sys_dictionary"."children_num" IS '子节点数量';
COMMENT ON COLUMN "sys_dictionary"."state" IS 'enum_EnableState_状态';
COMMENT ON COLUMN "sys_dictionary"."sort" IS '排序值';
COMMENT ON COLUMN "sys_dictionary"."remark" IS '备注';
COMMENT ON COLUMN "sys_dictionary"."create_user" IS '创建账号';
COMMENT ON COLUMN "sys_dictionary"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_dictionary"."update_user" IS '更新账号';
COMMENT ON COLUMN "sys_dictionary"."update_time" IS '更新时间';

-- sys_gen_column Constraints
COMMENT ON TABLE "sys_gen_column" IS '表单生成字段';
COMMENT ON COLUMN "sys_gen_column"."table_name" IS '表名';
COMMENT ON COLUMN "sys_gen_column"."column_name" IS '字段名';
COMMENT ON COLUMN "sys_gen_column"."column_remark" IS '字段注释';
COMMENT ON COLUMN "sys_gen_column"."label" IS '显示标题';
COMMENT ON COLUMN "sys_gen_column"."property" IS '属性名';
COMMENT ON COLUMN "sys_gen_column"."form_type" IS '表单类型';
COMMENT ON COLUMN "sys_gen_column"."list_flag" IS '是否列表';
COMMENT ON COLUMN "sys_gen_column"."search_flag" IS '是否搜索';
COMMENT ON COLUMN "sys_gen_column"."required" IS '是否必填';
COMMENT ON COLUMN "sys_gen_column"."maxlength" IS '字符串长度';

-- sys_gen_table Constraints
COMMENT ON TABLE "sys_gen_table" IS '表单生成记录';
COMMENT ON COLUMN "sys_gen_table"."table_name" IS '表名';
COMMENT ON COLUMN "sys_gen_table"."table_remark" IS '表注释';

-- sys_login_log Constraints
COMMENT ON TABLE "sys_login_log" IS '系统登录日志';
COMMENT ON COLUMN "sys_login_log"."account" IS '用户账号';
COMMENT ON COLUMN "sys_login_log"."ip" IS '登录IP';
COMMENT ON COLUMN "sys_login_log"."location" IS '登录地点';
COMMENT ON COLUMN "sys_login_log"."browser" IS '浏览器类型';
COMMENT ON COLUMN "sys_login_log"."os" IS '操作系统';
COMMENT ON COLUMN "sys_login_log"."message" IS '提示消息';
COMMENT ON COLUMN "sys_login_log"."login_time" IS '登录时间';
COMMENT ON COLUMN "sys_login_log"."state" IS '状态(1成功0失败)';
COMMENT ON COLUMN "sys_login_log"."online_flag" IS '在线状态';
COMMENT ON COLUMN "sys_login_log"."logout_type" IS '登出类型';
COMMENT ON COLUMN "sys_login_log"."logout_time" IS '登出时间';

-- sys_organization Constraints
COMMENT ON TABLE "sys_organization" IS '组织机构';
COMMENT ON COLUMN "sys_organization"."pid" IS '父id';
COMMENT ON COLUMN "sys_organization"."ancestors" IS '祖先路径';
COMMENT ON COLUMN "sys_organization"."type" IS '类型(1组织机构,2部门)';
COMMENT ON COLUMN "sys_organization"."name" IS '部门名称';
COMMENT ON COLUMN "sys_organization"."sort" IS '显示顺序';
COMMENT ON COLUMN "sys_organization"."link_name" IS '联系人';
COMMENT ON COLUMN "sys_organization"."link_phone" IS '联系电话';
COMMENT ON COLUMN "sys_organization"."link_email" IS '联系邮箱';
COMMENT ON COLUMN "sys_organization"."state" IS 'enum_EnableState_状态';
COMMENT ON COLUMN "sys_organization"."create_user" IS '创建账号';
COMMENT ON COLUMN "sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_organization"."update_user" IS '更新账号';
COMMENT ON COLUMN "sys_organization"."update_time" IS '更新时间';

-- sys_role Constraints
COMMENT ON TABLE "sys_role" IS '系统角色';
COMMENT ON COLUMN "sys_role"."name" IS '角色名';
COMMENT ON COLUMN "sys_role"."code" IS '角色编码';
COMMENT ON COLUMN "sys_role"."sort" IS '排序';
COMMENT ON COLUMN "sys_role"."state" IS '状态';
COMMENT ON COLUMN "sys_role"."system_flag" IS '是否系统内置';
COMMENT ON COLUMN "sys_role"."remark" IS '备注';
COMMENT ON COLUMN "sys_role"."create_user" IS '创建账号';
COMMENT ON COLUMN "sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_role"."update_user" IS '更新账号';
COMMENT ON COLUMN "sys_role"."update_time" IS '更新时间';

-- sys_role_auth Constraints
COMMENT ON TABLE "sys_role_auth" IS '系统角色-权限';
COMMENT ON COLUMN "sys_role_auth"."role_id" IS '角色id';
COMMENT ON COLUMN "sys_role_auth"."auth_id" IS '权限id';

-- sys_upload Constraints
COMMENT ON TABLE "sys_upload" IS '系统文件上传';
COMMENT ON COLUMN "sys_upload"."path" IS '文件路径';
COMMENT ON COLUMN "sys_upload"."name" IS '文件名';
COMMENT ON COLUMN "sys_upload"."file_suffix" IS '文件后缀';
COMMENT ON COLUMN "sys_upload"."upload_time" IS '上传时间';
COMMENT ON COLUMN "sys_upload"."file_size" IS '文件大小';

-- sys_user Constraints
COMMENT ON TABLE "sys_user" IS '系统用户';
CREATE UNIQUE INDEX "unique_account" ON "sys_user" USING btree ("account" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS FIRST);
COMMENT ON COLUMN "sys_user"."account" IS '账号';
COMMENT ON COLUMN "sys_user"."password" IS '密码';
COMMENT ON COLUMN "sys_user"."org_id" IS '组织机构';
COMMENT ON COLUMN "sys_user"."phone" IS '手机号码';
COMMENT ON COLUMN "sys_user"."nickname" IS '昵称';
COMMENT ON COLUMN "sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "sys_user"."gender" IS '性别';
COMMENT ON COLUMN "sys_user"."state" IS '状态';
COMMENT ON COLUMN "sys_user"."system_flag" IS '是否系统内置';
COMMENT ON COLUMN "sys_user"."create_user" IS '创建账号';
COMMENT ON COLUMN "sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user"."update_user" IS '更新账号';
COMMENT ON COLUMN "sys_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_user"."remark" IS '备注';

-- sys_user_role Constraints
COMMENT ON TABLE "sys_user_role" IS '系统用户-角色';
COMMENT ON COLUMN "sys_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "sys_user_role"."role_id" IS '角色id';

-- sys_admin_log DML
-- sys_auth DML
INSERT INTO "sys_auth" ("id","parent_id","title","code","name","auth_type","sort","icon","path","component","frame_flag","frame_url","cache_flag","show_flag","state","create_user","create_time","update_user","update_time","remark") VALUES (1,0,'系统管理','','sys',2,1,'ep:platform','/sys',NULL,0,'',1,1,1,'admin','2022-12-18 06:44:47','admin','2025-03-19 14:52:51',''),(2,1,'用户管理','/sysUser/**','sysUser',1,1,'ep:user-filled','/sys/sysUser','/sys/sysUser/index',0,'',1,1,1,'admin','2022-12-18 06:52:17','admin','2025-03-19 14:58:11',''),(3,1,'角色管理','/sysRole/**','sysRole',1,10,'ep:collection-tag','/sys/sysRole','/sys/sysRole/index',0,'',1,1,1,'admin','2022-12-18 06:56:21','admin','2025-03-19 14:59:53',''),(4,1,'菜单权限','/sysAuth/**','sysAuth',1,20,'ep:unlock','/sys/sysAuth','/sys/sysAuth/index',0,'',1,1,1,'admin','2022-12-18 06:58:45','admin','2025-03-19 15:14:20',''),(23,1,'系统日志','/sysAdminLog/**','sysAdminLog',1,30,'ep:operation','/sys/sysAdminLog','/sys/sysAdminLog/index',0,'',1,1,1,'admin','2022-12-18 13:39:57','admin','2025-03-19 15:01:13',''),(24,1,'登录日志','/sysLoginLog/**','sysLoginLog',1,40,'ep:lock','/sys/sysLoginLog','/sys/sysLoginLog/index',0,'',1,1,1,'admin','2022-12-18 13:41:59','admin','2025-03-19 15:01:36',''),(45,1,'角色授权用户','/sysRole/users','sysUserRole',1,11,NULL,'/sys/sysUserRole/:roleId','/sys/sysRole/users',0,'',1,0,1,'admin','2022-12-29 07:10:23','admin','2022-12-29 07:30:12',''),(46,1,'代码生成','/gen/**','codeGen',1,50,NULL,'/sys/gen','/sys/gen/index',0,NULL,1,1,1,'admin','2023-01-04 06:34:48',NULL,NULL,NULL),(47,1,'表单生成','/gen/**','formGen',1,60,NULL,'/sys/formGen','/sys/gen/form',0,'',1,0,1,'admin','2023-01-04 06:35:29','admin','2023-01-06 13:55:47',''),(48,0,'测试菜单',NULL,'testMenu',2,20,NULL,'/test',NULL,0,'',1,1,1,'admin','2023-01-05 02:23:46','admin','2023-01-05 02:24:01',''),(49,48,'系统示例','sysDemo:page','demo',1,1,'','/test/demo','/sys/sysDemo/index',0,'',1,1,1,'admin','2023-01-05 02:25:00','admin','2025-03-24 06:42:46',''),(50,1,'表单生成记录','/gen/**','formGenRecord',1,70,'','/sys/formGenRecord','/sys/gen/formRecord',0,'',1,1,1,'admin','2023-01-06 13:59:53','admin','2025-03-31 07:30:30',''),(54,49,'新增','sysDemo:add','add',3,1,'','','',0,'',1,1,1,'admin','2023-01-14 14:24:02','admin','2025-03-24 06:43:11',''),(55,49,'编辑','sysDemo:edit','edit',3,2,'','','',0,'',1,1,1,'admin','2023-01-14 14:24:19','admin','2025-03-24 06:43:23',''),(56,49,'删除','sysDemo:del','del',3,3,'','','',0,'',1,1,1,'admin','2023-01-14 14:28:44','admin','2025-03-24 06:43:49',''),(57,49,'查看','sysDemo:view','view',3,4,'','','',0,'',1,1,1,'admin','2023-01-14 14:32:31','admin','2025-03-24 06:44:26',''),(60,1,'组织机构','','sysOrganization',1,0,'ri:organization-chart','/sys/sysOrganization','/sys/sysOrganization/index',0,'',1,1,1,'admin','2025-03-23 11:12:31','admin','2025-03-24 08:30:57',''),(61,1,'数据字典','','sysDictionary',1,25,'ep:document','/sys/sysDictionary','/sys/sysDictionary/index',0,'',1,1,1,'admin','2025-03-30 06:49:24','admin','2025-03-30 12:55:51',''),(62,1,'数据权限配置',NULL,'sysDataScope',1,26,'ep:circle-check','/sys/sysDataScope','/sys/sysDataScope/index',0,NULL,1,1,1,'admin','2025-03-31 06:37:37',NULL,NULL,NULL);
-- sys_config DML
INSERT INTO "sys_config" ("id","key","value","comment") VALUES (1,'phone','123456',NULL);
-- sys_content DML
INSERT INTO "sys_content" ("id","content") VALUES (1,'<h1 style="text-align: center;">“数据二十条”新政出台 促进数据赋能实体经济</h1><p>来源：央视网 | 2022年12月20日 10:36:53</p><p style="text-indent: 2em; text-align: start;"><strong>央视网消息：</strong>近日，中共中央、国务院对外发布了《关于构建数据基础制度更好发挥数据要素作用的意见》，又称“数据二十条”。“数据二十条”提出构建数据产权、流通交易、收益分配、安全治理等制度，初步形成我国数据基础制度的“四梁八柱”。“数据二十条”的出台，有利于充分激活数据要素价值，赋能实体经济，推动高质量发展。</p><p style="text-indent: 2em; text-align: start;">本次出台的“数据二十条”，构建了数据产权、流通交易、收益分配、安全治理等4项制度，共计20条政策措施。</p>'),(2,'<h2 style="text-indent: 2em; text-align: center;"><strong>新华社北京12月19日电题：提振信心 笃定前行——广大干部群众畅谈学习贯彻中央经济工作会议精神</strong></h2><p style="text-indent: 2em; text-align: center;">新华社记者刘开雄、刘羽佳</p><p style="text-indent: 2em; text-align: justify;">连日来，全国各地、各行各业都在认真学习领会中央经济工作会议精神。广大干部群众表示，以习近平同志为核心的党中央关于经济工作的决策部署让大家对未来中国经济发展充满信心。要持续深入学习领会会议精神，纲举目张做好工作，将中央经济工作会议的各项部署落到实处。</p>'),(3,'<h1 style="text-align: center;">学习时节丨心系澳门发展，总书记这些话直抵人心</h1><p><span style="color: rgb(130, 130, 130); font-size: 14px;">2022-12-20 07:38</span> <span style="color: rgb(130, 130, 130); font-size: 14px;">来源：南方网</span> </p><p style="text-align: left;">　　党的十八大以来，习近平总书记一直心系澳门发展，殷殷寄语、深情勉励，为澳门保持繁荣稳定指明了前进方向、提供了不竭动力。今天（12月20日），是澳门回归祖国23周年纪念日。让我们一同重温总书记对澳门的殷切期许，感受直抵人心的力量。</p><p><br></p>'),(26,'<p>1111111111112</p>');
-- sys_data_scope DML
INSERT INTO "sys_data_scope" ("id","config_type","config_id","config_name","data_key","data_scope","custom_ids") VALUES (1,2,5,'技术部','sys_demo',1,''),(2,2,3,'云南鸿运有限公司','sys_demo',4,'[5,6]'),(3,1,1,'系统管理员','sys_demo',1,'');
-- sys_demo DML
INSERT INTO "sys_demo" ("id","name","age","score","gender","state","interest","deleted","birth","regist_time","avatar","video","photos","attachment","introduction","detail","phone","location","lng","lat","file_input","user_id","org_id") VALUES (5,'zzzzz',1,2.00,2,1,'["1", "2"]',0,'2023-01-06','2023-01-06 05:36:25','/202503/3537dae2-ee18-494d-ace3-a18f9e42f3dd.jpg','http://127.0.0.1:8081/upload/202301/4bc82364-2daa-4977-b982-25b1c67453d4.mp4','[{"id":15,"name":"QRqMK.jpg","url":"/202503/3022ce7a-1f54-4c65-a268-238b11b89eb4.jpg"}]','[{"id":17,"name":"hello.txt","url":"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt"}]','简介xxx','<p><span style="color: rgb(96, 98, 102); background-color: rgb(255, 255, 255); font-size: 14px;">详情123</span></p>','11111111111','云南省昆明市呈贡区七甸街道白泥洞',NULL,NULL,'http://127.0.0.1:8081/upload/202301/4bae2c0d-1f10-4b65-8fa1-a74fa1bc7e52.txt',NULL,3),(6,'farkle_4',20,100.00,1,1,'["2", "1"]',0,'2025-03-12','2025-03-12 12:25:13','/202503/85128da8-70a5-467c-a379-29a649cab949.gif',NULL,'','','简介xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','','15288192183',NULL,NULL,NULL,NULL,NULL,4),(4,'farkle',20,100.00,1,1,'["1", "2"]',0,'2025-03-12','2025-03-11 12:25:13','/202601/a4b59912-fb83-4259-9cca-680eec190a38.jpg','','[{"id":2,"name":"b.jpg","url":"/202601/0f55ef26-a201-4c60-a4a8-5e74233cfacd.jpg"}]','[{"id":17,"name":"hello.txt","url":"/202503/fbfd2430-ed50-4bf9-badf-b3485b4ac2e1.txt"},{"id":16,"name":"test.txt","url":"/202503/e03f4fa8-c518-45fe-a0d8-89fe76d62e09.txt"}]','简介xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','<p>详情<img src="http://localhost:8080/upload/202503/996db02c-0a2c-4588-ae6a-6f51d8e39881.jpeg" alt="" data-href="" style=""/></p>','15288192183','',NULL,NULL,'',NULL,1);
-- sys_dictionary DML
INSERT INTO "sys_dictionary" ("id","pid","leaf_flag","children_num","state","dict_key","dict_value","sort","remark","create_user","create_time","update_user","update_time") VALUES (1,0,0,1,1,'sys_dict','系统字典',0,'','admin','2025-03-30 07:07:52','admin','2025-03-30 12:43:39'),(4,1,0,0,1,'sys_dict_1','系统字典1',0,'','admin','2025-03-30 07:21:41','admin','2025-03-30 12:43:50');
-- sys_gen_column DML
INSERT INTO "sys_gen_column" ("id","table_name","column_name","column_remark","label","property","form_type","list_flag","search_flag","required","maxlength") VALUES (150,'app_article','type','','文章类型','type','select',1,1,1,0),(151,'app_article','title','','标题','title','input',1,1,1,255),(152,'app_article','picture','','图片','picture','picture',0,0,0,255),(153,'app_article','introduction','','简介','introduction','input',1,0,0,255),(154,'app_article','content_type','','内容类型','contentType','radio',0,0,1,0),(155,'app_article','content_id','','图文详情','contentId','richtext',0,0,1,0),(156,'app_article','link_url','','超链接','linkUrl','input',0,0,1,255),(157,'app_article','location','','文章位置','location','input',0,0,0,255),(163,'app_version','device','','设备','device','radio',1,1,1,0),(164,'app_version','version','','版本','version','input',1,1,1,20),(165,'app_version','description','','描述','description','input',1,0,1,255),(166,'app_version','download_url','','apk下载url','downloadUrl','input',1,0,1,200),(167,'app_version','force_update','','是否强制更新','forceUpdate','radio',1,1,1,0),(168,'app_user','nickname','','昵称','nickname','input',1,1,1,100),(169,'app_user','avatar','','头像','avatar','picture',1,0,0,200),(170,'app_user','phone','','手机号码','phone','phone',1,1,1,11),(171,'app_user','gender','','性别','gender','select',1,0,0,0),(172,'app_user','birth','','生日','birth','date',1,0,0,0),(173,'app_user','address','','地址','address','input',1,0,0,50),(174,'app_user','integral','','积分','integral','number',0,0,0,0),(175,'app_user','balance','','余额','balance','number',0,0,0,0),(176,'app_user','role','','角色','role','select',0,0,0,0),(177,'app_user','enable_state','','启用状态','enableState','select',1,1,1,0),(178,'app_user','comment','','备注','comment','input',1,0,0,255),(198,'sys_demo','name','','名称','name','input',1,0,1,50),(199,'sys_demo','age','','年龄','age','number',1,0,0,NULL),(200,'sys_demo','score','','分数','score','number',1,0,0,NULL),(201,'sys_demo','gender','Gender','性别','gender','enum',1,0,0,NULL),(202,'sys_demo','state','','启用状态','state','number',1,0,0,NULL),(203,'sys_demo','interest','','兴趣','interest','input',1,0,0,255),(204,'sys_demo','birth','','生日','birth','date',1,0,0,NULL),(205,'sys_demo','regist_time','','注册时间','registTime','datetime',1,0,1,NULL),(206,'sys_demo','avatar','','头像','avatar','input',1,0,1,200),(207,'sys_demo','video','','视频','video','input',1,0,0,200),(208,'sys_demo','photos','','照片','photos','input',1,0,0,500),(209,'sys_demo','attachment','','附件','attachment','input',1,0,0,500),(210,'sys_demo','introduction','','简介','introduction','input',1,0,1,255),(211,'sys_demo','detail','','详情','detail','input',1,0,0,500),(212,'sys_demo','phone','','手机','phone','input',1,0,1,11),(213,'sys_demo','location','','地址','location','input',1,0,0,200),(214,'sys_demo','lng','','经度','lng','number',1,0,0,NULL),(215,'sys_demo','lat','','纬度','lat','number',1,0,0,NULL),(216,'sys_demo','file_input','','文件输入','fileInput','input',1,0,0,200),(241,'sys_organization','pid','','父部门id','pid','number',0,0,0,NULL),(242,'sys_organization','type','','类型','type','radio',1,0,1,NULL),(243,'sys_organization','name','','部门名称','name','input',1,1,1,50),(244,'sys_organization','sort','','显示顺序','sort','number',1,0,0,NULL),(245,'sys_organization','link_name','','联系人','linkName','input',1,0,0,20),(246,'sys_organization','link_phone','','联系电话','linkPhone','input',1,0,0,20),(247,'sys_organization','link_email','','联系邮箱','linkEmail','input',1,0,0,50),(248,'sys_organization','state','EnableState','状态','state','enum',1,0,1,NULL),(249,'sys_organization','creator','','创建账号','creator','input',0,0,1,50),(250,'sys_organization','create_time','','创建时间','createTime','datetime',0,0,1,NULL),(251,'sys_organization','updator','','更新账号','updator','input',0,0,0,50),(252,'sys_organization','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(253,'sys_role','name','','角色名','name','input',1,0,1,50),(254,'sys_role','code','','角色编码','code','input',1,0,0,50),(255,'sys_role','sort','','排序','sort','number',1,0,0,NULL),(256,'sys_role','state','','状态','state','radio',1,0,1,NULL),(257,'sys_role','system_flag','','是否系统内置','systemFlag','radio',0,0,0,NULL),(258,'sys_role','remark','','备注','remark','input',1,0,0,255),(259,'sys_role','create_user','','创建账号','createUser','input',0,0,1,50),(260,'sys_role','create_time','','创建时间','createTime','datetime',1,0,1,NULL),(261,'sys_role','update_user','','更新账号','updateUser','input',0,0,0,50),(262,'sys_role','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(263,'sys_dictionary','pid','','分类父id','pid','number',0,0,0,NULL),(264,'sys_dictionary','leaf_flag','','叶子节点','leafFlag','number',0,0,1,NULL),(265,'sys_dictionary','children_num','','子节点数量','childrenNum','number',0,0,1,NULL),(266,'sys_dictionary','state','EnableState','状态','state','enum',1,1,1,NULL),(267,'sys_dictionary','dict_key','','dict_key','dictKey','input',1,1,1,200),(268,'sys_dictionary','dict_value','','dict_value','dictValue','input',1,1,1,200),(269,'sys_dictionary','sort','','排序值','sort','number',1,0,0,NULL),(270,'sys_dictionary','remark','','备注','remark','input',1,0,0,200),(271,'sys_dictionary','create_user','','创建账号','createUser','input',0,0,1,50),(272,'sys_dictionary','create_time','','创建时间','createTime','datetime',0,0,1,NULL),(273,'sys_dictionary','update_user','','更新账号','updateUser','input',0,0,0,50),(274,'sys_dictionary','update_time','','更新时间','updateTime','datetime',0,0,0,NULL),(275,'sys_data_scope','config_type','','配置类型','configType','radio',1,1,1,NULL),(276,'sys_data_scope','config_id','','用户/机构id','configId','number',1,0,1,NULL),(277,'sys_data_scope','config_name','','用户/结构名称','configName','input',1,1,0,100),(278,'sys_data_scope','data_key','','数据key','dataKey','input',1,1,0,100),(279,'sys_data_scope','data_scope','DataScope','数据权限','dataScope','enum',1,0,1,NULL),(280,'sys_data_scope','custom_ids','','自定义数据权限ids','customIds','input',1,0,0,100);
-- sys_gen_table DML
INSERT INTO "sys_gen_table" ("id","table_name","table_remark") VALUES (1,'sys_demo','系统示例表'),(7,'app_article','app文章'),(8,'app_version','app版本管理'),(9,'app_version','app版本管理'),(10,'app_user','app用户'),(11,'sys_demo','系统示例表'),(12,'sys_demo','系统示例表'),(13,'sys_organization','组织机构'),(14,'sys_organization','组织机构'),(15,'sys_organization','组织机构'),(16,'sys_role','系统角色'),(17,'sys_dictionary','字典表'),(18,'sys_data_scope','数据权限表');
-- sys_login_log DML
-- sys_organization DML
INSERT INTO "sys_organization" ("id","pid","ancestors","type","name","sort","link_name","link_phone","link_email","state","create_user","create_time","update_user","update_time") VALUES (1,0,'1',1,'鸿运集团',0,'','','',1,'admin','2025-03-17 12:57:28','admin','2025-03-24 06:14:50'),(3,1,'1,3',1,'云南鸿运有限公司',0,'1','2','3',1,'admin','2025-03-24 06:18:16','admin','2025-03-24 06:18:16'),(4,1,'1,4',1,'成都分公司',0,NULL,NULL,NULL,1,'admin','2025-03-24 06:18:56','admin','2025-03-24 06:18:56'),(5,3,'1,3,5',2,'技术部',1,'','','',1,'admin','2025-03-24 06:19:59','admin','2025-03-24 06:19:59'),(6,3,'1,3,6',2,'测试部',10,'','','',1,'admin','2025-03-24 06:20:19','admin','2025-03-24 06:20:25'),(7,4,'1,4,7',2,'市场部',0,'','','',1,'admin','2025-03-24 06:20:58','admin','2025-03-24 06:20:58');
-- sys_role DML
INSERT INTO "sys_role" ("id","name","code","sort","state","system_flag","remark","create_user","create_time","update_user","update_time") VALUES (1,'管理员','admin',1,1,0,NULL,'admin','2022-12-18 07:39:13',NULL,NULL),(5,'测试权限','test_auth',2,1,0,'','admin','2023-01-14 14:38:47','admin','2025-03-24 06:49:09');
-- sys_role_auth DML
INSERT INTO "sys_role_auth" ("id","role_id","auth_id") VALUES (32,5,1),(33,5,60),(34,5,48),(35,5,49),(36,5,56),(37,5,57);
-- sys_upload DML
INSERT INTO "sys_upload" ("id","md5","url","path","name","file_suffix","upload_time","file_size") VALUES (1,'9c08c97e408de9fd78462efc0c8a56da','/202601/a4b59912-fb83-4259-9cca-680eec190a38.jpg','C:\tmp\upload\202601\a4b59912-fb83-4259-9cca-680eec190a38.jpg','a.jpg','jpg','2026-01-29 17:21:08',29015),(2,'235295be64fd6e4240191730f653640c','/202601/0f55ef26-a201-4c60-a4a8-5e74233cfacd.jpg','C:\tmp\upload\202601\0f55ef26-a201-4c60-a4a8-5e74233cfacd.jpg','b.jpg','jpg','2026-01-29 17:22:31',46059);
-- sys_user DML
INSERT INTO "sys_user" ("id","account","password","org_id","phone","nickname","avatar","email","gender","state","system_flag","create_user","create_time","update_user","update_time","remark") VALUES (5,'test','$2a$12$IANymdvP6Nmbdaecovl6y.Ubf7xI.P97PWNQ/5VWDmhmJKqk6Nth2',3,'           ','','','',NULL,1,0,'admin','2023-01-14 14:39:16','admin','2025-09-04 16:05:09',''),(1,'admin','$2a$12$JR1RFAWTSJaxsgv7.w/9wOvY0ZtHjFaVordMx25slUQMDbvoNIUiS',NULL,'           ','系统管理员','http://localhost:8081/upload/202509/8b925790-0d11-491c-a9f7-ac4123dfc000','',1,1,1,'admin','2022-12-18 07:44:57','admin','2025-09-04 16:06:31','xxxxxx');
-- sys_user_role DML
INSERT INTO "sys_user_role" ("id","user_id","role_id") VALUES (24,5,5);
