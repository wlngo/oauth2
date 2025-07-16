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
     * 用户名，唯一，用于登录.
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码，建议加密存储（如 bcrypt）.
     */
    @TableField("password")
    private String password;

    /**
     * 用户昵称，用于展示.
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 真实姓名，用于实名认证或内部识别.
     */
    @TableField("real_name")
    private String realName;

    /**
     * 用户邮箱地址，唯一，可用于找回密码或登录.
     */
    @TableField("email")
    private String email;

    /**
     * 邮箱是否已验证，1 表示已验证，0 表示未验证.
     */
    @TableField("email_verified")
    private Boolean emailVerified;

    /**
     * 手机号，唯一，可用于登录或接收验证码.
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 手机号是否已验证，1 表示已验证，0 表示未验证.
     */
    @TableField("phone_number_verified")
    private Boolean phoneNumberVerified;

    /**
     * 性别：-1=未知，0=女，1=男.
     */
    @TableField("gender")
    private Byte gender;

    /**
     * 出生日期，用于年龄计算或生日提醒.
     */
    @TableField("birthdate")
    private Date birthdate;

    /**
     * 用户头像图片URL.
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 账户是否过期，1 是，0 否.
     */
    @TableField("account_expired")
    private Boolean accountExpired;

    /**
     * 账户是否被锁定，1 是，0 否.
     */
    @TableField("account_locked")
    private Boolean accountLocked;

    /**
     * 凭据（密码）是否过期，1 是，0 否.
     */
    @TableField("credentials_expired")
    private Boolean credentialsExpired;

    /**
     * 账户是否被禁用，1 是，0 否.
     */
    @TableField("disabled")
    private Boolean disabled;

    /**
     * 是否逻辑删除，1 表示已删除，0 表示未删除.
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 客户端密钥，用于某些认证场景.
     */
    @TableField("secret")
    private String secret;

    /**
     * 记录用户创建时间，自动填充.
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 记录用户最后更新时间，自动更新.
     */
    @TableField("updated_at")
    private Date updatedAt;
}
