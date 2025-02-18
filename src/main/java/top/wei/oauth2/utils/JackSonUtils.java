package top.wei.oauth2.utils;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import top.wei.oauth2.jackson2.IdServerJackson2Module;

import java.util.Collections;
import java.util.List;


/**
 * @author 魏亮宁
 * @date 2023年07月05日 17:08:00
 * 单例懒汉模式双检锁
 */
public class JackSonUtils {


    private volatile static ObjectMapper objectMapper;


    public static ObjectMapper getObjectMapper() {
        // 第一次校验
        if (objectMapper == null) {
            // 确定需要创建时，再抢夺锁
            synchronized (JackSonUtils.class) {
                // 抢到锁后再次检验，保证只会被实例化一次
                if (objectMapper == null) {
                    // 当重排序成 1 -> 3 -> 2 的时候可能出问题
                    // 通过 volatile 修复
                    objectMapper = new ObjectMapper();
                    ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
                    List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
                    objectMapper.registerModules(securityModules);
                    objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
                    objectMapper.registerModules(new IdServerJackson2Module());
                    objectMapper.addMixIn(Collections.singletonMap(null, null).getClass(), Collections.singletonMap(null, null).getClass());
                }
            }
        }
        return objectMapper;
    }
}
