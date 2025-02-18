package top.wei.oauth2.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 魏亮宁
 * @date 2023年07月12日 16:25:00
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24)
public class RedisSessionConfig {

    @Bean
    @ConditionalOnProperty(prefix = "oauth2",name = "domainName")
    public CorsFilter corsFilter(Oauth2Properties oauth2Properties) {
        CorsConfiguration config = new CorsConfiguration();
        //允许白名单域名进行跨域调用
        config.addAllowedOriginPattern(oauth2Properties.getDomainName());
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
