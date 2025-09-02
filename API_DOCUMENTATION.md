# New CRUD APIs Documentation

This document describes the newly implemented CRUD APIs for Menu, Role, Permission, and RoleMenuRelation entities.

## Menu APIs

### Base URL: `/api/menus`

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| POST | `/getAllMenus` | Get paginated list of menus | `page` (default: 1), `size` (default: 10), `keyword` (optional) |
| GET | `/{id}` | Get menu by ID | `id` (path parameter) |
| POST | `/createMenu` | Create new menu | Menu object in request body |
| POST | `/updateMenu` | Update existing menu | Menu object in request body |
| DELETE | `/deleteMenu/{id}` | Soft delete menu | `id` (path parameter) |

### Menu Entity Fields
- `menuId`: Menu unique identifier
- `menuName`: Menu display name
- `menuPath`: URL path for the menu
- `menuIcon`: Icon identifier for the menu
- `parentId`: Parent menu ID (for hierarchical structure)
- `sortOrder`: Display order
- `menuType`: Type (0=directory, 1=menu, 2=button)
- `visible`: Visibility status (true/false)
- `description`: Menu description
- Standard audit fields: `createdAt`, `createId`, `updatedAt`, `updateId`, `deleted`

## Role APIs

### Base URL: `/api/roles`

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| POST | `/getAllRoles` | Get paginated list of roles | `page` (default: 1), `size` (default: 10), `keyword` (optional) |
| GET | `/{id}` | Get role by ID | `id` (path parameter) |
| POST | `/createRole` | Create new role | Role object in request body |
| POST | `/updateRole` | Update existing role | Role object in request body |
| DELETE | `/deleteRole/{id}` | Soft delete role | `id` (path parameter) |

### Role Entity Fields
- `roleId`: Role unique identifier
- `roleName`: Role code/name
- `roleContent`: Role display name
- `description`: Role description
- Standard audit fields: `createdAt`, `createId`, `updatedAt`, `updateId`, `deleted`

## Permission APIs

### Base URL: `/api/permissions`

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| POST | `/getAllPermissions` | Get paginated list of permissions | `page` (default: 1), `size` (default: 10), `keyword` (optional) |
| GET | `/{id}` | Get permission by ID | `id` (path parameter) |
| POST | `/createPermission` | Create new permission | Permission object in request body |
| POST | `/updatePermission` | Update existing permission | Permission object in request body |
| DELETE | `/deletePermission/{id}` | Soft delete permission | `id` (path parameter) |

### Permission Entity Fields
- `permissionId`: Permission unique identifier (auto-increment)
- `permissionCode`: Permission code
- `permissionName`: Permission display name
- Standard audit fields: `createAt`, `createId`, `updateAt`, `updateId`, `deleted`

## Role-Menu Relation APIs

### Base URL: `/api/role-menu-relations`

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| POST | `/getAllRoleMenuRelations` | Get paginated list of role-menu relations | `page` (default: 1), `size` (default: 10), `roleId` (optional filter) |
| GET | `/{id}` | Get relation by ID | `id` (path parameter) |
| POST | `/createRoleMenuRelation` | Create new relation | RoleMenuRelation object in request body |
| POST | `/updateRoleMenuRelation` | Update existing relation | RoleMenuRelation object in request body |
| DELETE | `/deleteRoleMenuRelation/{id}` | Delete relation | `id` (path parameter) |

### RoleMenuRelation Entity Fields
- `id`: Relation unique identifier
- `roleId`: Reference to Role entity
- `menuId`: Reference to Menu entity

## Response Format

All APIs return responses in the following format:

```json
{
  "code": 200,
  "data": <response_data>,
  "msg": "",
  "identifier": true
}
```

## Features

1. **Pagination**: All list endpoints support pagination with configurable page size
2. **Search**: List endpoints support keyword search across relevant fields
3. **Soft Delete**: Menu, Role, and Permission entities support soft delete (marked as deleted rather than physically removed)
4. **Audit Trail**: All entities include creation and modification timestamps and user IDs
5. **Hierarchical Menus**: Menu entity supports parent-child relationships for building menu trees

## Database Tables

The implementation expects the following database tables:
- `t_menu`: Menu storage
- `t_role`: Role storage (already exists)
- `t_permission`: Permission storage (already exists)
- `t_role_menu_relation`: Many-to-many relationship between roles and menus
- `t_role_permission_relation`: Many-to-many relationship between roles and permissions (already exists)

## Usage Examples

### Create a Menu
```bash
POST /api/menus/createMenu
{
  "menuId": "menu_001",
  "menuName": "User Management",
  "menuPath": "/users",
  "menuIcon": "user",
  "parentId": null,
  "sortOrder": 1,
  "menuType": 1,
  "visible": true,
  "description": "User management module"
}
```

### Assign Menu to Role
```bash
POST /api/role-menu-relations/createRoleMenuRelation
{
  "roleId": "role_001",
  "menuId": "menu_001"
}
```

### Search Roles
```bash
POST /api/roles/getAllRoles?page=1&size=10&keyword=admin
```