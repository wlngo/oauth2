package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.PermissionMapper;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.model.vo.UserInfoVo;
import top.wei.oauth2.service.UserService;

import java.util.List;

/**
 * UserServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PermissionMapper permissionMapper;

    @Override
    public UserLoginDto loadUserByUsername(String username) {
        return userMapper.selectUserRoleByUserName(username);
    }

    @Override
    public Integer createUser(User user) {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(delegatingPasswordEncoder.encode(user.getPassword()));
        }
        return userMapper.insert(user);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("deleted", 0));
    }

    @Override
    public List<PermissionDto> getPermissionByUserid(String userId) {
        return permissionMapper.selectPermissionByUserid(userId);
    }

    @Override
    public UserInfoVo getUserInfo(String username) {
        return userMapper.selectUserInfoByUserName(username);
    }

    @Override
    public PageInfo<UserInfoVo> selectAllUserInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfoVo> userInfoVos = userMapper.selectAllUserInfo();
        PageInfo<UserInfoVo> pageInfo = new PageInfo<>(userInfoVos);
        return pageInfo;
    }

    @Override
    public Integer updateUser(User user) {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(delegatingPasswordEncoder.encode(user.getPassword()));
        }
        return userMapper.updateById(user);
    }

    @Override
    public Integer deleteUser(Long id) {
        return userMapper.update(new UpdateWrapper<User>().eq("id", id).set("deleted", 1));
    }
}
