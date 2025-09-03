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
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.service.RoleService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

/**
 * RoleController.
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 获取所有角色.
     *
     * @param page    page
     * @param size    size
     * @param keyword keyword
     * @return roles
     */
    @PostMapping("/getAllRoles")
    @PreAuthorize("hasAuthority('role:view')")
    public Rest<PageInfo<Role>> getAllRoles(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String keyword) {
        return RestBody.okData(roleService.selectAllRoles(page, size, keyword));
    }

    /**
     * 根据ID获取角色.
     *
     * @param id id
     * @return role
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role:view')")
    public Rest<Role> getRoleById(@PathVariable String id) {
        return RestBody.okData(roleService.getRoleById(id));
    }

    /**
     * 创建角色.
     *
     * @param role role
     * @return rows
     */
    @PostMapping("/createRole")
    @PreAuthorize("hasAuthority('role:create')")
    public Rest<Integer> createRole(@RequestBody Role role) {
        return RestBody.okData(roleService.createRole(role));
    }

    /**
     * 更新角色.
     *
     * @param role role
     * @return rows
     */
    @PostMapping("/updateRole")
    @PreAuthorize("hasAuthority('role:update')")
    public Rest<Integer> updateRole(@RequestBody Role role) {
        return RestBody.okData(roleService.updateRole(role));
    }

    /**
     * 删除角色.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deleteRole/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    public Rest<Integer> deleteRole(@PathVariable String id) {
        return RestBody.okData(roleService.deleteRole(id));
    }
}