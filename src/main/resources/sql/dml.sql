-- 权限表（主键 permission_id）
INSERT INTO t_permission (
    permission_id,
    permission_code,
    permission_name,
    create_at,
    deleted
) VALUES
      (1, 'permission:create', '创建权限', NOW(), 0),
      (2, 'permission:delete', '删除权限', NOW(), 0),
      (3, 'permission:update', '修改权限', NOW(), 0),
      (4, 'permission:view', '查看权限', NOW(), 0),
      (5, 'role:create', '创建角色', NOW(), 0),
      (6, 'role:delete', '删除角色', NOW(), 0),
      (7, 'role-permission:create', '分配角色权限', NOW(), 0),
      (8, 'role-permission:delete', '移除角色权限', NOW(), 0),
      (9, 'role-permission:view', '查看角色权限', NOW(), 0),
      (10, 'role:update', '修改角色', NOW(), 0),
      (11, 'role:view', '查看角色', NOW(), 0),
      (12, 'user:create', '创建用户', NOW(), 0),
      (13, 'user:delete', '删除用户', NOW(), 0),
      (14, 'user-permission:view', '查看用户权限', NOW(), 0),
      (15, 'user-role:create', '分配用户角色', NOW(), 0),
      (16, 'user-role:delete', '移除用户角色', NOW(), 0),
      (17, 'user-role:view', '查看用户角色', NOW(), 0),
      (18, 'user:update', '修改用户', NOW(), 0),
      (19, 'user:view', '查看用户', NOW(), 0),
      (20, 'oauth2-client:create', '创建OAuth2客户端', NOW(), 0),
      (21, 'oauth2-client:delete', '删除OAuth2客户端', NOW(), 0),
      (22, 'oauth2-client:update', '更新OAuth2客户端', NOW(), 0),
      (23, 'oauth2-client:view', '查看OAuth2客户端', NOW(), 0)
    ON DUPLICATE KEY UPDATE permission_id = permission_id;

-- 角色权限关系表（主键 id）
INSERT INTO t_role_permission_relation (id, role_id, permission_id)
VALUES
    ('1', '1', '1'),
    ('2', '1', '2'),
    ('3', '1', '3'),
    ('4', '1', '4'),
    ('5', '1', '5'),
    ('6', '1', '6'),
    ('7', '1', '7'),
    ('8', '1', '8'),
    ('9', '1', '9'),
    ('10', '1', '10'),
    ('11', '1', '11'),
    ('12', '1', '12'),
    ('13', '1', '13'),
    ('14', '1', '14'),
    ('15', '1', '15'),
    ('16', '1', '16'),
    ('17', '1', '17'),
    ('18', '1', '18'),
    ('19', '1', '19'),
    ('20', '1', '20'),
    ('21', '1', '21'),
    ('22', '1', '22'),
    ('23', '1', '23')
    ON DUPLICATE KEY UPDATE id = id;

-- 角色表（主键 role_id）
INSERT INTO t_role (role_id, role_name, role_content, description, created_at, create_id, updated_at, update_id, deleted)
VALUES ('1', 'ADMIN', '系统管理员', '全局最高权限', NOW(), NULL, NOW(), NULL, 0)
    ON DUPLICATE KEY UPDATE role_id = role_id;

-- 用户表（主键 user_id）
INSERT INTO t_user (user_id, username, password, nick_name, real_name, email, email_verified, phone_number, phone_number_verified, gender, birthdate, avatar_url, account_expired, account_locked, credentials_expired, disabled, deleted, secret, created_at, updated_at)
VALUES ('1', 'admin', '{bcrypt}$2a$10$7FhmqQTZq3siuhslJkKKPuUYA1fGfYsyq2xohqi3X7d5iaaHqR9xG', 'admin', 'admin', 'admin@admin.com', 0, '18000000000', 0, 1, NULL, NULL, 0, 0, 0, 0, 0, NULL, '2025-09-05 15:53:00', '2025-09-05 15:53:44')
    ON DUPLICATE KEY UPDATE user_id = user_id;

-- 用户角色关系表（主键 id）
INSERT INTO t_user_role_relation (id, user_id, role_id)
VALUES ('1', '1', '1')
    ON DUPLICATE KEY UPDATE id = id;