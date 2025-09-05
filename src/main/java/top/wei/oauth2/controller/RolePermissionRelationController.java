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
import top.wei.oauth2.model.entity.Permission;
import top.wei.oauth2.model.entity.RolePermissionRelation;
import top.wei.oauth2.service.RolePermissionRelationService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.util.List;

/**
 * RolePermissionRelationController.
 */
@RestController
@RequestMapping("/api/role-permission-relations")
@RequiredArgsConstructor
public class RolePermissionRelationController {

    private final RolePermissionRelationService rolePermissionRelationService;


    /**
     * 根据角色ID获取权限关系列表.
     *
     * @param roleId 角色ID
     * @return 权限关系列表
     */
    @GetMapping("/queryPermissionByRoleId/{roleId}")
    @PreAuthorize("hasAuthority('role-permission:view')")
    public Rest<List<Permission>> queryPermissionByRoleId(@PathVariable String roleId) {
        return RestBody.okData(rolePermissionRelationService.queryPermissionByRoleId(roleId));
    }

    /**
     * 根据角色ID获取未拥有的权限列表.
     *
     * @param roleId 用户ID
     * @return 角色
     */
    @GetMapping("/queryPermissionNotAssignedToRoleId/{roleId}")
    @PreAuthorize("hasAuthority('user-permission:view')")
    public Rest<List<Permission>> queryPermissionNotAssignedToRoleId(@PathVariable String roleId) {
        return RestBody.okData(rolePermissionRelationService.queryPermissionNotAssignedToRoleId(roleId));
    }



    /**
     * 创建角色权限关系.
     *
     * @param rolePermissionRelation 角色权限关系
     * @return 影响行数
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('role-permission:create')")
    public Rest<Integer> createRolePermissionRelation(@RequestBody RolePermissionRelation rolePermissionRelation) {
        return RestBody.okData(rolePermissionRelationService.createRolePermissionRelation(rolePermissionRelation));
    }

    /**
     * 批量创建角色权限关系.
     *
     * @param rolePermissionRelations 角色权限关系列表
     * @return 影响行数
     */
    @PostMapping("/batch-create")
    @PreAuthorize("hasAuthority('role-permission:create')")
    public Rest<Integer> batchCreateRolePermissionRelations(@RequestBody List<RolePermissionRelation> rolePermissionRelations) {
        return RestBody.okData(rolePermissionRelationService.batchCreateRolePermissionRelations(rolePermissionRelations));
    }


    /**
     * 删除角色权限关系.
     *
     * @param roleId 角色id
     * @return 影响行数
     */
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('role-permission:delete')")
    public Rest<Integer> deleteRolePermissionRelation(@PathVariable String roleId) {
        return RestBody.okData(rolePermissionRelationService.deleteRolePermissionRelation(roleId));
    }

    /**
     * 根据角色ID和权限ID删除关系.
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 影响行数
     */
    @DeleteMapping("/by-role-permission")
    @PreAuthorize("hasAuthority('role-permission:delete')")
    public Rest<Integer> deleteRolePermissionRelationByRoleIdAndPermissionId(
            @RequestParam String roleId,
            @RequestParam String permissionId) {
        return RestBody.okData(rolePermissionRelationService.deleteRolePermissionRelationByRoleIdAndPermissionId(roleId, permissionId));
    }
}