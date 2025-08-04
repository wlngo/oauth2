package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RoleMapper;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.service.UserService;

import java.util.List;

/**
 * UserServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    @Override
    public UserLoginDto loadUserByUsername(String username) {
        return userMapper.selectUserRoleByUserName(username);
    }

    @Override
    public void createUser(User user) {
        // 检查用户名是否已存在
        userMapper.insert(user);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public List<PermissionDto> getPermissionByRoleNames(List<String> roleNames) {
        return roleMapper.selectPermissionByRoleNames(roleNames);
    }


}
