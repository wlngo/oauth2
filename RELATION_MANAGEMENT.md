# RolePermissionRelation and UserRoleRelation Management

This document describes the management APIs added for RolePermissionRelation and UserRoleRelation entities.

## RolePermissionRelation API Endpoints

Base URL: `/api/role-permission-relations`

### GET Endpoints
- `GET /list` - 分页查询角色权限关系 (List role-permission relations with pagination)
  - Parameters: `page`, `size`, `roleId` (optional), `permissionId` (optional)
- `GET /{id}` - 根据ID获取角色权限关系 (Get by ID)
- `GET /by-role/{roleId}` - 根据角色ID获取权限关系列表 (Get by role ID)
- `GET /by-permission/{permissionId}` - 根据权限ID获取角色关系列表 (Get by permission ID)

### POST Endpoints
- `POST /create` - 创建角色权限关系 (Create single relation)
- `POST /batch-create` - 批量创建角色权限关系 (Create multiple relations)

### PUT Endpoints
- `PUT /update` - 更新角色权限关系 (Update relation)

### DELETE Endpoints
- `DELETE /{id}` - 删除角色权限关系 (Delete by ID)
- `DELETE /by-role-permission` - 根据角色ID和权限ID删除关系 (Delete by role and permission IDs)
  - Parameters: `roleId`, `permissionId`

## UserRoleRelation API Endpoints

Base URL: `/api/user-role-relations`

### GET Endpoints
- `GET /list` - 分页查询用户角色关系 (List user-role relations with pagination)
  - Parameters: `page`, `size`, `userId` (optional), `roleId` (optional)
- `GET /{id}` - 根据ID获取用户角色关系 (Get by ID)
- `GET /by-user/{userId}` - 根据用户ID获取角色关系列表 (Get by user ID)
- `GET /by-role/{roleId}` - 根据角色ID获取用户关系列表 (Get by role ID)

### POST Endpoints
- `POST /create` - 创建用户角色关系 (Create single relation)
- `POST /batch-create` - 批量创建用户角色关系 (Create multiple relations)

### PUT Endpoints
- `PUT /update` - 更新用户角色关系 (Update relation)

### DELETE Endpoints
- `DELETE /{id}` - 删除用户角色关系 (Delete by ID)
- `DELETE /by-user-role` - 根据用户ID和角色ID删除关系 (Delete by user and role IDs)
  - Parameters: `userId`, `roleId`

## Security

All endpoints require:
- `ROLE_ROLE_USERMANAGER` authority at the controller level
- Specific permissions for each operation:
  - View operations: `role-permission:view` or `user-role:view`
  - Create operations: `role-permission:create` or `user-role:create`
  - Update operations: `role-permission:update` or `user-role:update`
  - Delete operations: `role-permission:delete` or `user-role:delete`

## Features

- **Pagination Support**: All list endpoints support pagination
- **Flexible Filtering**: Filter by role ID, permission ID, or user ID
- **Batch Operations**: Support for creating multiple relations at once
- **Consistent API Design**: Follows the same patterns as existing controllers
- **Full CRUD Operations**: Create, Read, Update, Delete functionality
- **Security Integration**: Proper authorization checks using Spring Security