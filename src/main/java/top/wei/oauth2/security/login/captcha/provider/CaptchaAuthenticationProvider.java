package top.wei.oauth2.security.login.captcha.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import top.wei.oauth2.security.login.captcha.authentication.CaptchaAuthenticationToken;
import top.wei.oauth2.security.login.captcha.service.CaptchaService;
import top.wei.oauth2.security.login.captcha.service.CaptchaUserDetailsService;

import java.util.Collection;

/**
 * 验证码认证器.
 */
public class CaptchaAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private final CaptchaUserDetailsService captchaUserDetailsService;

    private final CaptchaService captchaService;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public CaptchaAuthenticationProvider(CaptchaUserDetailsService captchaUserDetailsService, CaptchaService captchaService) {
        this.captchaUserDetailsService = captchaUserDetailsService;
        this.captchaService = captchaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(CaptchaAuthenticationToken.class, authentication,
                () -> messages.getMessage(
                        "CaptchaAuthenticationProvider.onlySupports",
                        "Only CaptchaAuthenticationToken is supported"));

        CaptchaAuthenticationToken unAuthenticationToken = (CaptchaAuthenticationToken) authentication;

        String phone = unAuthenticationToken.getName();
        String rawCode = (String) unAuthenticationToken.getCredentials();
        // 验证码校验
        if (captchaService.verifyCaptcha(phone, rawCode)) {
            UserDetails userDetails = captchaUserDetailsService.loadUserByPhone(phone);
            return createSuccessAuthentication(authentication, userDetails);
        } else {
            throw new BadCredentialsException("captcha is not matched");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(captchaUserDetailsService, "captchaUserDetailsService must not be null");
        Assert.notNull(captchaService, "captchaService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据.
     * 封装用户信息 角色信息。
     *
     * @param authentication the authentication
     * @param user           the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {

        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        CaptchaAuthenticationToken authenticationToken = new CaptchaAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

}
