package top.wei.oauth2.security.oauth2.authentication.client.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestClient;


/**
 * CustomAuthorizationCodeTokenResponseClientConfig.
 */
@Configuration
public class CustomAuthorizationCodeTokenResponseClientConfig {

    @Bean
    public RestClientAuthorizationCodeTokenResponseClient restClientAuthorizationCodeTokenResponseClient() {
        RestClientAuthorizationCodeTokenResponseClient restClientAuthorizationCodeTokenResponseClient = new RestClientAuthorizationCodeTokenResponseClient();
        OAuth2AccessTokenResponseHttpMessageConverter oAuth2AccessTokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        oAuth2AccessTokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomMapOAuth2AccessTokenResponseConverter());
        RestClient restClient = RestClient.builder()
                .messageConverters(messageConverters -> {
                    messageConverters.clear();
                    messageConverters.add(new FormHttpMessageConverter());
                    messageConverters.add(oAuth2AccessTokenResponseHttpMessageConverter);
                })
                .defaultStatusHandler(new OAuth2ErrorResponseErrorHandler())
                .build();
        restClientAuthorizationCodeTokenResponseClient.setRestClient(restClient);
        return restClientAuthorizationCodeTokenResponseClient;

    }

}
