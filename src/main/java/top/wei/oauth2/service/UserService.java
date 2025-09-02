package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
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
     * @return rows
     */
    Integer createUser(User user);

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

    /**
     * 查询所有用户信息.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param keyword  keyword
     * @return List UserInfoVo
     */
    PageInfo<User> selectAllUserInfo(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新用户.
     *
     * @param user user
     * @return rows
     */
    Integer updateUser(User user);

    /**
     * 删除用户.
     *
     * @param id id
     * @return rows
     */
    Integer deleteUser(String id);
}
