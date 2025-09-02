package top.wei.oauth2.model.entity;

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
 * 菜单表.
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_menu")
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID.
     */
    @TableId("menu_id")
    private String menuId;

    /**
     * 菜单名称.
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单路径.
     */
    @TableField("menu_path")
    private String menuPath;

    /**
     * 菜单图标.
     */
    @TableField("menu_icon")
    private String menuIcon;

    /**
     * 父菜单ID.
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 排序.
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 菜单类型（0目录 1菜单 2按钮）.
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 菜单状态（0隐藏 1显示）.
     */
    @TableField("visible")
    private Boolean visible;

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
    @JsonIgnore
    private Boolean deleted;
}