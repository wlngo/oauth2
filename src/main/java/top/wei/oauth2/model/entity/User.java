package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 密码.
     */
    @TableField("password")
    private String password;

    /**
     * 昵称.
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 真实姓名.
     */
    @TableField("real_name")
    private String realName;


    /**
     * 电话号码是否验证(1 是，0 否）默认否.
     */
    @TableField("phone_number_verified")
    private Boolean phoneNumberVerified;

    /**
     * 手机号.
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 头像.
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 电子邮件是否验证(1 是，0 否）默认否.
     */
    @TableField("email_verified")
    private Boolean emailVerified;

    /**
     * 邮箱.
     */
    @TableField("email")
    private String email;

    /**
     * 性别   -1 未知 0 女性  1 男性.
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 出生日期.
     */
    @TableField("birthdate")
    private Date birthdate;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人ID.
     */
    @TableField("create_id")
    private String createId;

    /**
     * 修改时间.
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人ID.
     */
    @TableField("update_id")
    private String updateId;

    /**
     * 删除状态（1 是，0 否）默认否.
     */
    @TableField("deleted")
    private Boolean deleted;

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

    @TableField("secret")
    private String secret;
}
