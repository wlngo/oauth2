package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
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
     * 根据ID获取用户角色关系.
     *
     * @param id 主键ID
     * @return UserRoleRelation
     */
    UserRoleRelation getUserRoleRelationById(String id);

    /**
     * 根据用户ID获取角色列表.
     *
     * @param userId 用户ID
     * @return 用户角色关系列表
     */
    List<UserRoleRelation> getUserRoleRelationsByUserId(String userId);

    /**
     * 根据角色ID获取用户列表.
     *
     * @param roleId 角色ID
     * @return 用户角色关系列表
     */
    List<UserRoleRelation> getUserRoleRelationsByRoleId(String roleId);

    /**
     * 分页查询用户角色关系.
     *
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param userId   用户ID（可选）
     * @param roleId   角色ID（可选）
     * @return 分页结果
     */
    PageInfo<UserRoleRelation> selectUserRoleRelations(Integer pageNum, Integer pageSize, String userId, String roleId);

    /**
     * 更新用户角色关系.
     *
     * @param userRoleRelation 用户角色关系
     * @return 影响行数
     */
    Integer updateUserRoleRelation(UserRoleRelation userRoleRelation);

    /**
     * 删除用户角色关系.
     *
     * @param id 主键ID
     * @return 影响行数
     */
    Integer deleteUserRoleRelation(String id);

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