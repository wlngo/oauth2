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
 * 角色菜单关系表.
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_role_menu_relation")
public class RoleMenuRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    @TableId("id")
    private String id;

    /**
     * 角色id.
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 菜单id.
     */
    @TableField("menu_id")
    private String menuId;
}