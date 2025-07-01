package top.wei.oauth2.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24)
public class RedisSessionConfig {

    @Bean
    @ConditionalOnProperty(prefix = "oauth2", name = "domainName")
    public CorsFilter corsFilter(Oauth2Properties oauth2Properties) {
        CorsConfiguration config = new CorsConfiguration();
        //允许白名单域名进行跨域调用
        config.addAllowedOriginPattern(oauth2Properties.getDomainName());
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //支持私有网络访问 配合 addAllowedOriginPattern 和setAllowCredentials 使用
        config.setAllowPrivateNetwork(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
