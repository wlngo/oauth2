package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;



public interface UserMapper extends BaseMapper<User> {


    /**
     * 通过用户查询所有角色.
     *
     * @param username username
     * @return UserLoginDto
     */
    UserLoginDto selectUserRoleByUserName(@Param("username") String username);

    /**
     * 通过手机号查询所有角色.
     *
     * @param phoneNumber 手机号
     * @return UserLoginDto
     */
    UserLoginDto selectUserRoleByPhoneNumber(@Param("phoneNumber") String phoneNumber);


}
