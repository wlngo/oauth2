package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.MenuMapper;
import top.wei.oauth2.model.entity.Menu;
import top.wei.oauth2.service.MenuService;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * MenuServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public Integer createMenu(Menu menu) {
        menu.setCreatedAt(new Date());
        menu.setDeleted(false);
        return menuMapper.insert(menu);
    }

    @Override
    public Menu getMenuById(String menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public PageInfo<Menu> selectAllMenus(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", false);
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper.like("menu_name", keyword)
                    .or().like("menu_path", keyword)
                    .or().like("description", keyword));
        }
        queryWrapper.orderByDesc("created_at");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return new PageInfo<>(menus);
    }

    @Override
    public Integer updateMenu(Menu menu) {
        menu.setUpdatedAt(new Date());
        return menuMapper.updateById(menu);
    }

    @Override
    public Integer deleteMenu(String menuId) {
        UpdateWrapper<Menu> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("menu_id", menuId);
        updateWrapper.set("deleted", true);
        updateWrapper.set("updated_at", new Date());
        return menuMapper.update(null, updateWrapper);
    }

    @Override
    public TreeMap<Long, Menu> getAllMenuTree(String userId) {
        List<Menu> menus = menuMapper.searchAllByUserId(userId);
        // 按 parentId 分组
        TreeMap<Long, Menu> treeMap = new TreeMap<>();
        for (Menu menu : menus) {
            treeMap.put(menu.getParentId() != null ? Long.parseLong(menu.getParentId()) : 0L, menu);
        }
        // 构建树
        return treeMap;
    }

}