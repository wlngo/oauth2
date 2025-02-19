package top.wei.oauth2.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户账号密码登录实体.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserLoginDto {

    /**
     * 用户ID.
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名称.
     */
    @TableField("username")
    private String username;

    /**
     * 手机号.
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 密码.
     */
    @TableField("password")
    private String password;

    /**
     * 角色名称;角色名称.
     */
    @TableField("role_name")
    private List<String> roleNames;

    /**
     * 定义帐户是否已过期(1 是，0 否）默认否.
     */
    @TableField("accountExpired")
    private Boolean accountExpired;

    /**
     * 定义帐户是否已锁定(1 是，0 否）默认否.
     */
    @TableField("accountLocked")
    private Boolean accountLocked;

    /**
     * 定义凭据是否已过期(1 是，0 否）默认否.
     */
    @TableField("credentialsExpired")
    private Boolean credentialsExpired;

    /**
     * 定义帐户是否被禁用(1 是，0 否）默认否.
     */
    @TableField("disabled")
    private Boolean disabled;

}
