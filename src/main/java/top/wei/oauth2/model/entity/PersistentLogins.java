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

/**
 * <p>
 * 
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-12-10 15:47:30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_persistent_logins")
public class PersistentLogins implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableId("series")
    private String series;

    @TableField("token")
    private String token;

    @TableField("last_used")
    private Date lastUsed;


}
