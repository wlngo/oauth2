package top.wei.oauth2.configure;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import top.wei.oauth2.configure.authentication.LoginFilterSecurityConfigurer;
import top.wei.oauth2.configure.authentication.captcha.CaptchaService;
import top.wei.oauth2.configure.authentication.captcha.CaptchaUserDetailsService;
import top.wei.oauth2.configure.authentication.impl.CustomOidcUserInfoMapperImpl;
import top.wei.oauth2.handler.RedirectLoginAuthenticationSuccessHandler;
import top.wei.oauth2.handler.RememberMeRedirectLoginAuthenticationSuccessHandler;
import top.wei.oauth2.handler.SimpleAuthenticationEntryPoint;

import java.security.KeyStore;


@EnableMethodSecurity
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class Oauth2Config {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    private static final String SYSTEM_ANT_PATH = "/system/**";


    /**
     * 白名单不拦截的接口或静态资源.
     * 优先级最高
     */
    @Configuration(proxyBeanMethods = false)
    public static class WhiteListSecurityConfiguration {
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public SecurityFilterChain whiteListSecurityFilterChain(HttpSecurity http) throws Exception {
            http.securityMatcher(
                            "/csrf",
                            "/favicon.ico",
                            "/error",
                            "/captcha/sendSms"
                    ).authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                    .requestCache(RequestCacheConfigurer::disable)
                    .securityContext(AbstractHttpConfigurer::disable)
                    .sessionManagement(AbstractHttpConfigurer::disable)
                    .csrf(Customizer.withDefaults())
                    .cors(Customizer.withDefaults());

            return http.build();
        }
    }

    /**
     * OAuth2.0授权服务器配置 仅次于白名单.
     */
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    public static class AuthorizationServerConfiguration {

        private final Oauth2Properties oauth2Properties;


        private final OAuth2AuthorizationService oAuth2AuthorizationService;

        private final RegisteredClientRepository registeredClientRepository;

        private final OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService;

        private final CustomOidcUserInfoMapperImpl customOidcUserInfoMapper;

        /**
         * Authorization server  优先级 Ordered.HIGHEST_PRECEDENCE + 1.
         *
         * @param http http
         * @return SecurityFilterChain
         * @throws Exception Exception
         */

        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE + 1)
        public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
                throws Exception {
            //oauth2配置
            OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                    new OAuth2AuthorizationServerConfigurer();
            //自定义授权页
            authorizationServerConfigurer.authorizationEndpoint(oAuth2AuthorizationEndpointConfigurer
                    -> {
//                oAuth2AuthorizationEndpointConfigurer.consentPage(CUSTOM_CONSENT_PAGE_URI);
            });
            //todo 撤销token端点 销毁redis key
//        authorizationServerConfigurer.tokenRevocationEndpoint()
            //获取授权服务器端点
            RequestMatcher endpointsMatcher = authorizationServerConfigurer
                    .getEndpointsMatcher();
            //配置授权服务器端点
            http.securityMatcher(endpointsMatcher)
                    .authorizeHttpRequests(authorize ->
                            authorize.anyRequest().authenticated()
                    )
                    .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                    .with(authorizationServerConfigurer, oAuth2AuthorizationServerConfigurer -> {
                        //管理 OAuth 2.0 Authorization(s) 和 RegisteredClient 储存
                        oAuth2AuthorizationServerConfigurer.authorizationService(oAuth2AuthorizationService)
                                .registeredClientRepository(registeredClientRepository)
                                .authorizationConsentService(oAuth2AuthorizationConsentService)
                                // Enable OpenID Connect 1.0
                                .oidc(oidcConfigurer ->
                                        oidcConfigurer.userInfoEndpoint(oidcUserInfoEndpointConfigurer ->
                                                oidcUserInfoEndpointConfigurer.userInfoMapper(customOidcUserInfoMapper)));
                    });

            http
                    //Redirect to the login page when exceptions
                    .exceptionHandling(exceptions -> exceptions
                            .defaultAuthenticationEntryPointFor(
                                    new LoginUrlAuthenticationEntryPoint("/login"),
                                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                            )
                    );

            return http.build();
        }


        /**
         * 生成jks命令
         * keytool -genkeypair -alias www.wlngo.top -keypass www.wlngo.top -keyalg RSA -keysize 2048 -validity 36500 -keystore ./www.wlngo.top.jks -storetype pkcs12 -storepass www.wlngo.top
         * -alias 别名
         * -keypass 指定生成密钥的密码
         * -keyalg 指定密钥使用的加密算法（如 RSA）
         * -keysize 密钥大小
         * -validity 过期时间，单位：天
         * -keystore 指定存储密钥的 密钥库的生成路径、名称。
         * -storepass 指定访问密钥库的密码。
         * -storetype 密钥储存格式 example pkcs12.
         *
         * @return JWKSource
         */
        @Bean
        @SneakyThrows
        public JWKSource<SecurityContext> jwkSource() {

            String path = oauth2Properties.getJwkPath();
            String alias = oauth2Properties.getJwkAlias();
            String pass = oauth2Properties.getJwkPass();

            ClassPathResource resource = new ClassPathResource(path);
            KeyStore jks = KeyStore.getInstance("jks");
            char[] pin = pass.toCharArray();
            jks.load(resource.getInputStream(), pin);
            RSAKey rsaKey = RSAKey.load(jks, alias, pin);
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        }


        /**
         * jwk 解密配置.
         *
         * @param jwkSource jwkSource
         * @return JwtDecoder
         */
        @Bean
        public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
            return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
        }

    }


    /**
     * 普通用户访问安全配置.(默认兜底配置).
     */
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    public static class DefaultUserSecurityConfiguration {

        private final UserDetailsService userDetailsService;

        private final PersistentTokenRepository persistentTokenRepository;

        private final CaptchaService captchaService;

        private final CaptchaUserDetailsService captchaUserDetailsService;

        /**
         * 最低优先级.
         *
         * @param http                http
         * @param securityFilterChain securityFilterChain
         * @return SecurityFilterChain
         * @throws Exception Exception
         */

        @Bean
        @Order
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                              @Qualifier("authorizationServerSecurityFilterChain") SecurityFilterChain securityFilterChain)
                throws Exception {
            DefaultSecurityFilterChain authorizationServerFilterChain = (DefaultSecurityFilterChain) securityFilterChain;
            SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
            AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
            RedirectLoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler = new RedirectLoginAuthenticationSuccessHandler();
            RememberMeRedirectLoginAuthenticationSuccessHandler rememberMeRedirectLoginAuthenticationSuccessHandler = new RememberMeRedirectLoginAuthenticationSuccessHandler();
            http
                    .authorizeHttpRequests(authorize -> {
                        authorize.requestMatchers(new AndRequestMatcher(
                                new NegatedRequestMatcher(new AntPathRequestMatcher(SYSTEM_ANT_PATH)),
                                new NegatedRequestMatcher(authorizationServerFilterChain.getRequestMatcher())
                        ));
                        authorize.anyRequest().authenticated();
                    })

                    .csrf(Customizer.withDefaults())
                    //加载用户特定数据的核心接口
                    .userDetailsService(userDetailsService)
                    .formLogin(httpSecurityFormLoginConfigurer ->
                            httpSecurityFormLoginConfigurer
//                                    .loginPage("https://wlngo.top:9400/oauth2/login")
                                    .loginProcessingUrl("/login")
                                    .successHandler(loginAuthenticationSuccessHandler)
                                    .failureHandler(authenticationFailureHandler))
                    // Redirect to the login page when not authenticated from the
                    // authorization endpoint
                    .exceptionHandling(exceptions -> exceptions
                            .defaultAuthenticationEntryPointFor(
                                    new LoginUrlAuthenticationEntryPoint("/login"),
                                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                            )
                    )

                    .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                            .userDetailsService(userDetailsService).tokenValiditySeconds(60 * 60 * 24 * 7)
                            .tokenRepository(persistentTokenRepository)
                            .authenticationSuccessHandler(rememberMeRedirectLoginAuthenticationSuccessHandler)
                    )
                    // 手机号验证码登录模拟
                    .with(new LoginFilterSecurityConfigurer<>(),
                            httpSecurityLoginFilterSecurityConfigurer ->
                                    httpSecurityLoginFilterSecurityConfigurer.captchaLogin(captchaLoginConfigurer ->
                                            // 验证码校验 1 在此处配置 优先级最高 2 注册为Spring Bean 可以免配置
                                            captchaLoginConfigurer.captchaService(captchaService)
                                                    // 根据手机号查询用户UserDetials  1 在此处配置 优先级最高 2 注册为Spring Bean 可以免配置
                                                    .captchaUserDetailsService(captchaUserDetailsService)
                                                    // 两个登录保持一致
                                                    .successHandler(loginAuthenticationSuccessHandler)
                                                    // 两个登录保持一致
                                                    .failureHandler(authenticationFailureHandler)
                                    ))
                    // Accept access tokens for User Info and/or Client Registration
                    .oauth2ResourceServer(resourceServer -> resourceServer
                            .jwt(Customizer.withDefaults()));
            ;
            return http.build();
        }

    }


}
