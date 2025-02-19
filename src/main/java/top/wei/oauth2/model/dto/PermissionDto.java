package top.wei.oauth2.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@TableName("t_permission")
public class PermissionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限id;权限id.
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;

    /**
     * 权限代码;权限代码.
     */
    @TableField("permission_code")
    private String permissionCode;

}
