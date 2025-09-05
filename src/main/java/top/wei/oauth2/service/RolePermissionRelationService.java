package top.wei.oauth2.service;

import top.wei.oauth2.model.entity.Permission;
import top.wei.oauth2.model.entity.RolePermissionRelation;

import java.util.List;

/**
 * RolePermissionRelationService.
 */
public interface RolePermissionRelationService {

    /**
     * 创建角色权限关系.
     *
     * @param rolePermissionRelation 角色权限关系
     * @return 影响行数
     */
    Integer createRolePermissionRelation(RolePermissionRelation rolePermissionRelation);


    /**
     * 根据角色ID获取权限列表.
     *
     * @param roleId 角色ID
     * @return 权限关系列表
     */
    List<Permission> queryPermissionByRoleId(String roleId);

    /**
     * 根据角色ID获取权限列表.
     *
     * @param roleId 角色ID
     * @return 权限关系列表
     */
    List<Permission> queryPermissionNotAssignedToRoleId(String roleId);


    /**
     * 删除角色权限关系.
     *
     * @param roleId 角色id
     * @return 影响行数
     */
    Integer deleteRolePermissionRelation(String roleId);

    /**
     * 根据角色ID和权限ID删除关系.
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 影响行数
     */
    Integer deleteRolePermissionRelationByRoleIdAndPermissionId(String roleId, String permissionId);

    /**
     * 批量创建角色权限关系.
     *
     * @param rolePermissionRelations 角色权限关系列表
     * @return 影响行数
     */
    Integer batchCreateRolePermissionRelations(List<RolePermissionRelation> rolePermissionRelations);
}