package top.wei.oauth2.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {
    /**
     * jks 文件位置.
     */
    private String jwkPath;

    /**
     * jks名字.
     */
    private String jwkAlias;

    /**
     * jks 密码.
     */
    private String jwkPass;

    /**
     * oauth2 issuer.
     */
    private String issuer;

    /**
     * 域名.
     */
    private String domainName;
}
