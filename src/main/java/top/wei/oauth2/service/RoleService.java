package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import top.wei.oauth2.model.entity.Role;

/**
 * RoleService.
 */
public interface RoleService {

    /**
     * 创建角色.
     *
     * @param role role
     * @return rows
     */
    Integer createRole(Role role);

    /**
     * 根据ID获取角色.
     *
     * @param roleId roleId
     * @return Role
     */
    Role getRoleById(String roleId);

    /**
     * 查询所有角色信息.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param keyword  keyword
     * @return PageInfo Role
     */
    PageInfo<Role> selectAllRoles(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新角色.
     *
     * @param role role
     * @return rows
     */
    Integer updateRole(Role role);

    /**
     * 删除角色.
     *
     * @param roleId roleId
     * @return rows
     */
    Integer deleteRole(String roleId);
}