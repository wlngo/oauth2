package top.wei.oauth2.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomOidcUserDto.
 */
@JsonSerialize
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class CustomOidcUserDto extends CustomOAuth2UserDto implements OidcUser {

    private final OidcIdToken idToken;

    private final OidcUserInfo userInfo;


    /**
     * Constructs a {@code DefaultOidcUser} using the provided parameters.
     *
     * @param authorities the authorities granted to the user
     * @param idToken     the {@link OidcIdToken ID Token} containing claims about the user
     * @param userInfo    the {@link OidcUserInfo UserInfo} containing claims about the user,
     *                    may be {@code null}
     * @param name        name
     */
    public CustomOidcUserDto(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
                             OidcUserInfo userInfo, String name) {
        super(authorities, collectClaims(idToken, userInfo), name);
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.getAttributes();
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    /**
     * Collects the claims from the provided {@link OidcIdToken} and {@link OidcUserInfo}.
     *
     * @param idToken  idToken
     * @param userInfo userInfo
     * @return map
     */
    public static Map<String, Object> collectClaims(OidcIdToken idToken, OidcUserInfo userInfo) {
        Assert.notNull(idToken, "idToken cannot be null");
        Map<String, Object> claims = new HashMap<>();
        if (userInfo != null) {
            claims.putAll(userInfo.getClaims());
        }
        claims.putAll(idToken.getClaims());
        return claims;
    }

}
