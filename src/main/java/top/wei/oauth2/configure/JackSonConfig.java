package top.wei.oauth2.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wei.oauth2.utils.JackSonUtils;

/**
 * JackSonConfig.
 */
@Configuration
public class JackSonConfig {

    /**
     * getSecurityObjectMapper.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return JackSonUtils.getSecurityObjectMapper();
    }
}
