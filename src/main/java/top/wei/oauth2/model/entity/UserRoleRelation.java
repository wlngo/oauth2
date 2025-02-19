package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user_role_relation")
public class UserRoleRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标识id.
     */
    @TableId("id")
    private String id;

    /**
     * 外键,t_user 用户id.
     */
    @TableField("user_id")
    private String userId;

    /**
     * 外键,t_role 角色id.
     */
    @TableField("role_id")
    private String roleId;
}
