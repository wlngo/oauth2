package top.wei.oauth2.controller;

import com.github.pagehelper.PageInfo;
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
import top.wei.oauth2.model.entity.RoleMenuRelation;
import top.wei.oauth2.service.RoleMenuRelationService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

/**
 * RoleMenuRelationController.
 */
@RestController
@RequestMapping("/api/role-menu-relations")
@RequiredArgsConstructor
public class RoleMenuRelationController {

    private final RoleMenuRelationService roleMenuRelationService;

    /**
     * 获取所有角色菜单关系.
     *
     * @param page   page
     * @param size   size
     * @param roleId roleId
     * @return relations
     */
    @PostMapping("/getAllRoleMenuRelations")
    @PreAuthorize("hasAuthority('role-menu:view')")
    public Rest<PageInfo<RoleMenuRelation>> getAllRoleMenuRelations(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(required = false) String roleId) {
        return RestBody.okData(roleMenuRelationService.selectAllRoleMenuRelations(page, size, roleId));
    }

    /**
     * 根据ID获取角色菜单关系.
     *
     * @param id id
     * @return relation
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role-menu:view')")
    public Rest<RoleMenuRelation> getRoleMenuRelationById(@PathVariable String id) {
        return RestBody.okData(roleMenuRelationService.getRoleMenuRelationById(id));
    }

    /**
     * 创建角色菜单关系.
     *
     * @param roleMenuRelation roleMenuRelation
     * @return rows
     */
    @PostMapping("/createRoleMenuRelation")
    @PreAuthorize("hasAuthority('role-menu:create')")
    public Rest<Integer> createRoleMenuRelation(@RequestBody RoleMenuRelation roleMenuRelation) {
        return RestBody.okData(roleMenuRelationService.createRoleMenuRelation(roleMenuRelation));
    }

    /**
     * 更新角色菜单关系.
     *
     * @param roleMenuRelation roleMenuRelation
     * @return rows
     */
    @PostMapping("/updateRoleMenuRelation")
    @PreAuthorize("hasAuthority('role-menu:update')")
    public Rest<Integer> updateRoleMenuRelation(@RequestBody RoleMenuRelation roleMenuRelation) {
        return RestBody.okData(roleMenuRelationService.updateRoleMenuRelation(roleMenuRelation));
    }

    /**
     * 删除角色菜单关系.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deleteRoleMenuRelation/{id}")
    @PreAuthorize("hasAuthority('role-menu:delete')")
    public Rest<Integer> deleteRoleMenuRelation(@PathVariable String id) {
        return RestBody.okData(roleMenuRelationService.deleteRoleMenuRelation(id));
    }
}