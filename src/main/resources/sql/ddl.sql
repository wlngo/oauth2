SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_authorization
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_authorization`  (
    `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `principal_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `authorization_grant_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `authorized_scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `attributes` blob NULL,
    `state_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `state` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `authorization_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `authorization_code_value` blob NULL,
    `authorization_code_issued_at` timestamp NULL DEFAULT NULL,
    `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
    `authorization_code_metadata` blob NULL,
    `access_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `access_token_value` blob NULL,
    `access_token_issued_at` timestamp NULL DEFAULT NULL,
    `access_token_expires_at` timestamp NULL DEFAULT NULL,
    `access_token_metadata` blob NULL,
    `access_token_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `access_token_scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `oidc_id_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `oidc_id_token_value` blob NULL,
    `oidc_id_token_issued_at` timestamp NULL DEFAULT NULL,
    `oidc_id_token_expires_at` timestamp NULL DEFAULT NULL,
    `oidc_id_token_metadata` blob NULL,
    `refresh_token_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `refresh_token_value` blob NULL,
    `refresh_token_issued_at` timestamp NULL DEFAULT NULL,
    `refresh_token_expires_at` timestamp NULL DEFAULT NULL,
    `refresh_token_metadata` blob NULL,
    `user_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `user_code_value` blob NULL,
    `user_code_issued_at` timestamp NULL DEFAULT NULL,
    `user_code_expires_at` timestamp NULL DEFAULT NULL,
    `user_code_metadata` blob NULL,
    `device_code_value_index_sha256` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
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
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_authorization_consent`  (
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `principal_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `authorities` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_registered_client`  (
    `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `client_secret_expires_at` timestamp NULL DEFAULT NULL,
    `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `post_logout_redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
    `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `client_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `token_settings` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `client_id`(`client_id` ASC) USING BTREE COMMENT 'client_id 索引'
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth2_scope
-- ----------------------------
CREATE TABLE IF NOT EXISTS `oauth2_scope`  (
     `id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端 ID',
    `scope` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识，如 message.read、profile',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称，简短标题',
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限说明，展示在授权页面',
    `is_visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否在授权页面展示该权限',
    `is_default` tinyint(1) NULL DEFAULT 1 COMMENT '是否为默认勾选的权限',
    `sort_order` int NULL DEFAULT 0 COMMENT '显示顺序（数字越小越靠前）',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_client_scope` (`registered_client_id`,`scope`),
    KEY `registered_client_id` (`registered_client_id`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OAuth2 客户端权限定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_permission`  (
    `permission_id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `permission_code` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限代码',
    `permission_name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限名称',
    `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人ID',
    `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人ID',
    `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态（1 是，0 否）',
    PRIMARY KEY (`permission_id`) USING BTREE,
    UNIQUE INDEX `uk_permission_code`(`permission_code` ASC) USING BTREE,
    INDEX `idx_deleted`(`deleted` ASC) USING BTREE COMMENT '逻辑删除索引'
    ) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '权限表（精简版）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_persistent_logins
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_persistent_logins`  (
    `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `last_used` timestamp NOT NULL,
    PRIMARY KEY (`series`) USING BTREE,
    INDEX `series`(`series` ASC) USING BTREE COMMENT 'series'
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_role`  (
    `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
    `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
    `role_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '展示名称',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人ID',
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人ID',
    `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态（1 是，0 否）默认否',
    PRIMARY KEY (`role_id`) USING BTREE,
    UNIQUE INDEX `idx_role_name`(`role_name` ASC) USING BTREE,
    INDEX `idx_role_deleted`(`deleted` ASC) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role_permission_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_role_permission_relation`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '主键id;主键id',
    `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色id;角色id',
    `permission_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限id;权限id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role_id`(`role_id` ASC) USING BTREE COMMENT 'role_id索引',
    INDEX `permission_id`(`permission_id` ASC) USING BTREE COMMENT 'permission_id索引'
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色权限关系表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user`  (
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID，主键，唯一标识用户',
    `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名，唯一，用于登录',
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户密码，建议加密存储（如 bcrypt）',
    `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户昵称，用于展示',
    `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '真实姓名，用于实名认证或内部识别',
    `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户邮箱地址，唯一，可用于找回密码或登录',
    `email_verified` tinyint(1) NOT NULL DEFAULT 0 COMMENT '邮箱是否已验证，1 表示已验证，0 表示未验证',
    `phone_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号，唯一，可用于登录或接收验证码',
    `phone_number_verified` tinyint(1) NOT NULL DEFAULT 0 COMMENT '手机号是否已验证，1 表示已验证，0 表示未验证',
    `gender` tinyint NULL DEFAULT -1 COMMENT '性别：-1=未知，0=女，1=男',
    `birthdate` date NULL DEFAULT NULL COMMENT '出生日期，用于年龄计算或生日提醒',
    `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户头像图片URL',
    `account_expired` tinyint(1) NOT NULL DEFAULT 0 COMMENT '账户是否过期，1 是，0 否',
    `account_locked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '账户是否被锁定，1 是，0 否',
    `credentials_expired` tinyint(1) NOT NULL DEFAULT 0 COMMENT '凭据（密码）是否过期，1 是，0 否',
    `disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '账户是否被禁用，1 是，0 否',
    `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除，1 表示已删除，0 表示未删除',
    `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端密钥，用于某些认证场景',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录用户创建时间，自动填充',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录用户最后更新时间，自动更新',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `username`(`username` ASC) USING BTREE,
    UNIQUE INDEX `email`(`email` ASC) USING BTREE,
    UNIQUE INDEX `phone_number`(`phone_number` ASC) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_role_relation
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user_role_relation`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标识id',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '外键,t_user 用户id',
    `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '外键,t_role 角色id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id`(`user_id` ASC) USING BTREE COMMENT 'user_id索引',
    INDEX `role_id`(`role_id` ASC) USING BTREE COMMENT 'role_id索引'
    ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色关系表' ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
