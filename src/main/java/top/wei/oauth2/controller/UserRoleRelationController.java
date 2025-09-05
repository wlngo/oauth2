package top.wei.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.model.entity.UserRoleRelation;
import top.wei.oauth2.service.UserRoleRelationService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.util.List;

/**
 * UserRoleRelationController.
 */
@RestController
@RequestMapping("/api/user-role-relations")
@RequiredArgsConstructor
public class UserRoleRelationController {

    private final UserRoleRelationService userRoleRelationService;


    /**
     * 根据用户ID获取用户拥有的角色列表.
     *
     * @param userId 用户ID
     * @return 角色
     */
    @GetMapping("/queryRolesByUserId/{userId}")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<List<Role>> queryRolesByUserId(@PathVariable String userId) {
        return RestBody.okData(userRoleRelationService.queryRolesByUserId(userId));
    }

    /**
     * 根据用户ID获取用户未拥有的角色列表.
     *
     * @param userId 用户ID
     * @return 角色
     */
    @GetMapping("/queryRolesNotAssignedToUser/{userId}")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<List<Role>> queryRolesNotAssignedToUser(@PathVariable String userId) {
        return RestBody.okData(userRoleRelationService.queryRolesNotAssignedToUser(userId));
    }


    /**
     * 创建用户角色关系.
     *
     * @param userRoleRelation 用户角色关系
     * @return 影响行数
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user-role:create')")
    public Rest<Integer> createUserRoleRelation(@RequestBody UserRoleRelation userRoleRelation) {
        return RestBody.okData(userRoleRelationService.createUserRoleRelation(userRoleRelation));
    }

    /**
     * 批量创建用户角色关系.
     *
     * @param userRoleRelations 用户角色关系列表
     * @return 影响行数
     */
    @PostMapping("/batch-create")
    @PreAuthorize("hasAuthority('user-role:create')")
    public Rest<Integer> batchCreateUserRoleRelations(@RequestBody List<UserRoleRelation> userRoleRelations) {
        return RestBody.okData(userRoleRelationService.batchCreateUserRoleRelations(userRoleRelations));
    }


    /**
     * 删除用户角色关系.
     *
     * @param userId 用户id
     * @return 影响行数
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('user-role:delete')")
    public Rest<Integer> deleteUserRoleRelation(@PathVariable String userId) {
        return RestBody.okData(userRoleRelationService.deleteUserRoleRelation(userId));
    }

    /**
     * 根据用户ID和角色ID删除关系.
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    @DeleteMapping("/by-user-role")
    @PreAuthorize("hasAuthority('user-role:delete')")
    public Rest<Integer> deleteUserRoleRelationByUserIdAndRoleId(
            @RequestParam String userId,
            @RequestParam String roleId) {
        return RestBody.okData(userRoleRelationService.deleteUserRoleRelationByUserIdAndRoleId(userId, roleId));
    }
}