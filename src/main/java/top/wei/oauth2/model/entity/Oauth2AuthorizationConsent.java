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
 * 
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-06-13 15:37:10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("oauth2_authorization_consent")
public class Oauth2AuthorizationConsent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("registered_client_id")
    private String registeredClientId;

    @TableField("principal_name")
    private String principalName;

    @TableField("authorities")
    private String authorities;
}
