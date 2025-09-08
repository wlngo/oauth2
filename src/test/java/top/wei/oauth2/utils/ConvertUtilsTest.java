package top.wei.oauth2.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ConvertUtils 测试类，重点测试多个重定向URI的转换功能.
 */
public class ConvertUtilsTest {

    @Test
    public void testOauth2ToSpringOauth2SingleUris() {
        // 准备测试数据 - 单个URI
        Oauth2RegisteredClient oauth2Client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback",
                "http://localhost:8080/logout"
        );

        // 执行转换
        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2Client);

        // 验证结果
        assertNotNull(registeredClient);
        assertEquals("test-client", registeredClient.getClientId());
        assertEquals(1, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        assertEquals(1, registeredClient.getPostLogoutRedirectUris().size());
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/logout"));
    }

    @Test
    public void testOauth2ToSpringOauth2MultipleRedirectUris() {
        // 准备测试数据 - 多个重定向URI
        Oauth2RegisteredClient oauth2Client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback,http://localhost:8080/callback2,https://example.com/oauth/callback",
                "http://localhost:8080/logout"
        );

        // 执行转换
        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2Client);

        // 验证结果
        assertNotNull(registeredClient);
        assertEquals(3, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback2"));
        assertTrue(registeredClient.getRedirectUris().contains("https://example.com/oauth/callback"));
        
        assertEquals(1, registeredClient.getPostLogoutRedirectUris().size());
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/logout"));
    }

    @Test
    public void testOauth2ToSpringOauth2MultiplePostLogoutUris() {
        // 准备测试数据 - 多个登出重定向URI
        Oauth2RegisteredClient oauth2Client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback",
                "http://localhost:8080/logout,http://localhost:8080/goodbye,https://example.com/logout"
        );

        // 执行转换
        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2Client);

        // 验证结果
        assertNotNull(registeredClient);
        assertEquals(1, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        
        assertEquals(3, registeredClient.getPostLogoutRedirectUris().size());
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/logout"));
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/goodbye"));
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("https://example.com/logout"));
    }

    @Test
    public void testOauth2ToSpringOauth2MultipleAllUris() {
        // 准备测试数据 - 所有URI都是多个
        Oauth2RegisteredClient oauth2Client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback,https://app.example.com/auth/callback",
                "http://localhost:8080/logout,https://app.example.com/logout"
        );

        // 执行转换
        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2Client);

        // 验证结果
        assertNotNull(registeredClient);
        assertEquals(2, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        assertTrue(registeredClient.getRedirectUris().contains("https://app.example.com/auth/callback"));
        
        assertEquals(2, registeredClient.getPostLogoutRedirectUris().size());
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/logout"));
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("https://app.example.com/logout"));
    }

    @Test
    public void testSpringOauth2ToOauth2MultipleUris() {
        // 准备测试数据 - Spring的RegisteredClient，包含多个URI
        RegisteredClient registeredClient = RegisteredClient.withId("test-id")
                .clientId("test-client")
                .clientSecret("{noop}test-secret")
                .clientName("Test Client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUris(uris -> {
                    uris.add("http://localhost:8080/callback");
                    uris.add("http://localhost:8080/callback2");
                    uris.add("https://example.com/oauth/callback");
                })
                .postLogoutRedirectUris(uris -> {
                    uris.add("http://localhost:8080/logout");
                    uris.add("https://example.com/logout");
                })
                .scopes(scopes -> {
                    scopes.add("read");
                    scopes.add("write");
                })
                .build();

        // 执行转换
        Oauth2RegisteredClient oauth2Client = ConvertUtils.springOauth2RegisteredClientToOauth2RegisteredClient(registeredClient);

        // 验证结果
        assertNotNull(oauth2Client);
        assertEquals("test-client", oauth2Client.getClientId());
        
        // 验证重定向URI（应该是逗号分隔的字符串）
        String redirectUris = oauth2Client.getRedirectUris();
        assertTrue(redirectUris.contains("http://localhost:8080/callback"));
        assertTrue(redirectUris.contains("http://localhost:8080/callback2"));
        assertTrue(redirectUris.contains("https://example.com/oauth/callback"));
        // 验证有3个URI（2个逗号分隔符）
        assertEquals(3, redirectUris.split(",").length);
        
        // 验证登出重定向URI
        String postLogoutUris = oauth2Client.getPostLogoutRedirectUris();
        assertTrue(postLogoutUris.contains("http://localhost:8080/logout"));
        assertTrue(postLogoutUris.contains("https://example.com/logout"));
        // 验证有2个URI（1个逗号分隔符）
        assertEquals(2, postLogoutUris.split(",").length);
    }

    @Test
    public void testRoundTripConversionMultipleUris() {
        // 测试往返转换 - 确保数据不丢失
        Oauth2RegisteredClient originalClient = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback,https://app.example.com/auth/callback,https://secondary.example.com/callback",
                "http://localhost:8080/logout,https://app.example.com/logout"
        );

        // 第一次转换：Oauth2RegisteredClient -> RegisteredClient
        RegisteredClient springClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(originalClient);
        
        // 第二次转换：RegisteredClient -> Oauth2RegisteredClient
        Oauth2RegisteredClient convertedClient = ConvertUtils.springOauth2RegisteredClientToOauth2RegisteredClient(springClient);

        // 验证往返转换后数据一致
        assertEquals(originalClient.getClientId(), convertedClient.getClientId());
        assertEquals(originalClient.getClientName(), convertedClient.getClientName());
        
        // 验证URI数据一致（注意：可能顺序不同，但内容应该相同）
        Set<String> originalRedirectUris = Set.of(originalClient.getRedirectUris().split(","));
        Set<String> convertedRedirectUris = Set.of(convertedClient.getRedirectUris().split(","));
        assertEquals(originalRedirectUris, convertedRedirectUris);
        
        Set<String> originalPostLogoutUris = Set.of(originalClient.getPostLogoutRedirectUris().split(","));
        Set<String> convertedPostLogoutUris = Set.of(convertedClient.getPostLogoutRedirectUris().split(","));
        assertEquals(originalPostLogoutUris, convertedPostLogoutUris);
    }

    @Test
    public void testEmptyAndNullUris() {
        // 测试空值和null值的处理 - 提供有效的重定向URI，测试postLogoutRedirectUris为空的情况
        Oauth2RegisteredClient oauth2Client = createTestOauth2RegisteredClient("http://localhost:8080/callback", null);

        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2Client);

        assertNotNull(registeredClient);
        assertEquals(1, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        assertTrue(registeredClient.getPostLogoutRedirectUris().isEmpty());
    }

    @Test
    public void testValidateOauth2RegisteredClientUrisValid() {
        Oauth2RegisteredClient client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback,https://example.com/oauth/callback",
                "http://localhost:8080/logout,https://example.com/logout"
        );

        List<String> errors = ConvertUtils.validateOauth2RegisteredClientUris(client);
        assertTrue(errors.isEmpty(), "Valid URIs should not produce any validation errors");
    }

    @Test
    public void testValidateOauth2RegisteredClientUrisInvalid() {
        Oauth2RegisteredClient client = createTestOauth2RegisteredClient(
                "invalid-uri,http://localhost:8080/callback#fragment",
                "ftp://example.com/logout"
        );

        List<String> errors = ConvertUtils.validateOauth2RegisteredClientUris(client);
        assertTrue(errors.size() > 0, "Invalid URIs should produce validation errors");
        assertTrue(errors.stream().anyMatch(error -> 
                error.contains("格式无效") || error.contains("缺少协议") || error.contains("不能包含 fragment")));
    }

    @Test
    public void testValidateOauth2RegisteredClientUrisDuplicates() {
        Oauth2RegisteredClient client = createTestOauth2RegisteredClient(
                "http://localhost:8080/callback,http://localhost:8080/callback",
                "http://localhost:8080/logout,http://localhost:8080/logout"
        );

        List<String> errors = ConvertUtils.validateOauth2RegisteredClientUris(client);
        assertEquals(2, errors.size());
        assertTrue(errors.stream().anyMatch(error -> error.contains("重定向URI中包含重复项")));
        assertTrue(errors.stream().anyMatch(error -> error.contains("登出重定向URI中包含重复项")));
    }

    @Test
    public void testNormalizationInConversion() {
        // 测试转换过程中的URI规范化
        Oauth2RegisteredClient client = createTestOauth2RegisteredClient(
                " http://localhost:8080/callback , https://example.com/oauth/callback ",
                " http://localhost:8080/logout , https://example.com/logout "
        );

        RegisteredClient registeredClient = ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(client);

        assertEquals(2, registeredClient.getRedirectUris().size());
        assertTrue(registeredClient.getRedirectUris().contains("http://localhost:8080/callback"));
        assertTrue(registeredClient.getRedirectUris().contains("https://example.com/oauth/callback"));
        
        assertEquals(2, registeredClient.getPostLogoutRedirectUris().size());
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("http://localhost:8080/logout"));
        assertTrue(registeredClient.getPostLogoutRedirectUris().contains("https://example.com/logout"));
    }

    private Oauth2RegisteredClient createTestOauth2RegisteredClient(String redirectUris, String postLogoutRedirectUris) {
        return new Oauth2RegisteredClient()
                .setId("test-id")
                .setClientId("test-client")
                .setClientIdIssuedAt(Instant.now())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris(redirectUris)
                .setPostLogoutRedirectUris(postLogoutRedirectUris)
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");
    }
}