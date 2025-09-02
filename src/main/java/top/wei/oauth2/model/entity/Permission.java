package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限表.
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_permission")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限ID.
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    /**
     * 权限代码.
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 权限名称.
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 创建时间.
     */
    @TableField("create_at")
    private Date createAt;

    /**
     * 创建人ID.
     */
    @TableField("create_id")
    private String createId;

    /**
     * 修改时间.
     */
    @TableField("update_at")
    private Date updateAt;

    /**
     * 修改人ID.
     */
    @TableField("update_id")
    private String updateId;

    /**
     * 删除状态（1 是，0 否）.
     */
    @TableField("deleted")
    @JsonIgnore
    private Boolean deleted;
}
