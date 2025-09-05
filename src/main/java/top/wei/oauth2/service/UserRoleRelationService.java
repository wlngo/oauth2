package top.wei.oauth2.service;

import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.model.entity.UserRoleRelation;

import java.util.List;

/**
 * UserRoleRelationService.
 */
public interface UserRoleRelationService {

    /**
     * 创建用户角色关系.
     *
     * @param userRoleRelation 用户角色关系
     * @return 影响行数
     */
    Integer createUserRoleRelation(UserRoleRelation userRoleRelation);


    /**
     * 根据用户ID查询角色列表.
     *
     * @param userId userId
     * @return Role
     */
    List<Role> queryRolesByUserId(@Param("userId") String userId);


    /**
     * 查询未分配给指定用户的角色列表.
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> queryRolesNotAssignedToUser(@Param("userId") String userId);

    /**
     * 删除用户角色关系.
     *
     * @param userId 用户id
     * @return 影响行数
     */
    Integer deleteUserRoleRelation(String userId);

    /**
     * 根据用户ID和角色ID删除关系.
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    Integer deleteUserRoleRelationByUserIdAndRoleId(String userId, String roleId);

    /**
     * 批量创建用户角色关系.
     *
     * @param userRoleRelations 用户角色关系列表
     * @return 影响行数
     */
    Integer batchCreateUserRoleRelations(List<UserRoleRelation> userRoleRelations);
}