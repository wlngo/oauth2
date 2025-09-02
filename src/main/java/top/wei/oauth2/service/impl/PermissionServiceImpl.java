package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.PermissionMapper;
import top.wei.oauth2.model.entity.Permission;
import top.wei.oauth2.service.PermissionService;

import java.util.Date;
import java.util.List;

/**
 * PermissionServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public Integer createPermission(Permission permission) {
        permission.setCreateAt(new Date());
        permission.setDeleted(false);
        return permissionMapper.insert(permission);
    }

    @Override
    public Permission getPermissionById(Integer permissionId) {
        return permissionMapper.selectById(permissionId);
    }

    @Override
    public PageInfo<Permission> selectAllPermissions(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", false);
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper.like("permission_code", keyword)
                    .or().like("permission_name", keyword));
        }
        queryWrapper.orderByDesc("create_at");
        List<Permission> permissions = permissionMapper.selectList(queryWrapper);
        return new PageInfo<>(permissions);
    }

    @Override
    public Integer updatePermission(Permission permission) {
        permission.setUpdateAt(new Date());
        return permissionMapper.updateById(permission);
    }

    @Override
    public Integer deletePermission(Integer permissionId) {
        UpdateWrapper<Permission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("permission_id", permissionId);
        updateWrapper.set("deleted", true);
        updateWrapper.set("update_at", new Date());
        return permissionMapper.update(null, updateWrapper);
    }
}