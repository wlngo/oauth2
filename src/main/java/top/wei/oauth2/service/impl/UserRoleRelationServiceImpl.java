package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.UserRoleRelationMapper;
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.model.entity.UserRoleRelation;
import top.wei.oauth2.service.UserRoleRelationService;

import java.util.List;

/**
 * UserRoleRelationServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class UserRoleRelationServiceImpl implements UserRoleRelationService {

    private final UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public Integer createUserRoleRelation(UserRoleRelation userRoleRelation) {
        return userRoleRelationMapper.insert(userRoleRelation);
    }

    @Override
    public List<Role> queryRolesByUserId(String userId) {
        return userRoleRelationMapper.queryRolesByUserId(userId);
    }

    @Override
    public List<Role> queryRolesNotAssignedToUser(String userId) {
        return userRoleRelationMapper.queryRolesNotAssignedToUser(userId);
    }

    @Override
    public Integer deleteUserRoleRelation(String userId) {
        return userRoleRelationMapper.delete(new QueryWrapper<UserRoleRelation>()
                .eq("user_id", userId)
        );
    }

    @Override
    public Integer deleteUserRoleRelationByUserIdAndRoleId(String userId, String roleId) {
        return userRoleRelationMapper.delete(
                new QueryWrapper<UserRoleRelation>()
                        .eq("user_id", userId)
                        .eq("role_id", roleId)
        );
    }

    @Override
    public Integer batchCreateUserRoleRelations(List<UserRoleRelation> userRoleRelations) {
        int count = 0;
        for (UserRoleRelation relation : userRoleRelations) {
            count += userRoleRelationMapper.insert(relation);
        }
        return count;
    }
}