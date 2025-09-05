package top.wei.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Oauth2RegisteredClientServiceTest.
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ActiveProfiles("test")
public class Oauth2RegisteredClientServiceTest {

    private final Oauth2RegisteredClientService oauth2RegisteredClientService;

    @Test
    void testServiceIsLoaded() {
        // Test that service is properly injected and Spring context loads
        assertNotNull(oauth2RegisteredClientService);
    }

    @Test 
    void testServiceMethods() {
        // Test basic service functionality - just verify methods exist
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
        
        // These methods should exist and be callable
        assertNotNull(oauth2RegisteredClientService);
        
        // Test method signatures exist
        try {
            oauth2RegisteredClientService.getClass().getMethod("createOauth2RegisteredClient", Oauth2RegisteredClient.class);
            oauth2RegisteredClientService.getClass().getMethod("getOauth2RegisteredClientById", String.class);
            oauth2RegisteredClientService.getClass().getMethod("selectAllOauth2RegisteredClients", Integer.class, Integer.class, String.class);
            oauth2RegisteredClientService.getClass().getMethod("updateOauth2RegisteredClient", Oauth2RegisteredClient.class);
            oauth2RegisteredClientService.getClass().getMethod("deleteOauth2RegisteredClient", String.class);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Required service methods are missing", e);
        }
    }
}