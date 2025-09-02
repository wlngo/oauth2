package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import top.wei.oauth2.model.entity.Menu;

import java.util.TreeMap;

/**
 * MenuService.
 */
public interface MenuService {

    /**
     * 创建菜单.
     *
     * @param menu menu
     * @return rows
     */
    Integer createMenu(Menu menu);

    /**
     * 根据ID获取菜单.
     *
     * @param menuId menuId
     * @return Menu
     */
    Menu getMenuById(String menuId);

    /**
     * 查询所有菜单信息.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param keyword  keyword
     * @return PageInfo Menu
     */
    PageInfo<Menu> selectAllMenus(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新菜单.
     *
     * @param menu menu
     * @return rows
     */
    Integer updateMenu(Menu menu);

    /**
     * 删除菜单.
     *
     * @param menuId menuId
     * @return rows
     */
    Integer deleteMenu(String menuId);


    /**
     * 获取所有菜单树形结构.
     *
     * @param userId userId
     * @return menu tree
     */
    TreeMap<Long, Menu> getAllMenuTree(String userId);
}