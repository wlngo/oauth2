package top.wei.oauth2.utils;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import top.wei.oauth2.jackson2.IdServerJackson2Module;

import java.util.List;

/**
 * 单例懒汉模式双检锁拆分：普通和安全两种 ObjectMapper.
 */
public class JackSonUtils {

    private static volatile ObjectMapper defaultObjectMapper;

    private static volatile ObjectMapper securityObjectMapper;

    /**
     * 获取普通使用的 ObjectMapper（只注册基础模块）.
     * @return ObjectMapper
     */
    public static ObjectMapper getDefaultObjectMapper() {
        if (defaultObjectMapper == null) {
            synchronized (JackSonUtils.class) {
                if (defaultObjectMapper == null) {
                    defaultObjectMapper = new ObjectMapper();
                    // 只注册常用模块
                    defaultObjectMapper.registerModule(new JavaTimeModule());
                    defaultObjectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

                    // 如果还有其他通用模块，可以加在这里
                }
            }
        }
        return defaultObjectMapper;
    }

    /**
     * 获取安全使用的 ObjectMapper（注册 Spring Security + OAuth2 等模块）.
     * @return ObjectMapper
     */
    public static ObjectMapper getSecurityObjectMapper() {
        if (securityObjectMapper == null) {
            synchronized (JackSonUtils.class) {
                if (securityObjectMapper == null) {
                    securityObjectMapper = new ObjectMapper();
                    ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
                    List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
                    securityObjectMapper.registerModules(securityModules);
                    securityObjectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
                    securityObjectMapper.registerModules(new IdServerJackson2Module());
                    securityObjectMapper.registerModule(new JavaTimeModule());
                }
            }
        }
        return securityObjectMapper;
    }
}
