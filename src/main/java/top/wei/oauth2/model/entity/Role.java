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
@TableName("t_role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID.
     */
    @TableId("role_id")
    private String roleId;

    /**
     * 角色名称.
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 展示名称.
     */
    @TableField("role_content")
    private String roleContent;

    /**
     * 描述.
     */
    @TableField("description")
    private String description;

    /**
     * 创建时间.
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 创建人ID.
     */
    @TableField("create_id")
    private String createId;

    /**
     * 修改时间.
     */
    @TableField("updated_at")
    private Date updatedAt;

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
}
