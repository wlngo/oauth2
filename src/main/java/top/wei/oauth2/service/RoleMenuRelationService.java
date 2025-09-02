package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import top.wei.oauth2.model.entity.RoleMenuRelation;

/**
 * RoleMenuRelationService.
 */
public interface RoleMenuRelationService {

    /**
     * 创建角色菜单关系.
     *
     * @param roleMenuRelation roleMenuRelation
     * @return rows
     */
    Integer createRoleMenuRelation(RoleMenuRelation roleMenuRelation);

    /**
     * 根据ID获取角色菜单关系.
     *
     * @param id id
     * @return RoleMenuRelation
     */
    RoleMenuRelation getRoleMenuRelationById(String id);

    /**
     * 查询所有角色菜单关系.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param roleId   roleId
     * @return PageInfo RoleMenuRelation
     */
    PageInfo<RoleMenuRelation> selectAllRoleMenuRelations(Integer pageNum, Integer pageSize, String roleId);

    /**
     * 更新角色菜单关系.
     *
     * @param roleMenuRelation roleMenuRelation
     * @return rows
     */
    Integer updateRoleMenuRelation(RoleMenuRelation roleMenuRelation);

    /**
     * 删除角色菜单关系.
     *
     * @param id id
     * @return rows
     */
    Integer deleteRoleMenuRelation(String id);
}