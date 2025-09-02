# OAuth2 系统 API 接口文档

## 概述

本文档详细描述了 OAuth2 系统中的所有 API 接口，包括认证、用户管理、角色管理、权限管理、菜单管理以及相关关系管理的接口。

## 通用响应格式

所有 API 接口都采用统一的响应格式：

```json
{
  "code": 200,
  "data": {},
  "msg": "",
  "identifier": true
}
```

- `code`: 业务状态码，通常 200 表示成功
- `data`: 响应数据，具体格式根据接口而定
- `msg`: 提示信息
- `identifier`: 标识位，true 表示成功，false 表示失败

## 分页响应格式

分页接口的响应数据格式为：

```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 10,
    "total": 100,
    "pages": 10,
    "list": []
  },
  "msg": "",
  "identifier": true
}
```

---

## 1. AuthController - 认证控制器

基础路径: `/api/auth`

### 1.1 获取当前用户信息

**接口地址**: `GET /api/auth/me`

**接口描述**: 获取当前登录用户的基本信息

**请求参数**: 无

**请求头**: 需要身份认证

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "username": "admin",
    "authorities": ["user:view", "user:create", "role:view"]
  },
  "msg": "",
  "identifier": true
}
```

**响应字段说明**:
- `username`: 用户名
- `authorities`: 用户权限列表

### 1.2 获取用户详细信息

**接口地址**: `POST /api/auth/userinfo`

**接口描述**: 获取当前登录用户的详细信息

**请求参数**: 无

**请求头**: 需要身份认证

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "userId": "u001",
    "username": "admin",
    "nickName": "管理员",
    "realName": "张三",
    "email": "admin@example.com",
    "emailVerified": true,
    "phoneNumber": "13800138000",
    "phoneNumberVerified": true,
    "gender": 1,
    "birthdate": "1990-01-01",
    "avatarUrl": "http://example.com/avatar.jpg",
    "authorities": ["user:view", "user:create", "role:view"],
    "createdAt": "2023-01-01T00:00:00.000Z",
    "updatedAt": "2023-01-01T00:00:00.000Z"
  },
  "msg": "",
  "identifier": true
}
```

### 1.3 获取用户菜单树

**接口地址**: `POST /api/auth`

**接口描述**: 获取当前用户的菜单树形结构

**请求参数**: 无

**请求头**: 需要身份认证

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "1": {
      "menuId": "m001",
      "menuName": "系统管理",
      "menuPath": "/system",
      "menuIcon": "system",
      "parentId": null,
      "sortOrder": 1,
      "menuType": 0,
      "visible": true,
      "description": "系统管理模块"
    }
  },
  "msg": "",
  "identifier": true
}
```

---

## 2. MenuController - 菜单管理控制器

基础路径: `/api/menus`

### 2.1 获取所有菜单（分页）

**接口地址**: `POST /api/menus/getAllMenus`

**接口描述**: 分页获取菜单列表

**权限要求**: `menus:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `keyword` (string, 可选): 搜索关键词

**请求示例**:
```
POST /api/menus/getAllMenus?page=1&size=10&keyword=系统
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "menuId": "m001",
        "menuName": "系统管理",
        "menuPath": "/system",
        "menuIcon": "system",
        "parentId": null,
        "sortOrder": 1,
        "menuType": 0,
        "visible": true,
        "description": "系统管理模块",
        "createdAt": "2023-01-01T00:00:00.000Z",
        "createId": "u001",
        "updatedAt": "2023-01-01T00:00:00.000Z",
        "updateId": "u001"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 2.2 根据ID获取菜单

**接口地址**: `GET /api/menus/{id}`

**接口描述**: 根据菜单ID获取菜单详情

**权限要求**: `menus:view`

**路径参数**:
- `id` (string): 菜单ID

**请求示例**:
```
GET /api/menus/m001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "menuId": "m001",
    "menuName": "系统管理",
    "menuPath": "/system",
    "menuIcon": "system",
    "parentId": null,
    "sortOrder": 1,
    "menuType": 0,
    "visible": true,
    "description": "系统管理模块",
    "createdAt": "2023-01-01T00:00:00.000Z",
    "createId": "u001",
    "updatedAt": "2023-01-01T00:00:00.000Z",
    "updateId": "u001"
  },
  "msg": "",
  "identifier": true
}
```

### 2.3 创建菜单

**接口地址**: `POST /api/menus/createMenu`

**接口描述**: 创建新菜单

**权限要求**: `menus:create`

**请求体**:
```json
{
  "menuName": "用户管理",
  "menuPath": "/user",
  "menuIcon": "user",
  "parentId": "m001",
  "sortOrder": 2,
  "menuType": 1,
  "visible": true,
  "description": "用户管理页面"
}
```

**请求体字段说明**:
- `menuName` (string): 菜单名称
- `menuPath` (string): 菜单路径
- `menuIcon` (string): 菜单图标
- `parentId` (string, 可选): 父菜单ID
- `sortOrder` (int): 排序号
- `menuType` (int): 菜单类型（0目录 1菜单 2按钮）
- `visible` (boolean): 是否可见
- `description` (string, 可选): 描述

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 2.4 更新菜单

**接口地址**: `POST /api/menus/updateMenu`

**接口描述**: 更新菜单信息

**权限要求**: `menus:update`

**请求体**:
```json
{
  "menuId": "m001",
  "menuName": "系统管理",
  "menuPath": "/system",
  "menuIcon": "system",
  "parentId": null,
  "sortOrder": 1,
  "menuType": 0,
  "visible": true,
  "description": "系统管理模块"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 2.5 删除菜单

**接口地址**: `DELETE /api/menus/deleteMenu/{id}`

**接口描述**: 删除菜单

**权限要求**: `menus:delete`

**路径参数**:
- `id` (string): 菜单ID

**请求示例**:
```
DELETE /api/menus/deleteMenu/m001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 3. PermissionController - 权限管理控制器

基础路径: `/api/permissions`

### 3.1 获取所有权限（分页）

**接口地址**: `POST /api/permissions/getAllPermissions`

**接口描述**: 分页获取权限列表

**权限要求**: `permission:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `keyword` (string, 可选): 搜索关键词

**请求示例**:
```
POST /api/permissions/getAllPermissions?page=1&size=10&keyword=用户
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "permissionId": 1,
        "permissionCode": "user:view",
        "permissionName": "查看用户",
        "createAt": "2023-01-01T00:00:00.000Z",
        "createId": "u001",
        "updateAt": "2023-01-01T00:00:00.000Z",
        "updateId": "u001"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 3.2 根据ID获取权限

**接口地址**: `GET /api/permissions/{id}`

**接口描述**: 根据权限ID获取权限详情

**权限要求**: `permission:view`

**路径参数**:
- `id` (integer): 权限ID

**请求示例**:
```
GET /api/permissions/1
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "permissionId": 1,
    "permissionCode": "user:view",
    "permissionName": "查看用户",
    "createAt": "2023-01-01T00:00:00.000Z",
    "createId": "u001",
    "updateAt": "2023-01-01T00:00:00.000Z",
    "updateId": "u001"
  },
  "msg": "",
  "identifier": true
}
```

### 3.3 创建权限

**接口地址**: `POST /api/permissions/createPermission`

**接口描述**: 创建新权限

**权限要求**: `permission:create`

**请求体**:
```json
{
  "permissionCode": "user:create",
  "permissionName": "创建用户"
}
```

**请求体字段说明**:
- `permissionCode` (string): 权限代码
- `permissionName` (string): 权限名称

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 3.4 更新权限

**接口地址**: `POST /api/permissions/updatePermission`

**接口描述**: 更新权限信息

**权限要求**: `permission:update`

**请求体**:
```json
{
  "permissionId": 1,
  "permissionCode": "user:view",
  "permissionName": "查看用户"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 3.5 删除权限

**接口地址**: `DELETE /api/permissions/deletePermission/{id}`

**接口描述**: 删除权限

**权限要求**: `permission:delete`

**路径参数**:
- `id` (integer): 权限ID

**请求示例**:
```
DELETE /api/permissions/deletePermission/1
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 4. RoleController - 角色管理控制器

基础路径: `/api/roles`

### 4.1 获取所有角色（分页）

**接口地址**: `POST /api/roles/getAllRoles`

**接口描述**: 分页获取角色列表

**权限要求**: `user-role:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `keyword` (string, 可选): 搜索关键词

**请求示例**:
```
POST /api/roles/getAllRoles?page=1&size=10&keyword=管理员
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 3,
    "list": [
      {
        "roleId": "r001",
        "roleName": "admin",
        "roleContent": "管理员",
        "description": "系统管理员角色",
        "createdAt": "2023-01-01T00:00:00.000Z",
        "createId": "u001",
        "updatedAt": "2023-01-01T00:00:00.000Z",
        "updateId": "u001"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 4.2 根据ID获取角色

**接口地址**: `GET /api/roles/{id}`

**接口描述**: 根据角色ID获取角色详情

**权限要求**: `user-role:view`

**路径参数**:
- `id` (string): 角色ID

**请求示例**:
```
GET /api/roles/r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "roleId": "r001",
    "roleName": "admin",
    "roleContent": "管理员",
    "description": "系统管理员角色",
    "createdAt": "2023-01-01T00:00:00.000Z",
    "createId": "u001",
    "updatedAt": "2023-01-01T00:00:00.000Z",
    "updateId": "u001"
  },
  "msg": "",
  "identifier": true
}
```

### 4.3 创建角色

**接口地址**: `POST /api/roles/createRole`

**接口描述**: 创建新角色

**权限要求**: `role:create`

**请求体**:
```json
{
  "roleName": "operator",
  "roleContent": "操作员",
  "description": "系统操作员角色"
}
```

**请求体字段说明**:
- `roleName` (string): 角色名称
- `roleContent` (string): 角色展示名称
- `description` (string, 可选): 角色描述

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 4.4 更新角色

**接口地址**: `POST /api/roles/updateRole`

**接口描述**: 更新角色信息

**权限要求**: `role:update`

**请求体**:
```json
{
  "roleId": "r001",
  "roleName": "admin",
  "roleContent": "管理员",
  "description": "系统管理员角色"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 4.5 删除角色

**接口地址**: `DELETE /api/roles/deleteRole/{id}`

**接口描述**: 删除角色

**权限要求**: `role:delete`

**路径参数**:
- `id` (string): 角色ID

**请求示例**:
```
DELETE /api/roles/deleteRole/r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 5. RoleMenuRelationController - 角色菜单关系管理控制器

基础路径: `/api/role-menu-relations`

### 5.1 获取所有角色菜单关系（分页）

**接口地址**: `POST /api/role-menu-relations/getAllRoleMenuRelations`

**接口描述**: 分页获取角色菜单关系列表

**权限要求**: `role-menu:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `roleId` (string, 可选): 角色ID，用于筛选

**请求示例**:
```
POST /api/role-menu-relations/getAllRoleMenuRelations?page=1&size=10&roleId=r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "id": "rm001",
        "roleId": "r001",
        "menuId": "m001"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 5.2 根据ID获取角色菜单关系

**接口地址**: `GET /api/role-menu-relations/{id}`

**接口描述**: 根据关系ID获取角色菜单关系详情

**权限要求**: `role-menu:view`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
GET /api/role-menu-relations/rm001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "id": "rm001",
    "roleId": "r001",
    "menuId": "m001"
  },
  "msg": "",
  "identifier": true
}
```

### 5.3 创建角色菜单关系

**接口地址**: `POST /api/role-menu-relations/createRoleMenuRelation`

**接口描述**: 创建新的角色菜单关系

**权限要求**: `role-menu:create`

**请求体**:
```json
{
  "roleId": "r001",
  "menuId": "m001"
}
```

**请求体字段说明**:
- `roleId` (string): 角色ID
- `menuId` (string): 菜单ID

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 5.4 更新角色菜单关系

**接口地址**: `POST /api/role-menu-relations/updateRoleMenuRelation`

**接口描述**: 更新角色菜单关系

**权限要求**: `role-menu:update`

**请求体**:
```json
{
  "id": "rm001",
  "roleId": "r001",
  "menuId": "m002"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 5.5 删除角色菜单关系

**接口地址**: `DELETE /api/role-menu-relations/deleteRoleMenuRelation/{id}`

**接口描述**: 删除角色菜单关系

**权限要求**: `role-menu:delete`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
DELETE /api/role-menu-relations/deleteRoleMenuRelation/rm001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 6. RolePermissionRelationController - 角色权限关系管理控制器

基础路径: `/api/role-permission-relations`

### 6.1 分页查询角色权限关系

**接口地址**: `GET /api/role-permission-relations/list`

**接口描述**: 分页获取角色权限关系列表

**权限要求**: `role-permission:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `roleId` (string, 可选): 角色ID，用于筛选
- `permissionId` (string, 可选): 权限ID，用于筛选

**请求示例**:
```
GET /api/role-permission-relations/list?page=1&size=10&roleId=r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "id": "rp001",
        "roleId": "r001",
        "permissionId": "1"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 6.2 根据ID获取角色权限关系

**接口地址**: `GET /api/role-permission-relations/{id}`

**接口描述**: 根据关系ID获取角色权限关系详情

**权限要求**: `role-permission:view`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
GET /api/role-permission-relations/rp001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "id": "rp001",
    "roleId": "r001",
    "permissionId": "1"
  },
  "msg": "",
  "identifier": true
}
```

### 6.3 根据角色ID获取权限关系列表

**接口地址**: `GET /api/role-permission-relations/by-role/{roleId}`

**接口描述**: 根据角色ID获取该角色的所有权限关系

**权限要求**: `role-permission:view`

**路径参数**:
- `roleId` (string): 角色ID

**请求示例**:
```
GET /api/role-permission-relations/by-role/r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "id": "rp001",
      "roleId": "r001",
      "permissionId": "1"
    },
    {
      "id": "rp002",
      "roleId": "r001",
      "permissionId": "2"
    }
  ],
  "msg": "",
  "identifier": true
}
```

### 6.4 根据权限ID获取角色关系列表

**接口地址**: `GET /api/role-permission-relations/by-permission/{permissionId}`

**接口描述**: 根据权限ID获取拥有该权限的所有角色关系

**权限要求**: `role-permission:view`

**路径参数**:
- `permissionId` (string): 权限ID

**请求示例**:
```
GET /api/role-permission-relations/by-permission/1
```

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "id": "rp001",
      "roleId": "r001",
      "permissionId": "1"
    },
    {
      "id": "rp003",
      "roleId": "r002",
      "permissionId": "1"
    }
  ],
  "msg": "",
  "identifier": true
}
```

### 6.5 创建角色权限关系

**接口地址**: `POST /api/role-permission-relations/create`

**接口描述**: 创建新的角色权限关系

**权限要求**: `role-permission:create`

**请求体**:
```json
{
  "roleId": "r001",
  "permissionId": "1"
}
```

**请求体字段说明**:
- `roleId` (string): 角色ID
- `permissionId` (string): 权限ID

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 6.6 批量创建角色权限关系

**接口地址**: `POST /api/role-permission-relations/batch-create`

**接口描述**: 批量创建角色权限关系

**权限要求**: `role-permission:create`

**请求体**:
```json
[
  {
    "roleId": "r001",
    "permissionId": "1"
  },
  {
    "roleId": "r001",
    "permissionId": "2"
  }
]
```

**响应示例**:
```json
{
  "code": 200,
  "data": 2,
  "msg": "",
  "identifier": true
}
```

### 6.7 更新角色权限关系

**接口地址**: `PUT /api/role-permission-relations/update`

**接口描述**: 更新角色权限关系

**权限要求**: `role-permission:update`

**请求体**:
```json
{
  "id": "rp001",
  "roleId": "r001",
  "permissionId": "2"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 6.8 删除角色权限关系

**接口地址**: `DELETE /api/role-permission-relations/{id}`

**接口描述**: 根据关系ID删除角色权限关系

**权限要求**: `role-permission:delete`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
DELETE /api/role-permission-relations/rp001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 6.9 根据角色和权限ID删除关系

**接口地址**: `DELETE /api/role-permission-relations/by-role-permission`

**接口描述**: 根据角色ID和权限ID删除关系

**权限要求**: `role-permission:delete`

**请求参数**:
- `roleId` (string): 角色ID
- `permissionId` (string): 权限ID

**请求示例**:
```
DELETE /api/role-permission-relations/by-role-permission?roleId=r001&permissionId=1
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 7. UserController - 用户管理控制器

基础路径: `/api/users`

### 7.1 获取所有用户（分页）

**接口地址**: `POST /api/users/getAllUsers`

**接口描述**: 分页获取用户列表

**权限要求**: `user:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `keyword` (string, 可选): 搜索关键词

**请求示例**:
```
POST /api/users/getAllUsers?page=1&size=10&keyword=张三
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "userId": "u001",
        "username": "admin",
        "nickName": "管理员",
        "realName": "张三",
        "email": "admin@example.com",
        "emailVerified": true,
        "phoneNumber": "13800138000",
        "phoneNumberVerified": true,
        "gender": 1,
        "birthdate": "1990-01-01",
        "avatarUrl": "http://example.com/avatar.jpg",
        "accountExpired": false,
        "accountLocked": false,
        "credentialsExpired": false,
        "disabled": false,
        "secret": "secret123",
        "createdAt": "2023-01-01T00:00:00.000Z",
        "updatedAt": "2023-01-01T00:00:00.000Z"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 7.2 根据用户名获取用户

**接口地址**: `GET /api/users/findByUsername/{username}`

**接口描述**: 根据用户名获取用户信息

**权限要求**: `user:view`

**路径参数**:
- `username` (string): 用户名

**请求示例**:
```
GET /api/users/findByUsername/admin
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "userId": "u001",
    "username": "admin",
    "nickName": "管理员",
    "realName": "张三",
    "email": "admin@example.com",
    "emailVerified": true,
    "phoneNumber": "13800138000",
    "phoneNumberVerified": true,
    "gender": 1,
    "birthdate": "1990-01-01",
    "avatarUrl": "http://example.com/avatar.jpg",
    "accountExpired": false,
    "accountLocked": false,
    "credentialsExpired": false,
    "disabled": false,
    "secret": "secret123",
    "createdAt": "2023-01-01T00:00:00.000Z",
    "updatedAt": "2023-01-01T00:00:00.000Z"
  },
  "msg": "",
  "identifier": true
}
```

### 7.3 创建用户

**接口地址**: `POST /api/users/createUser`

**接口描述**: 创建新用户

**权限要求**: `user:create`

**请求体**:
```json
{
  "username": "newuser",
  "password": "password123",
  "nickName": "新用户",
  "realName": "李四",
  "email": "newuser@example.com",
  "phoneNumber": "13800138001",
  "gender": 1,
  "birthdate": "1995-01-01"
}
```

**请求体字段说明**:
- `username` (string): 用户名
- `password` (string): 密码
- `nickName` (string, 可选): 昵称
- `realName` (string, 可选): 真实姓名
- `email` (string, 可选): 邮箱
- `phoneNumber` (string, 可选): 手机号
- `gender` (byte, 可选): 性别（-1=未知，0=女，1=男）
- `birthdate` (date, 可选): 出生日期

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 7.4 更新用户

**接口地址**: `POST /api/users/updateUser`

**接口描述**: 更新用户信息

**权限要求**: `user:update`

**请求体**:
```json
{
  "userId": "u001",
  "username": "admin",
  "nickName": "管理员",
  "realName": "张三",
  "email": "admin@example.com",
  "phoneNumber": "13800138000",
  "gender": 1,
  "birthdate": "1990-01-01"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 7.5 删除用户

**接口地址**: `DELETE /api/users/deleteUser/{id}`

**接口描述**: 删除用户

**权限要求**: `user:delete`

**路径参数**:
- `id` (string): 用户ID

**请求示例**:
```
DELETE /api/users/deleteUser/u001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 8. UserRoleRelationController - 用户角色关系管理控制器

基础路径: `/api/user-role-relations`

### 8.1 分页查询用户角色关系

**接口地址**: `GET /api/user-role-relations/list`

**接口描述**: 分页获取用户角色关系列表

**权限要求**: `user-role:view`

**请求参数**:
- `page` (int, 可选): 页码，默认值 1
- `size` (int, 可选): 每页大小，默认值 10
- `userId` (string, 可选): 用户ID，用于筛选
- `roleId` (string, 可选): 角色ID，用于筛选

**请求示例**:
```
GET /api/user-role-relations/list?page=1&size=10&userId=u001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 5,
    "list": [
      {
        "id": "ur001",
        "userId": "u001",
        "roleId": "r001"
      }
    ]
  },
  "msg": "",
  "identifier": true
}
```

### 8.2 根据ID获取用户角色关系

**接口地址**: `GET /api/user-role-relations/{id}`

**接口描述**: 根据关系ID获取用户角色关系详情

**权限要求**: `user-role:view`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
GET /api/user-role-relations/ur001
```

**响应示例**:
```json
{
  "code": 200,
  "data": {
    "id": "ur001",
    "userId": "u001",
    "roleId": "r001"
  },
  "msg": "",
  "identifier": true
}
```

### 8.3 根据用户ID获取角色关系列表

**接口地址**: `GET /api/user-role-relations/by-user/{userId}`

**接口描述**: 根据用户ID获取该用户的所有角色关系

**权限要求**: `user-role:view`

**路径参数**:
- `userId` (string): 用户ID

**请求示例**:
```
GET /api/user-role-relations/by-user/u001
```

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "id": "ur001",
      "userId": "u001",
      "roleId": "r001"
    },
    {
      "id": "ur002",
      "userId": "u001",
      "roleId": "r002"
    }
  ],
  "msg": "",
  "identifier": true
}
```

### 8.4 根据角色ID获取用户关系列表

**接口地址**: `GET /api/user-role-relations/by-role/{roleId}`

**接口描述**: 根据角色ID获取拥有该角色的所有用户关系

**权限要求**: `user-role:view`

**路径参数**:
- `roleId` (string): 角色ID

**请求示例**:
```
GET /api/user-role-relations/by-role/r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": [
    {
      "id": "ur001",
      "userId": "u001",
      "roleId": "r001"
    },
    {
      "id": "ur003",
      "userId": "u002",
      "roleId": "r001"
    }
  ],
  "msg": "",
  "identifier": true
}
```

### 8.5 创建用户角色关系

**接口地址**: `POST /api/user-role-relations/create`

**接口描述**: 创建新的用户角色关系

**权限要求**: `user-role:create`

**请求体**:
```json
{
  "userId": "u001",
  "roleId": "r001"
}
```

**请求体字段说明**:
- `userId` (string): 用户ID
- `roleId` (string): 角色ID

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 8.6 批量创建用户角色关系

**接口地址**: `POST /api/user-role-relations/batch-create`

**接口描述**: 批量创建用户角色关系

**权限要求**: `user-role:create`

**请求体**:
```json
[
  {
    "userId": "u001",
    "roleId": "r001"
  },
  {
    "userId": "u001",
    "roleId": "r002"
  }
]
```

**响应示例**:
```json
{
  "code": 200,
  "data": 2,
  "msg": "",
  "identifier": true
}
```

### 8.7 更新用户角色关系

**接口地址**: `PUT /api/user-role-relations/update`

**接口描述**: 更新用户角色关系

**权限要求**: `user-role:update`

**请求体**:
```json
{
  "id": "ur001",
  "userId": "u001",
  "roleId": "r002"
}
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 8.8 删除用户角色关系

**接口地址**: `DELETE /api/user-role-relations/{id}`

**接口描述**: 根据关系ID删除用户角色关系

**权限要求**: `user-role:delete`

**路径参数**:
- `id` (string): 关系ID

**请求示例**:
```
DELETE /api/user-role-relations/ur001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 8.9 根据用户和角色ID删除关系

**接口地址**: `DELETE /api/user-role-relations/by-user-role`

**接口描述**: 根据用户ID和角色ID删除关系

**权限要求**: `user-role:delete`

**请求参数**:
- `userId` (string): 用户ID
- `roleId` (string): 角色ID

**请求示例**:
```
DELETE /api/user-role-relations/by-user-role?userId=u001&roleId=r001
```

**响应示例**:
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

---

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权（需要登录） |
| 403 | 禁止访问（权限不足） |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 注意事项

1. 所有需要权限的接口都需要在请求头中携带有效的身份认证信息
2. 分页查询的页码从 1 开始
3. 日期格式采用 ISO 8601 标准
4. 所有删除操作都是逻辑删除，不会物理删除数据
5. 创建和更新操作会自动填充创建时间、更新时间、创建人、更新人等字段