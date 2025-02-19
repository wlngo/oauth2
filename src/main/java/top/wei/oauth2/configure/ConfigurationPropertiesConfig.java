package top.wei.oauth2.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 开启属性文件绑定功能.
 */
@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
public class ConfigurationPropertiesConfig {
}
