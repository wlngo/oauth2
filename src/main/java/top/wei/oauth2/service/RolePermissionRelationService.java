package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
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
     * 根据ID获取角色权限关系.
     *
     * @param id 主键ID
     * @return RolePermissionRelation
     */
    RolePermissionRelation getRolePermissionRelationById(String id);

    /**
     * 根据角色ID获取权限列表.
     *
     * @param roleId 角色ID
     * @return 权限关系列表
     */
    List<RolePermissionRelation> getRolePermissionRelationsByRoleId(String roleId);

    /**
     * 根据权限ID获取角色列表.
     *
     * @param permissionId 权限ID
     * @return 角色关系列表
     */
    List<RolePermissionRelation> getRolePermissionRelationsByPermissionId(String permissionId);

    /**
     * 分页查询角色权限关系.
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param roleId   角色ID（可选）
     * @param permissionId 权限ID（可选）
     * @return 分页结果
     */
    PageInfo<RolePermissionRelation> selectRolePermissionRelations(Integer pageNum, Integer pageSize, String roleId, String permissionId);

    /**
     * 更新角色权限关系.
     *
     * @param rolePermissionRelation 角色权限关系
     * @return 影响行数
     */
    Integer updateRolePermissionRelation(RolePermissionRelation rolePermissionRelation);

    /**
     * 删除角色权限关系.
     *
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteRolePermissionRelation(String id);

    /**
     * 根据角色ID和权限ID删除关系.
     *
     * @param roleId 角色ID
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