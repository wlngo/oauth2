package top.wei.oauth2.configure.authentication.captcha;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.beans.ConstructorProperties;
import java.io.Serial;
import java.util.Collection;

/**
 * @author n1
 */
@JsonSerialize
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private String captcha;

    /**
     * 此构造函数用来初始化未授信凭据.
     *
     * @param principal the principal
     * @param captcha   the captcha
     */
    public CaptchaAuthenticationToken(Object principal, String captcha) {
        super(null);
        this.principal = principal;
        this.captcha = captcha;
        setAuthenticated(false);
    }

    /**
     * 此构造函数用来初始化授信凭据.
     *
     * @param principal   the principal
     * @param captcha     the captcha
     * @param authorities the authorities
     */
    @ConstructorProperties({"principal","captcha"})
    public CaptchaAuthenticationToken(Object principal, String captcha,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.captcha = captcha;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.captcha;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        captcha = null;
    }


}
