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


@Getter
@Setter
@Accessors(chain = true)
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClient implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("client_id")
    private String clientId;

    @TableField("client_id_issued_at")
    private Instant clientIdIssuedAt;

    @TableField("client_secret")
    private String clientSecret;

    @TableField("client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @TableField("client_name")
    private String clientName;

    @TableField("client_authentication_methods")
    private String clientAuthenticationMethods;

    @TableField("authorization_grant_types")
    private String authorizationGrantTypes;

    @TableField("redirect_uris")
    private String redirectUris;

    @TableField("post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    @TableField("scopes")
    private String scopes;

    @TableField("client_settings")
    private String clientSettings;

    @TableField("token_settings")
    private String tokenSettings;
}
