package top.wei.oauth2.configure.authentication;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

/**
 * @author 魏亮宁
 * @date 2023年07月10日 13:59:00
 */
public interface OidcUserInfoService {


    /**
     * 通过用户名 加载用户数据
     *
     * @param username 用户名
     * @return OidcUserInfo
     */
    OidcUserInfo loadUser(String username);
}
