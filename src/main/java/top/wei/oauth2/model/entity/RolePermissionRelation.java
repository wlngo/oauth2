package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-06-13 15:37:10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_role_permission_relation")
public class RolePermissionRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id;主键id
     */
    @TableId("id")
    private String id;

    /**
     * 角色id;角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限id;权限id
     */
    @TableField("permission_id")
    private String permissionId;
}
