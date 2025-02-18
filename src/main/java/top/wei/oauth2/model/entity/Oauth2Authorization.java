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
 * @since 2023-12-28 17:52:42
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("oauth2_authorization")
public class Oauth2Authorization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("registered_client_id")
    private String registeredClientId;

    @TableField("principal_name")
    private String principalName;

    @TableField("authorization_grant_type")
    private String authorizationGrantType;

    @TableField("authorized_scopes")
    private String authorizedScopes;

    @TableField("attributes")
    private byte[] attributes;

    @TableField("state_index_sha256")
    private String stateIndexSha256;

    @TableField("state")
    private String state;

    @TableField("authorization_code_value_index_sha256")
    private String authorizationCodeValueIndexSha256;

    @TableField("authorization_code_value")
    private byte[] authorizationCodeValue;

    @TableField("authorization_code_issued_at")
    private Date authorizationCodeIssuedAt;

    @TableField("authorization_code_expires_at")
    private Date authorizationCodeExpiresAt;

    @TableField("authorization_code_metadata")
    private byte[] authorizationCodeMetadata;

    @TableField("access_token_value_index_sha256")
    private String accessTokenValueIndexSha256;

    @TableField("access_token_value")
    private byte[] accessTokenValue;

    @TableField("access_token_issued_at")
    private Date accessTokenIssuedAt;

    @TableField("access_token_expires_at")
    private Date accessTokenExpiresAt;

    @TableField("access_token_metadata")
    private byte[] accessTokenMetadata;

    @TableField("access_token_type")
    private String accessTokenType;

    @TableField("access_token_scopes")
    private String accessTokenScopes;

    @TableField("oidc_id_token_value_index_sha256")
    private String oidcIdTokenValueIndexSha256;

    @TableField("oidc_id_token_value")
    private byte[] oidcIdTokenValue;

    @TableField("oidc_id_token_issued_at")
    private Date oidcIdTokenIssuedAt;

    @TableField("oidc_id_token_expires_at")
    private Date oidcIdTokenExpiresAt;

    @TableField("oidc_id_token_metadata")
    private byte[] oidcIdTokenMetadata;

    @TableField("refresh_token_value_index_sha256")
    private String refreshTokenValueIndexSha256;

    @TableField("refresh_token_value")
    private byte[] refreshTokenValue;

    @TableField("refresh_token_issued_at")
    private Date refreshTokenIssuedAt;

    @TableField("refresh_token_expires_at")
    private Date refreshTokenExpiresAt;

    @TableField("refresh_token_metadata")
    private byte[] refreshTokenMetadata;

    @TableField("user_code_value_index_sha256")
    private String userCodeValueIndexSha256;

    @TableField("user_code_value")
    private byte[] userCodeValue;

    @TableField("user_code_issued_at")
    private Date userCodeIssuedAt;

    @TableField("user_code_expires_at")
    private Date userCodeExpiresAt;

    @TableField("user_code_metadata")
    private byte[] userCodeMetadata;

    @TableField("device_code_value_index_sha256")
    private String deviceCodeValueIndexSha256;

    @TableField("device_code_value")
    private byte[] deviceCodeValue;

    @TableField("device_code_issued_at")
    private Date deviceCodeIssuedAt;

    @TableField("device_code_expires_at")
    private Date deviceCodeExpiresAt;

    @TableField("device_code_metadata")
    private byte[] deviceCodeMetadata;
}
