package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RolePermissionRelationMapper;
import top.wei.oauth2.model.entity.RolePermissionRelation;
import top.wei.oauth2.service.RolePermissionRelationService;

import java.util.List;

/**
 * RolePermissionRelationServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class RolePermissionRelationServiceImpl implements RolePermissionRelationService {

    private final RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public Integer createRolePermissionRelation(RolePermissionRelation rolePermissionRelation) {
        return rolePermissionRelationMapper.insert(rolePermissionRelation);
    }

    @Override
    public RolePermissionRelation getRolePermissionRelationById(String id) {
        return rolePermissionRelationMapper.selectById(id);
    }

    @Override
    public List<RolePermissionRelation> getRolePermissionRelationsByRoleId(String roleId) {
        return rolePermissionRelationMapper.selectList(
                new QueryWrapper<RolePermissionRelation>().eq("role_id", roleId)
        );
    }

    @Override
    public List<RolePermissionRelation> getRolePermissionRelationsByPermissionId(String permissionId) {
        return rolePermissionRelationMapper.selectList(
                new QueryWrapper<RolePermissionRelation>().eq("permission_id", permissionId)
        );
    }

    @Override
    public PageInfo<RolePermissionRelation> selectRolePermissionRelations(Integer pageNum, Integer pageSize, String roleId, String permissionId) {
        PageHelper.startPage(pageNum, pageSize);
        
        QueryWrapper<RolePermissionRelation> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(roleId)) {
            queryWrapper.eq("role_id", roleId);
        }
        if (StringUtils.isNotBlank(permissionId)) {
            queryWrapper.eq("permission_id", permissionId);
        }
        
        List<RolePermissionRelation> relations = rolePermissionRelationMapper.selectList(queryWrapper);
        return new PageInfo<>(relations);
    }

    @Override
    public Integer updateRolePermissionRelation(RolePermissionRelation rolePermissionRelation) {
        return rolePermissionRelationMapper.updateById(rolePermissionRelation);
    }

    @Override
    public Integer deleteRolePermissionRelation(String id) {
        return rolePermissionRelationMapper.deleteById(id);
    }

    @Override
    public Integer deleteRolePermissionRelationByRoleIdAndPermissionId(String roleId, String permissionId) {
        return rolePermissionRelationMapper.delete(
                new QueryWrapper<RolePermissionRelation>()
                        .eq("role_id", roleId)
                        .eq("permission_id", permissionId)
        );
    }

    @Override
    public Integer batchCreateRolePermissionRelations(List<RolePermissionRelation> rolePermissionRelations) {
        int count = 0;
        for (RolePermissionRelation relation : rolePermissionRelations) {
            count += rolePermissionRelationMapper.insert(relation);
        }
        return count;
    }
}