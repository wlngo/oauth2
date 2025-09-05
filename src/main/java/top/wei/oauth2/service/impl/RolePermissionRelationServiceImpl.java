package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RolePermissionRelationMapper;
import top.wei.oauth2.model.entity.Permission;
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
    public List<Permission> queryPermissionByRoleId(String roleId) {
        return rolePermissionRelationMapper.queryPermissionByRoleId(roleId);
    }

    @Override
    public List<Permission> queryPermissionNotAssignedToRoleId(String roleId) {
        return rolePermissionRelationMapper.queryPermissionNotAssignedToRoleId(roleId);
    }

    @Override
    public Integer deleteRolePermissionRelation(String roleId) {
        return rolePermissionRelationMapper.delete(
                new QueryWrapper<RolePermissionRelation>()
                        .eq("role_id", roleId)
        );
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