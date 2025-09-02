package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RoleMapper;
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.service.RoleService;

import java.util.Date;
import java.util.List;

/**
 * RoleServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    public Integer createRole(Role role) {
        role.setCreatedAt(new Date());
        role.setDeleted(false);
        return roleMapper.insert(role);
    }

    @Override
    public Role getRoleById(String roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public PageInfo<Role> selectAllRoles(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", false);
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper.like("role_name", keyword)
                    .or().like("role_content", keyword)
                    .or().like("description", keyword));
        }
        queryWrapper.orderByDesc("created_at");
        List<Role> roles = roleMapper.selectList(queryWrapper);
        return new PageInfo<>(roles);
    }

    @Override
    public Integer updateRole(Role role) {
        role.setUpdatedAt(new Date());
        return roleMapper.updateById(role);
    }

    @Override
    public Integer deleteRole(String roleId) {
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        updateWrapper.set("deleted", true);
        updateWrapper.set("updated_at", new Date());
        return roleMapper.update(null, updateWrapper);
    }
}