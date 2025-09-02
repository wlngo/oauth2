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
import top.wei.oauth2.model.entity.Permission;
import top.wei.oauth2.service.PermissionService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

/**
 * PermissionController.
 */
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 获取所有权限.
     *
     * @param page    page
     * @param size    size
     * @param keyword keyword
     * @return permissions
     */
    @PostMapping("/getAllPermissions")
    @PreAuthorize("hasAuthority('permission:view')")
    public Rest<PageInfo<Permission>> getAllPermissions(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) String keyword) {
        return RestBody.okData(permissionService.selectAllPermissions(page, size, keyword));
    }

    /**
     * 根据ID获取权限.
     *
     * @param id id
     * @return permission
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:view')")
    public Rest<Permission> getPermissionById(@PathVariable Integer id) {
        return RestBody.okData(permissionService.getPermissionById(id));
    }

    /**
     * 创建权限.
     *
     * @param permission permission
     * @return rows
     */
    @PostMapping("/createPermission")
    @PreAuthorize("hasAuthority('permission:create')")
    public Rest<Integer> createPermission(@RequestBody Permission permission) {
        return RestBody.okData(permissionService.createPermission(permission));
    }

    /**
     * 更新权限.
     *
     * @param permission permission
     * @return rows
     */
    @PostMapping("/updatePermission")
    @PreAuthorize("hasAuthority('permission:update')")
    public Rest<Integer> updatePermission(@RequestBody Permission permission) {
        return RestBody.okData(permissionService.updatePermission(permission));
    }

    /**
     * 删除权限.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deletePermission/{id}")
    @PreAuthorize("hasAuthority('permission:delete')")
    public Rest<Integer> deletePermission(@PathVariable Integer id) {
        return RestBody.okData(permissionService.deletePermission(id));
    }
}