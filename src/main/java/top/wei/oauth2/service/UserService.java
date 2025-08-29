package top.wei.oauth2.service;

import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.model.vo.UserInfoVo;

import java.util.List;

/**
 * UserService.
 */
public interface UserService {


    /**
     * login.
     *
     * @param username username
     * @return UserLoginDto
     */
    UserLoginDto loadUserByUsername(String username);

    /**
     * createUser.
     *
     * @param user User
     */
    void createUser(User user);

    /**
     * getUserById.
     *
     * @param userId userId.
     * @return User
     */
    User getUserById(String userId);


    /**
     * getUserByUsername.
     *
     * @param username username.
     * @return User
     */
    User getUserByUsername(String username);


    /**
     * getPermissionByRoleNames.
     *
     * @param userId userId
     * @return PermissionDto
     */
    List<PermissionDto> getPermissionByUserid(String userId);

    /**
     * getUserInfo.
     *
     * @param username username
     * @return UserInfoVo
     */
    UserInfoVo getUserInfo(String username);
}
