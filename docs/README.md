# OAuth2 系统文档

## 文档结构

本目录包含 OAuth2 系统的相关文档。

### API 文档

- **[Controller API 文档](api/controllers-documentation.md)** - 详细描述了 `top.wei.oauth2.controller` 包下所有控制器的 API 接口

### 文档内容概览

#### Controllers API 文档
包含了以下控制器的完整 API 文档：

1. **UserController** - 用户管理相关 API
2. **AuthController** - 身份认证相关 API  
3. **RoleController** - 角色管理相关 API
4. **PermissionController** - 权限管理相关 API
5. **UserRoleRelationController** - 用户角色关系管理 API
6. **RolePermissionRelationController** - 角色权限关系管理 API
7. **CsrfController** - CSRF 令牌 API
8. **CaptchaController** - 验证码服务 API
9. **AuthorizationConsentController** - OAuth2 授权同意 API

每个控制器的文档都包含：
- 控制器描述和基础路径
- 详细的接口列表
- 请求参数说明
- 响应格式说明
- 权限要求
- 请求示例

### 如何使用

1. 查看具体 API 接口时，请参考对应的控制器文档
2. 所有接口都遵循统一的响应格式
3. 注意每个接口的权限要求
4. 参考示例代码进行 API 调用

### 更新日志

- 2024-12-19: 创建初始版本的 Controller API 文档