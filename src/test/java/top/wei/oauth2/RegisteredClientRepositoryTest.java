package top.wei.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import top.wei.oauth2.mapper.Oauth2RegisteredClientMapper;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.mapper.UserRoleRelationMapper;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.model.entity.UserRoleRelation;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisteredClientRepositoryTest {

    private final RegisteredClientRepository registeredClientRepository;

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    private final UserMapper userMapper;

    private final UserRoleRelationMapper userRoleRelationMapper;

    @Test
    void contextLoads() {
        boolean exists = oauth2RegisteredClientMapper.exists(new QueryWrapper<Oauth2RegisteredClient>().eq("client_id", "oidc-client"));
        if (!exists) {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("oidc-client")
                    .clientSettings(ClientSettings.builder().requireProofKey(true).build())
                    .clientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode("123456"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://127.0.0.1:8080/login/oauth2/code/felord")
                    .postLogoutRedirectUri("https://www.wlngo.top:4999/")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .scope("email")
                    .scope("address")
                    .scope("message.read")
                    .scope("message.write")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();
            registeredClientRepository.save(registeredClient);
        }

        boolean exists1 = oauth2RegisteredClientMapper.exists(new QueryWrapper<Oauth2RegisteredClient>().eq("client_id", "messaging-client"));
        if (!exists1) {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("messaging-client")
                    .clientSecret("{noop}secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                    .redirectUri("http://127.0.0.1:8080/authorized")
                    .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .scope("message.read")
                    .scope("message.write")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();
            registeredClientRepository.save(registeredClient);
        }

        //用户测试用例
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean flag = userMapper.exists(new QueryWrapper<User>().eq("username", "2252603132@qq.com"));
        if (!flag) {
            User user = new User();
            user.setUsername("2252603132@qq.com")
                    .setPassword(delegatingPasswordEncoder.encode("6060rudtn!"))
                    .setNickName("小海豹")
                    .setRealName("小海豹")
                    .setPhoneNumberVerified(true)
                    .setPhoneNumber("19031580292")
                    .setEmailVerified(true)
                    .setEmail("w2252603132@gmail.com");
            userMapper.insert(user);
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserId(user.getUserId())
                    .setRoleId("1");
            userRoleRelationMapper.insert(userRoleRelation);
        }
    }
}
