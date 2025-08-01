package top.wei.oauth2.service;

import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;

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
     * getPermissionByRoleNames.
     *
     * @param roleNames roleNames
     * @return PermissionDto
     */
    List<PermissionDto> getPermissionByRoleNames(List<String> roleNames);
}
