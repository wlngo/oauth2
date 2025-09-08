package top.wei.oauth2;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import top.wei.oauth2.controller.Oauth2RegisteredClientController;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Oauth2RegisteredClientControllerTest {

    @Mock
    private Oauth2RegisteredClientService oauth2RegisteredClientService;

    @InjectMocks
    private Oauth2RegisteredClientController oauth2RegisteredClientController;

    @Test
    public void testCreateClient() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.createOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.createClient(client);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
    }

    @Test
    public void testFindById() {
        // 准备测试数据
        String clientId = "test-id";
        Oauth2RegisteredClient client = createTestClient();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.getOauth2RegisteredClientById(anyString())).thenReturn(client);

        // 执行操作
        Rest<Oauth2RegisteredClient> result = oauth2RegisteredClientController.findById(clientId);

        // 验证结果
        assertNotNull(result);
        assertNotNull(((RestBody<Oauth2RegisteredClient>) result).getData());
        assertEquals(client.getClientId(), ((RestBody<Oauth2RegisteredClient>) result).getData().getClientId());
    }

    @Test
    public void testUpdateClient() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.updateOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.updateClient(client);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
    }

    @Test
    public void testDeleteClient() {
        // 准备测试数据
        String clientId = "test-id";
        
        // Mock service behavior
        when(oauth2RegisteredClientService.deleteOauth2RegisteredClient(anyString())).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.deleteClient(clientId);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
    }

    @Test
    public void testCreateClientWithMultipleRedirectUris() {
        // 准备测试数据 - 包含多个重定向URI
        Oauth2RegisteredClient client = createTestClientWithMultipleUris();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.createOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.createClient(client);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
        
        // 验证多个重定向URI格式正确
        assertEquals("http://localhost:8080/callback,http://localhost:8080/callback2,https://example.com/oauth/callback", 
                     client.getRedirectUris());
    }

    @Test
    public void testCreateClientWithMultiplePostLogoutRedirectUris() {
        // 准备测试数据 - 包含多个登出重定向URI
        Oauth2RegisteredClient client = createTestClientWithMultiplePostLogoutUris();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.createOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.createClient(client);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
        
        // 验证多个登出重定向URI格式正确
        assertEquals("http://localhost:8080/logout,http://localhost:8080/goodbye,https://example.com/logout", 
                     client.getPostLogoutRedirectUris());
    }

    @Test
    public void testUpdateClientWithMultipleUris() {
        // 准备测试数据 - 同时包含多个重定向URI和登出重定向URI
        Oauth2RegisteredClient client = createTestClientWithBothMultipleUris();
        
        // Mock service behavior
        when(oauth2RegisteredClientService.updateOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);

        // 执行操作
        Rest<Integer> result = oauth2RegisteredClientController.updateClient(client);

        // 验证结果
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), ((RestBody<Integer>) result).getData());
        
        // 验证两种URI都正确设置
        assertEquals("http://localhost:8080/callback,https://app.example.com/auth/callback", 
                     client.getRedirectUris());
        assertEquals("http://localhost:8080/logout,https://app.example.com/logout", 
                     client.getPostLogoutRedirectUris());
    }

    private Oauth2RegisteredClient createTestClient() {
        return new Oauth2RegisteredClient()
                .setId("test-id")
                .setClientId("test-client")
                .setClientIdIssuedAt(Instant.now())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris("http://localhost:8080/callback")
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");
    }

    private Oauth2RegisteredClient createTestClientWithMultipleUris() {
        return new Oauth2RegisteredClient()
                .setId("test-id-multiple")
                .setClientId("test-client-multiple")
                .setClientIdIssuedAt(Instant.now())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client Multiple URIs")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris("http://localhost:8080/callback,http://localhost:8080/callback2,https://example.com/oauth/callback")
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");
    }

    private Oauth2RegisteredClient createTestClientWithMultiplePostLogoutUris() {
        return new Oauth2RegisteredClient()
                .setId("test-id-logout")
                .setClientId("test-client-logout")
                .setClientIdIssuedAt(Instant.now())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client Multiple Logout URIs")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris("http://localhost:8080/callback")
                .setPostLogoutRedirectUris("http://localhost:8080/logout,http://localhost:8080/goodbye,https://example.com/logout")
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");
    }

    private Oauth2RegisteredClient createTestClientWithBothMultipleUris() {
        return new Oauth2RegisteredClient()
                .setId("test-id-both")
                .setClientId("test-client-both")
                .setClientIdIssuedAt(Instant.now())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client Both Multiple URIs")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris("http://localhost:8080/callback,https://app.example.com/auth/callback")
                .setPostLogoutRedirectUris("http://localhost:8080/logout,https://app.example.com/logout")
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");
    }
}