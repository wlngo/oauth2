package top.wei.oauth2.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@PreAuthorize("hasAuthority('ROLE_ROLE_USERMANAGER')")
public class UserRoleRelationController {

    private final UserRoleRelationService userRoleRelationService;

    /**
     * 分页查询用户角色关系.
     *
     * @param page   页码
     * @param size   页大小
     * @param userId 用户ID（可选）
     * @param roleId 角色ID（可选）
     * @return 分页结果
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<PageInfo<UserRoleRelation>> getAllUserRoleRelations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String roleId) {
        return RestBody.okData(userRoleRelationService.selectUserRoleRelations(page, size, userId, roleId));
    }

    /**
     * 根据ID获取用户角色关系.
     *
     * @param id 主键ID
     * @return UserRoleRelation
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<UserRoleRelation> getUserRoleRelationById(@PathVariable String id) {
        return RestBody.okData(userRoleRelationService.getUserRoleRelationById(id));
    }

    /**
     * 根据用户ID获取角色关系列表.
     *
     * @param userId 用户ID
     * @return 角色关系列表
     */
    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<List<UserRoleRelation>> getUserRoleRelationsByUserId(@PathVariable String userId) {
        return RestBody.okData(userRoleRelationService.getUserRoleRelationsByUserId(userId));
    }

    /**
     * 根据角色ID获取用户关系列表.
     *
     * @param roleId 角色ID
     * @return 用户关系列表
     */
    @GetMapping("/by-role/{roleId}")
    @PreAuthorize("hasAuthority('user-role:view')")
    public Rest<List<UserRoleRelation>> getUserRoleRelationsByRoleId(@PathVariable String roleId) {
        return RestBody.okData(userRoleRelationService.getUserRoleRelationsByRoleId(roleId));
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
     * 更新用户角色关系.
     *
     * @param userRoleRelation 用户角色关系
     * @return 影响行数
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user-role:update')")
    public Rest<Integer> updateUserRoleRelation(@RequestBody UserRoleRelation userRoleRelation) {
        return RestBody.okData(userRoleRelationService.updateUserRoleRelation(userRoleRelation));
    }

    /**
     * 删除用户角色关系.
     *
     * @param id 主键ID
     * @return 影响行数
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user-role:delete')")
    public Rest<Integer> deleteUserRoleRelation(@PathVariable String id) {
        return RestBody.okData(userRoleRelationService.deleteUserRoleRelation(id));
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