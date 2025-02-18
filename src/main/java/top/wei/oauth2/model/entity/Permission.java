package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-06-13 15:37:10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_permission")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限id;权限id
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    /**
     * 权限代码;权限代码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 图标的名称
     */
    @TableField("icon")
    private String icon;

    /**
     * 权限名称;权限名称
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 路径;路径
     */
    @TableField("path")
    private String path;

    /**
     * 类型;1为一级菜单 2为二级菜单 3为按钮
     */
    @TableField("type")
    private Integer type;

    /**
     * 父级id;父级id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @TableField("create_id")
    private String createId;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人ID
     */
    @TableField("update_id")
    private String updateId;

    /**
     * 删除状态（1-正常，0-删除）
     */
    @TableField("deleted")
    private Boolean deleted;
}
