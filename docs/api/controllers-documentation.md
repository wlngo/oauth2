# OAuth2 系统 Controller API 文档

## 概述

本文档详细描述了 `top.wei.oauth2.controller` 包下所有控制器的 API 接口。OAuth2 系统提供了完整的用户、角色、权限管理功能，以及身份认证和授权服务。

## 控制器列表

| 控制器 | 描述 | 基础路径 |
|--------|------|----------|
| [UserController](#usercontroller) | 用户管理 | `/api/users` |
| [AuthController](#authcontroller) | 身份认证 | `/api/auth` |
| [RoleController](#rolecontroller) | 角色管理 | `/api/roles` |
| [PermissionController](#permissioncontroller) | 权限管理 | `/api/permissions` |
| [UserRoleRelationController](#userrolerelationcontroller) | 用户角色关系管理 | `/api/user-role-relations` |
| [RolePermissionRelationController](#rolepermissionrelationcontroller) | 角色权限关系管理 | `/api/role-permission-relations` |
| [CsrfController](#csrfcontroller) | CSRF 令牌 | `/` |
| [CaptchaController](#captchacontroller) | 验证码服务 | `/` |
| [AuthorizationConsentController](#authorizationconsentcontroller) | OAuth2 授权同意 | `/` |

---

## UserController

**描述**: 用户管理控制器，提供用户的 CRUD 操作功能  
**基础路径**: `/api/users`

### 接口列表

#### 1. 获取所有用户

- **方法**: `POST`
- **路径**: `/api/users/getAllUsers`
- **描述**: 分页获取所有用户信息
- **权限要求**: `user:view`
- **参数**:
  - `page` (int, 可选): 页码，默认值 1
  - `size` (int, 可选): 每页大小，默认值 10
  - `keyword` (String, 可选): 搜索关键词
- **返回**: `Rest<PageInfo<User>>`

**请求示例**:
```http
POST /api/users/getAllUsers?page=1&size=10&keyword=admin
```

#### 2. 根据用户名获取用户

- **方法**: `GET`
- **路径**: `/api/users/findByUsername/{username}`
- **描述**: 根据用户名获取用户详细信息
- **权限要求**: `user:view`
- **路径参数**:
  - `username` (String): 用户名
- **返回**: `Rest<User>`

**请求示例**:
```http
GET /api/users/findByUsername/admin
```

#### 3. 创建用户

- **方法**: `POST`
- **路径**: `/api/users/createUser`
- **描述**: 创建新用户
- **权限要求**: `user:create`
- **请求体**: `User` 对象
- **返回**: `Rest<Integer>` (影响行数)

**请求示例**:
```http
POST /api/users/createUser
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "email": "newuser@example.com"
}
```

#### 4. 更新用户

- **方法**: `POST`
- **路径**: `/api/users/updateUser`
- **描述**: 更新用户信息
- **权限要求**: `user:update`
- **请求体**: `User` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 5. 删除用户

- **方法**: `DELETE`
- **路径**: `/api/users/deleteUser/{id}`
- **描述**: 根据用户 ID 删除用户
- **权限要求**: `user:delete`
- **路径参数**:
  - `id` (String): 用户 ID
- **返回**: `Rest<Integer>` (影响行数)

---

## AuthController

**描述**: 身份认证控制器，提供用户身份验证和信息获取功能  
**基础路径**: `/api/auth`

### 接口列表

#### 1. 获取当前用户信息

- **方法**: `GET`
- **路径**: `/api/auth/me`
- **描述**: 获取当前认证用户的基本信息
- **权限要求**: 需要认证
- **参数**: `Authentication` (自动注入)
- **返回**: `ResponseEntity<CurrentUserVo>` 或 `401 Unauthorized`

**响应示例**:
```json
{
  "username": "admin",
  "authorities": ["user:view", "user:create", "role:view"]
}
```

#### 2. 获取用户详细信息

- **方法**: `POST`
- **路径**: `/api/auth/userinfo`
- **描述**: 获取当前认证用户的详细信息
- **权限要求**: 需要认证
- **参数**: `Authentication` (自动注入)
- **返回**: `ResponseEntity<UserInfoVo>` 或 `401 Unauthorized`

---

## RoleController

**描述**: 角色管理控制器，提供角色的 CRUD 操作功能  
**基础路径**: `/api/roles`

### 接口列表

#### 1. 获取所有角色

- **方法**: `POST`
- **路径**: `/api/roles/getAllRoles`
- **描述**: 分页获取所有角色信息
- **权限要求**: `role:view`
- **参数**:
  - `page` (int, 可选): 页码，默认值 1
  - `size` (int, 可选): 每页大小，默认值 10
  - `keyword` (String, 可选): 搜索关键词
- **返回**: `Rest<PageInfo<Role>>`

#### 2. 根据 ID 获取角色

- **方法**: `GET`
- **路径**: `/api/roles/{id}`
- **描述**: 根据角色 ID 获取角色详细信息
- **权限要求**: `role:view`
- **路径参数**:
  - `id` (String): 角色 ID
- **返回**: `Rest<Role>`

#### 3. 创建角色

- **方法**: `POST`
- **路径**: `/api/roles/createRole`
- **描述**: 创建新角色
- **权限要求**: `role:create`
- **请求体**: `Role` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 4. 更新角色

- **方法**: `POST`
- **路径**: `/api/roles/updateRole`
- **描述**: 更新角色信息
- **权限要求**: `role:update`
- **请求体**: `Role` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 5. 删除角色

- **方法**: `DELETE`
- **路径**: `/api/roles/deleteRole/{id}`
- **描述**: 根据角色 ID 删除角色
- **权限要求**: `role:delete`
- **路径参数**:
  - `id` (String): 角色 ID
- **返回**: `Rest<Integer>` (影响行数)

---

## PermissionController

**描述**: 权限管理控制器，提供权限的 CRUD 操作功能  
**基础路径**: `/api/permissions`

### 接口列表

#### 1. 获取所有权限

- **方法**: `POST`
- **路径**: `/api/permissions/getAllPermissions`
- **描述**: 分页获取所有权限信息
- **权限要求**: `permission:view`
- **参数**:
  - `page` (int, 可选): 页码，默认值 1
  - `size` (int, 可选): 每页大小，默认值 10
  - `keyword` (String, 可选): 搜索关键词
- **返回**: `Rest<PageInfo<Permission>>`

#### 2. 根据 ID 获取权限

- **方法**: `GET`
- **路径**: `/api/permissions/{id}`
- **描述**: 根据权限 ID 获取权限详细信息
- **权限要求**: `permission:view`
- **路径参数**:
  - `id` (Integer): 权限 ID
- **返回**: `Rest<Permission>`

#### 3. 创建权限

- **方法**: `POST`
- **路径**: `/api/permissions/createPermission`
- **描述**: 创建新权限
- **权限要求**: `permission:create`
- **请求体**: `Permission` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 4. 更新权限

- **方法**: `POST`
- **路径**: `/api/permissions/updatePermission`
- **描述**: 更新权限信息
- **权限要求**: `permission:update`
- **请求体**: `Permission` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 5. 删除权限

- **方法**: `DELETE`
- **路径**: `/api/permissions/deletePermission/{id}`
- **描述**: 根据权限 ID 删除权限
- **权限要求**: `permission:delete`
- **路径参数**:
  - `id` (Integer): 权限 ID
- **返回**: `Rest<Integer>` (影响行数)

---

## UserRoleRelationController

**描述**: 用户角色关系管理控制器，提供用户和角色之间关系的管理功能  
**基础路径**: `/api/user-role-relations`

### 接口列表

#### 1. 根据用户 ID 获取角色列表

- **方法**: `GET`
- **路径**: `/api/user-role-relations/queryRolesByUserId/{userId}`
- **描述**: 获取指定用户拥有的所有角色
- **权限要求**: `user-role:view`
- **路径参数**:
  - `userId` (String): 用户 ID
- **返回**: `Rest<List<Role>>`

#### 2. 根据用户 ID 获取未分配角色列表

- **方法**: `GET`
- **路径**: `/api/user-role-relations/queryRolesNotAssignedToUser/{userId}`
- **描述**: 获取指定用户未拥有的所有角色
- **权限要求**: `user-role:view`
- **路径参数**:
  - `userId` (String): 用户 ID
- **返回**: `Rest<List<Role>>`

#### 3. 创建用户角色关系

- **方法**: `POST`
- **路径**: `/api/user-role-relations/create`
- **描述**: 创建单个用户角色关系
- **权限要求**: `user-role:create`
- **请求体**: `UserRoleRelation` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 4. 批量创建用户角色关系

- **方法**: `POST`
- **路径**: `/api/user-role-relations/batch-create`
- **描述**: 批量创建用户角色关系
- **权限要求**: `user-role:create`
- **请求体**: `List<UserRoleRelation>` 对象列表
- **返回**: `Rest<Integer>` (影响行数)

#### 5. 删除用户角色关系

- **方法**: `DELETE`
- **路径**: `/api/user-role-relations/{userId}`
- **描述**: 删除指定用户的所有角色关系
- **权限要求**: `user-role:delete`
- **路径参数**:
  - `userId` (String): 用户 ID
- **返回**: `Rest<Integer>` (影响行数)

#### 6. 根据用户 ID 和角色 ID 删除关系

- **方法**: `DELETE`
- **路径**: `/api/user-role-relations/by-user-role`
- **描述**: 删除指定用户和角色之间的关系
- **权限要求**: `user-role:delete`
- **查询参数**:
  - `userId` (String): 用户 ID
  - `roleId` (String): 角色 ID
- **返回**: `Rest<Integer>` (影响行数)

---

## RolePermissionRelationController

**描述**: 角色权限关系管理控制器，提供角色和权限之间关系的管理功能  
**基础路径**: `/api/role-permission-relations`

### 接口列表

#### 1. 根据角色 ID 获取权限列表

- **方法**: `GET`
- **路径**: `/api/role-permission-relations/queryPermissionByRoleId/{roleId}`
- **描述**: 获取指定角色拥有的所有权限
- **权限要求**: `role-permission:view`
- **路径参数**:
  - `roleId` (String): 角色 ID
- **返回**: `Rest<List<Permission>>`

#### 2. 根据角色 ID 获取未分配权限列表

- **方法**: `GET`
- **路径**: `/api/role-permission-relations/queryPermissionNotAssignedToRoleId/{roleId}`
- **描述**: 获取指定角色未拥有的所有权限
- **权限要求**: `user-permission:view`
- **路径参数**:
  - `roleId` (String): 角色 ID
- **返回**: `Rest<List<Permission>>`

#### 3. 创建角色权限关系

- **方法**: `POST`
- **路径**: `/api/role-permission-relations/create`
- **描述**: 创建单个角色权限关系
- **权限要求**: `role-permission:create`
- **请求体**: `RolePermissionRelation` 对象
- **返回**: `Rest<Integer>` (影响行数)

#### 4. 批量创建角色权限关系

- **方法**: `POST`
- **路径**: `/api/role-permission-relations/batch-create`
- **描述**: 批量创建角色权限关系
- **权限要求**: `role-permission:create`
- **请求体**: `List<RolePermissionRelation>` 对象列表
- **返回**: `Rest<Integer>` (影响行数)

#### 5. 删除角色权限关系

- **方法**: `DELETE`
- **路径**: `/api/role-permission-relations/{roleId}`
- **描述**: 删除指定角色的所有权限关系
- **权限要求**: `role-permission:delete`
- **路径参数**:
  - `roleId` (String): 角色 ID
- **返回**: `Rest<Integer>` (影响行数)

#### 6. 根据角色 ID 和权限 ID 删除关系

- **方法**: `DELETE`
- **路径**: `/api/role-permission-relations/by-role-permission`
- **描述**: 删除指定角色和权限之间的关系
- **权限要求**: `role-permission:delete`
- **查询参数**:
  - `roleId` (String): 角色 ID
  - `permissionId` (String): 权限 ID
- **返回**: `Rest<Integer>` (影响行数)

---

## CsrfController

**描述**: CSRF 令牌控制器，提供 CSRF 保护令牌  
**基础路径**: `/`

### 接口列表

#### 1. 获取 CSRF 令牌

- **方法**: `GET`
- **路径**: `/csrf`
- **描述**: 获取 X-CSRF-TOKEN 用于表单提交保护
- **权限要求**: 无
- **参数**: `CsrfToken` (自动注入)
- **返回**: `CsrfToken`

**请求示例**:
```http
GET /csrf
```

**响应示例**:
```json
{
  "token": "a6f5086d-af6b-464f-988b-7a604e46062b",
  "headerName": "X-CSRF-TOKEN",
  "parameterName": "_csrf"
}
```

---

## CaptchaController

**描述**: 验证码服务控制器，提供短信验证码功能  
**基础路径**: `/`

### 接口列表

#### 1. 发送短信验证码

- **方法**: `POST`
- **路径**: `/captcha/sendSms`
- **描述**: 向指定手机号发送短信验证码
- **权限要求**: 无
- **参数**:
  - `phoneNumber` (String): 手机号码
  - `HttpServletRequest` (自动注入): 请求对象
- **返回**: `Rest<?>` 

**请求示例**:
```http
POST /captcha/sendSms
Content-Type: application/x-www-form-urlencoded

phoneNumber=13800138000
```

**成功响应**:
```json
{
  "code": 200,
  "data": null,
  "msg": "成功发送!",
  "identifier": true
}
```

**失败响应**:
```json
{
  "code": 401,
  "msg": "发送失败原因",
  "identifier": false
}
```

---

## AuthorizationConsentController

**描述**: OAuth2 授权同意控制器，处理 OAuth2 授权流程中的用户同意页面  
**基础路径**: `/`

### 接口列表

#### 1. 获取授权范围信息

- **方法**: `GET`
- **路径**: `/oauth2/consent/scope`
- **描述**: 根据客户端 ID 和授权范围查询授权信息
- **权限要求**: 需要认证
- **参数**:
  - `clientId` (String): OAuth2 客户端 ID
  - `scope` (String): 授权范围
  - `state` (String): 状态参数
  - `HttpServletRequest` (自动注入): 请求对象
  - `Principal` (自动注入): 当前用户信息
- **返回**: `Rest<AuthorizationConsentInfoVo>`

**请求示例**:
```http
GET /oauth2/consent/scope?clientId=client123&scope=read+write&state=xyz
```

---

## 通用响应格式

### Rest<T> 响应格式

所有 API 接口都使用统一的响应格式 `Rest<T>`：

```json
{
  "code": 200,
  "data": {},
  "msg": "操作成功",
  "identifier": true
}
```

**字段说明**:
- `code`: HTTP 状态码
- `data`: 响应数据，类型为泛型 T
- `msg`: 响应消息
- `identifier`: 操作是否成功的标识

### 分页响应格式

对于分页查询接口，数据部分使用 `PageInfo<T>` 格式：

```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 10,
    "total": 100,
    "pages": 10,
    "list": [],
    "hasNextPage": true,
    "hasPreviousPage": false
  },
  "msg": "",
  "identifier": true
}
```

## 权限体系

系统采用基于权限的访问控制 (RBAC)，主要权限包括：

### 用户权限
- `user:view` - 查看用户
- `user:create` - 创建用户
- `user:update` - 更新用户
- `user:delete` - 删除用户

### 角色权限
- `role:view` - 查看角色
- `role:create` - 创建角色
- `role:update` - 更新角色
- `role:delete` - 删除角色

### 权限管理权限
- `permission:view` - 查看权限
- `permission:create` - 创建权限
- `permission:update` - 更新权限
- `permission:delete` - 删除权限

### 关系管理权限
- `user-role:view` - 查看用户角色关系
- `user-role:create` - 创建用户角色关系
- `user-role:delete` - 删除用户角色关系
- `role-permission:view` - 查看角色权限关系
- `role-permission:create` - 创建角色权限关系
- `role-permission:delete` - 删除角色权限关系

## 注意事项

1. **认证要求**: 除了 CSRF 令牌和验证码接口外，所有接口都需要有效的身份认证。

2. **权限检查**: 每个接口都有明确的权限要求，需要用户具有相应权限才能访问。

3. **错误处理**: 当权限不足时，系统会返回 `403 Forbidden`；当未认证时，返回 `401 Unauthorized`。

4. **数据验证**: 所有输入数据都会进行相应的验证，不符合要求的请求会返回错误信息。

5. **日志记录**: 系统会记录所有重要操作的日志，便于审计和故障排查。