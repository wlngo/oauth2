package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RoleMenuRelationMapper;
import top.wei.oauth2.model.entity.RoleMenuRelation;
import top.wei.oauth2.service.RoleMenuRelationService;

import java.util.List;

/**
 * RoleMenuRelationServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class RoleMenuRelationServiceImpl implements RoleMenuRelationService {

    private final RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public Integer createRoleMenuRelation(RoleMenuRelation roleMenuRelation) {
        return roleMenuRelationMapper.insert(roleMenuRelation);
    }

    @Override
    public RoleMenuRelation getRoleMenuRelationById(String id) {
        return roleMenuRelationMapper.selectById(id);
    }

    @Override
    public PageInfo<RoleMenuRelation> selectAllRoleMenuRelations(Integer pageNum, Integer pageSize, String roleId) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<RoleMenuRelation> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(roleId)) {
            queryWrapper.eq("role_id", roleId);
        }
        List<RoleMenuRelation> relations = roleMenuRelationMapper.selectList(queryWrapper);
        return new PageInfo<>(relations);
    }

    @Override
    public Integer updateRoleMenuRelation(RoleMenuRelation roleMenuRelation) {
        return roleMenuRelationMapper.updateById(roleMenuRelation);
    }

    @Override
    public Integer deleteRoleMenuRelation(String id) {
        return roleMenuRelationMapper.deleteById(id);
    }
}