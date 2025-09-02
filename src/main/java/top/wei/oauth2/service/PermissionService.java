package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import top.wei.oauth2.model.entity.Permission;

/**
 * PermissionService.
 */
public interface PermissionService {

    /**
     * 创建权限.
     *
     * @param permission permission
     * @return rows
     */
    Integer createPermission(Permission permission);

    /**
     * 根据ID获取权限.
     *
     * @param permissionId permissionId
     * @return Permission
     */
    Permission getPermissionById(Integer permissionId);

    /**
     * 查询所有权限信息.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param keyword  keyword
     * @return PageInfo Permission
     */
    PageInfo<Permission> selectAllPermissions(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新权限.
     *
     * @param permission permission
     * @return rows
     */
    Integer updatePermission(Permission permission);

    /**
     * 删除权限.
     *
     * @param permissionId permissionId
     * @return rows
     */
    Integer deletePermission(Integer permissionId);
}