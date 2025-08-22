package top.wei.oauth2.security.login.captcha.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * CaptchaUserDetailsService.
 */
public interface CaptchaUserDetailsService {
    /**
     * load user by phone.
     *
     * @param phone phone
     * @return userDetails
     * @throws UsernameNotFoundException not found user
     */
    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;
}
