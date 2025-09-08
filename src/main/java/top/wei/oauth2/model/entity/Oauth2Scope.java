package top.wei.oauth2.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * OAuth2 客户端权限定义表.
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("oauth2_scope")
public class Oauth2Scope implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID.
     */
    @TableId
    private String id;

    /**
     * 客户端 ID.
     */
    @TableField("registered_client_id")
    private String registeredClientId;

    /**
     * 权限标识，如 message.read、profile.
     */
    @TableField("scope")
    private String scope;

    /**
     * 权限名称，简短标题.
     */
    @TableField("name")
    private String name;

    /**
     * 权限说明，展示在授权页面.
     */
    @TableField("description")
    private String description;

    /**
     * 是否在授权页面展示该权限.
     */
    @TableField("is_visible")
    private Boolean isVisible;

    /**
     * 是否为默认勾选的权限.
     */
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 显示顺序（数字越小越靠前）.
     */
    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("created_at")
    private Instant createdAt;

    @TableField("updated_at")
    private Instant updatedAt;
}
