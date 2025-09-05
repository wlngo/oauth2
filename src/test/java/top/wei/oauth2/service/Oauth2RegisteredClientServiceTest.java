package top.wei.oauth2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import top.wei.oauth2.mapper.Oauth2RegisteredClientMapper;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.impl.Oauth2RegisteredClientServiceImpl;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Oauth2RegisteredClientServiceTest.
 */
public class Oauth2RegisteredClientServiceTest {

    @Mock
    private Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    private Oauth2RegisteredClientService oauth2RegisteredClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        oauth2RegisteredClientService = new Oauth2RegisteredClientServiceImpl(oauth2RegisteredClientMapper);
    }

    @Test
    void testServiceIsCreated() {
        // Test that service is properly created
        assertNotNull(oauth2RegisteredClientService);
    }

    @Test 
    void testServiceMethods() {
        // Test basic service functionality - just verify methods exist and can be called
        Oauth2RegisteredClient client = new Oauth2RegisteredClient();
        client.setId("test-id");
        client.setClientId("test-client");
        client.setClientName("Test Client");
        client.setClientSecret("test-secret");
        client.setClientIdIssuedAt(Instant.now());
        client.setClientAuthenticationMethods("client_secret_basic");
        client.setAuthorizationGrantTypes("authorization_code");
        client.setRedirectUris("http://localhost:8080/callback");
        client.setScopes("read,write");
        client.setClientSettings("{}");
        client.setTokenSettings("{}");
        
        // Mock the mapper calls
        when(oauth2RegisteredClientMapper.insert(any(Oauth2RegisteredClient.class))).thenReturn(1);
        when(oauth2RegisteredClientMapper.selectById("test-id")).thenReturn(client);
        when(oauth2RegisteredClientMapper.updateById(any(Oauth2RegisteredClient.class))).thenReturn(1);
        when(oauth2RegisteredClientMapper.deleteById("test-id")).thenReturn(1);
        
        // Test method signatures exist and basic functionality
        try {
            oauth2RegisteredClientService.getClass().getMethod("createOauth2RegisteredClient", Oauth2RegisteredClient.class);
            oauth2RegisteredClientService.getClass().getMethod("getOauth2RegisteredClientById", String.class);
            oauth2RegisteredClientService.getClass().getMethod("selectAllOauth2RegisteredClients", Integer.class, Integer.class, String.class);
            oauth2RegisteredClientService.getClass().getMethod("updateOauth2RegisteredClient", Oauth2RegisteredClient.class);
            oauth2RegisteredClientService.getClass().getMethod("deleteOauth2RegisteredClient", String.class);
            
            // Test basic method calls
            assertNotNull(oauth2RegisteredClientService.createOauth2RegisteredClient(client));
            assertNotNull(oauth2RegisteredClientService.getOauth2RegisteredClientById("test-id"));
            assertNotNull(oauth2RegisteredClientService.selectAllOauth2RegisteredClients(1, 10, null));
            assertNotNull(oauth2RegisteredClientService.updateOauth2RegisteredClient(client));
            assertNotNull(oauth2RegisteredClientService.deleteOauth2RegisteredClient("test-id"));
            
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Required service methods are missing", e);
        }
    }
}