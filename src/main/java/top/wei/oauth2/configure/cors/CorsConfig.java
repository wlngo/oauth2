package top.wei.oauth2.configure.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.wei.oauth2.configure.Oauth2Properties;

/**
 * CorsConfig.
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域配置.
     *
     * @param oauth2Properties oauth2Properties
     * @return UrlBasedCorsConfigurationSource
     */
    @Bean
    @ConditionalOnProperty(prefix = "oauth2", name = "domainName")
    public UrlBasedCorsConfigurationSource corsConfigurationSource(Oauth2Properties oauth2Properties) {
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
        return source;
    }
}
