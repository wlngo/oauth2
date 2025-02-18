package top.wei.oauth2.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 魏亮宁
 * @date 2023年12月10日 17:45:00
 * @EnableConfigurationProperties 开启属性文件绑定功能
 */
@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
public class ConfigurationPropertiesConfig {
}
