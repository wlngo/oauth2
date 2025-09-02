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
import top.wei.oauth2.model.entity.Menu;
import top.wei.oauth2.service.MenuService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

/**
 * MenuController.
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 获取所有菜单.
     *
     * @param page    page
     * @param size    size
     * @param keyword keyword
     * @return menus
     */
    @PostMapping("/getAllMenus")
    @PreAuthorize("hasAuthority('menus:view')")
    public Rest<PageInfo<Menu>> getAllMenus(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String keyword) {
        return RestBody.okData(menuService.selectAllMenus(page, size, keyword));
    }

    /**
     * 根据ID获取菜单.
     *
     * @param id id
     * @return menu
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('menus:view')")
    public Rest<Menu> getMenuById(@PathVariable String id) {
        return RestBody.okData(menuService.getMenuById(id));
    }

    /**
     * 创建菜单.
     *
     * @param menu menu
     * @return rows
     */
    @PostMapping("/createMenu")
    @PreAuthorize("hasAuthority('menus:create')")
    public Rest<Integer> createMenu(@RequestBody Menu menu) {
        return RestBody.okData(menuService.createMenu(menu));
    }

    /**
     * 更新菜单.
     *
     * @param menu menu
     * @return rows
     */
    @PostMapping("/updateMenu")
    @PreAuthorize("hasAuthority('menus:update')")
    public Rest<Integer> updateMenu(@RequestBody Menu menu) {
        return RestBody.okData(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deleteMenu/{id}")
    @PreAuthorize("hasAuthority('menus:delete')")
    public Rest<Integer> deleteMenu(@PathVariable String id) {
        return RestBody.okData(menuService.deleteMenu(id));
    }
}