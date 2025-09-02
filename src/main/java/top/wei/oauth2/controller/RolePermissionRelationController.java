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
@PreAuthorize("hasAuthority('ROLE_ROLE_USERMANAGER')")
public class RolePermissionRelationController {

    private final RolePermissionRelationService rolePermissionRelationService;

    /**
     * 分页查询角色权限关系.
     *
     * @param page         页码
     * @param size         页大小
     * @param roleId       角色ID（可选）
     * @param permissionId 权限ID（可选）
     * @return 分页结果
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('role-permission:view')")
    public Rest<PageInfo<RolePermissionRelation>> getAllRolePermissionRelations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String permissionId) {
        return RestBody.okData(rolePermissionRelationService.selectRolePermissionRelations(page, size, roleId, permissionId));
    }

    /**
     * 根据ID获取角色权限关系.
     *
     * @param id 主键ID
     * @return RolePermissionRelation
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role-permission:view')")
    public Rest<RolePermissionRelation> getRolePermissionRelationById(@PathVariable String id) {
        return RestBody.okData(rolePermissionRelationService.getRolePermissionRelationById(id));
    }

    /**
     * 根据角色ID获取权限关系列表.
     *
     * @param roleId 角色ID
     * @return 权限关系列表
     */
    @GetMapping("/by-role/{roleId}")
    @PreAuthorize("hasAuthority('role-permission:view')")
    public Rest<List<RolePermissionRelation>> getRolePermissionRelationsByRoleId(@PathVariable String roleId) {
        return RestBody.okData(rolePermissionRelationService.getRolePermissionRelationsByRoleId(roleId));
    }

    /**
     * 根据权限ID获取角色关系列表.
     *
     * @param permissionId 权限ID
     * @return 角色关系列表
     */
    @GetMapping("/by-permission/{permissionId}")
    @PreAuthorize("hasAuthority('role-permission:view')")
    public Rest<List<RolePermissionRelation>> getRolePermissionRelationsByPermissionId(@PathVariable String permissionId) {
        return RestBody.okData(rolePermissionRelationService.getRolePermissionRelationsByPermissionId(permissionId));
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
     * 更新角色权限关系.
     *
     * @param rolePermissionRelation 角色权限关系
     * @return 影响行数
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('role-permission:update')")
    public Rest<Integer> updateRolePermissionRelation(@RequestBody RolePermissionRelation rolePermissionRelation) {
        return RestBody.okData(rolePermissionRelationService.updateRolePermissionRelation(rolePermissionRelation));
    }

    /**
     * 删除角色权限关系.
     *
     * @param id 主键ID
     * @return 影响行数
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role-permission:delete')")
    public Rest<Integer> deleteRolePermissionRelation(@PathVariable String id) {
        return RestBody.okData(rolePermissionRelationService.deleteRolePermissionRelation(id));
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