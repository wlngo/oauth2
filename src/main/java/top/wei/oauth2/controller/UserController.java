package top.wei.oauth2.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.model.vo.UserInfoVo;
import top.wei.oauth2.service.UserService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;


/**
 * UserController.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    /**
     * 获取所有用户.
     *
     * @param page page
     * @param size size
     * @return users
     */
    @PostMapping("/getAllUsers")
    public Rest<PageInfo<UserInfoVo>> getAllUsers(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return RestBody.okData(userService.selectAllUserInfo(page, size));
    }

    /**
     * 根据用户名获取用户信息.
     *
     * @param username username
     * @return UserInfoVo.
     */
    @GetMapping("/findByUsername/{username}")
    public Rest<UserInfoVo> findByUsername(@PathVariable String username) {
        return RestBody.okData(userService.getUserInfo(username));
    }

    /**
     * 创建用户.
     *
     * @param user user
     * @return rows
     */
    @PostMapping("/createUser")
    public Rest<Integer> createUser(@RequestBody User user) {
        return RestBody.okData(userService.createUser(user));
    }

    /**
     * 更新用户.
     *
     * @param user user
     * @return rows
     */
    @PostMapping("/updateUser")
    public Rest<Integer> updateUser(@RequestBody User user) {
        return RestBody.okData(userService.updateUser(user));
    }

    /**
     * 删除用户.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deleteUser/{id}")
    public Rest<Integer> deleteUser(@PathVariable Long id) {
        return RestBody.okData(userService.deleteUser(id));
    }
}