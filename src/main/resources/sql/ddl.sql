SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_authorization
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_authorization`  (
  `id` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `registered_client_id` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `principal_name` varchar(200) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `authorization_grant_type` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `authorized_scopes` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `attributes` blob NULL,
  `state_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `state` varchar(500) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `authorization_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `authorization_code_value` blob NULL,
  `authorization_code_issued_at` timestamp NULL DEFAULT NULL,
  `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
  `authorization_code_metadata` blob NULL,
  `access_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `access_token_value` blob NULL,
  `access_token_issued_at` timestamp NULL DEFAULT NULL,
  `access_token_expires_at` timestamp NULL DEFAULT NULL,
  `access_token_metadata` blob NULL,
  `access_token_type` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `access_token_scopes` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `oidc_id_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `oidc_id_token_value` blob NULL,
  `oidc_id_token_issued_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_expires_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_metadata` blob NULL,
  `refresh_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `refresh_token_value` blob NULL,
  `refresh_token_issued_at` timestamp NULL DEFAULT NULL,
  `refresh_token_expires_at` timestamp NULL DEFAULT NULL,
  `refresh_token_metadata` blob NULL,
  `user_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `user_code_value` blob NULL,
  `user_code_issued_at` timestamp NULL DEFAULT NULL,
  `user_code_expires_at` timestamp NULL DEFAULT NULL,
  `user_code_metadata` blob NULL,
  `device_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `device_code_value` blob NULL,
  `device_code_issued_at` timestamp NULL DEFAULT NULL,
  `device_code_expires_at` timestamp NULL DEFAULT NULL,
  `device_code_metadata` blob NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `state_index_sha256`(`state_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `authorization_code_value_index_sha256`(`authorization_code_value_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `access_token_value_index_sha256`(`access_token_value_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `oidc_id_token_value__index_sha256`(`oidc_id_token_value_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `refresh_token_value_index_sha256`(`refresh_token_value_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `user_code_value_index_sha256`(`user_code_value_index_sha256` ASC) USING BTREE,
  UNIQUE INDEX `device_code_value_index_sha256`(`device_code_value_index_sha256` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_authorization_consent`  (
  `registered_client_id` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `principal_name` varchar(200) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `authorities` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`registered_client_id`, `principal_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_registered_client`  (
  `id` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `client_id` varchar(100) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `client_secret_expires_at` timestamp NULL DEFAULT NULL,
  `client_name` varchar(200) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `post_logout_redirect_uris` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `scopes` varchar(1000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `client_settings` varchar(2000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `token_settings` varchar(2000) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id` ASC) USING BTREE COMMENT 'client_id 索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_permission`  (
  `permission_id` int NOT NULL AUTO_INCREMENT COMMENT '权限id;权限id',
  `permission_code` varchar(90) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '权限代码;权限代码',
  `icon` varchar(255) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图标的名称',
  `permission_name` varchar(90) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '权限名称;权限名称',
  `path` varchar(90) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '路径;路径',
  `type` int NOT NULL COMMENT '类型;1为一级菜单 2为二级菜单 3为按钮',
  `parent_id` int NOT NULL COMMENT '父级id;父级id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态（1 是，0 否）默认否\r\n',
  PRIMARY KEY (`permission_id`) USING BTREE,
  INDEX `deleted`(`deleted` ASC) USING BTREE COMMENT '逻辑删除索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin COMMENT = '权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_persistent_logins
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`) USING BTREE,
  INDEX `series`(`series` ASC) USING BTREE COMMENT 'series'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_role`  (
  `role_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
  `role_name` varchar(32) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_content` varchar(500) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '展示名称',
  `description` varchar(255) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态（1 是，0 否）默认否',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name` ASC) USING BTREE COMMENT 'role_name 索引',
  INDEX `deleted`(`deleted` ASC) USING BTREE COMMENT '逻辑删除索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role_permission_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_role_permission_relation`  (
  `id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '主键id;主键id',
  `role_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '角色id;角色id',
  `permission_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '权限id;权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE COMMENT 'role_id索引',
  INDEX `permission_id`(`permission_id` ASC) USING BTREE COMMENT 'permission_id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin COMMENT = '角色权限关系表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '用户名称',
  `password` varchar(255) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(32) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(32) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '真实姓名',
  `phone_number_verified` tinyint(1) NULL DEFAULT 0 COMMENT '电话号码是否验证(1 是，0 否）默认否',
  `phone_number` varchar(16) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `email_verified` tinyint(1) NULL DEFAULT 0 COMMENT '电子邮件是否验证(1 是，0 否）默认否',
  `email` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(1) NULL DEFAULT -1 COMMENT '性别   -1 未知 0 女性  1 男性 ',
  `birthdate` datetime NULL DEFAULT NULL COMMENT '出生日期',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态（1 是，0 否）默认否',
  `accountExpired` tinyint(1) NOT NULL DEFAULT 0 COMMENT '定义帐户是否已过期(1 是，0 否）默认否',
  `accountLocked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '定义帐户是否已锁定(1 是，0 否）默认否',
  `credentialsExpired` tinyint(1) NOT NULL DEFAULT 0 COMMENT '定义凭据是否已过期(1 是，0 否）默认否',
  `disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '定义帐户是否被禁用(1 是，0 否）默认否',
  `secret` varchar(255) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE COMMENT '用户名索引',
  UNIQUE INDEX `phone_number`(`phone_number` ASC) USING BTREE COMMENT '手机号索引',
  UNIQUE INDEX `email`(`email` ASC) USING BTREE COMMENT '邮箱索引',
  INDEX `deleted`(`deleted` ASC) USING BTREE COMMENT '逻辑删除索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_role_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user_role_relation`  (
  `id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '标识id',
  `user_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '外键,t_user 用户id',
  `role_id` varchar(64) CHARACTER SET utf8mb4_bin COLLATE utf8mb4_bin NOT NULL COMMENT '外键,t_role 角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE COMMENT 'user_id索引',
  INDEX `role_id`(`role_id` ASC) USING BTREE COMMENT 'role_id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4_bin COLLATE = utf8mb4_bin COMMENT = '用户角色关系表' ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
