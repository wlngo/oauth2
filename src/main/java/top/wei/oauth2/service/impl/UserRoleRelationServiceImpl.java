package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.UserRoleRelationMapper;
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
    public UserRoleRelation getUserRoleRelationById(String id) {
        return userRoleRelationMapper.selectById(id);
    }

    @Override
    public List<UserRoleRelation> getUserRoleRelationsByUserId(String userId) {
        return userRoleRelationMapper.selectList(
                new QueryWrapper<UserRoleRelation>().eq("user_id", userId)
        );
    }

    @Override
    public List<UserRoleRelation> getUserRoleRelationsByRoleId(String roleId) {
        return userRoleRelationMapper.selectList(
                new QueryWrapper<UserRoleRelation>().eq("role_id", roleId)
        );
    }

    @Override
    public PageInfo<UserRoleRelation> selectUserRoleRelations(Integer pageNum, Integer pageSize, String userId, String roleId) {
        PageHelper.startPage(pageNum, pageSize);
        
        QueryWrapper<UserRoleRelation> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.eq("user_id", userId);
        }
        if (StringUtils.isNotBlank(roleId)) {
            queryWrapper.eq("role_id", roleId);
        }
        
        List<UserRoleRelation> relations = userRoleRelationMapper.selectList(queryWrapper);
        return new PageInfo<>(relations);
    }

    @Override
    public Integer updateUserRoleRelation(UserRoleRelation userRoleRelation) {
        return userRoleRelationMapper.updateById(userRoleRelation);
    }

    @Override
    public Integer deleteUserRoleRelation(String id) {
        return userRoleRelationMapper.deleteById(id);
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