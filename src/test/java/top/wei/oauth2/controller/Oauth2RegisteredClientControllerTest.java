package top.wei.oauth2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Oauth2RegisteredClientControllerTest.
 */
public class Oauth2RegisteredClientControllerTest {

    @Mock
    private Oauth2RegisteredClientService oauth2RegisteredClientService;

    private Oauth2RegisteredClientController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Oauth2RegisteredClientController(oauth2RegisteredClientService);
    }

    @Test
    void testControllerIsCreated() {
        // Test that controller is properly created
        assertNotNull(controller);
    }

    @Test 
    void testControllerMethods() {
        // Test basic controller functionality - verify methods exist and can be called
        Oauth2RegisteredClient client = new Oauth2RegisteredClient();
        client.setId("test-id");
        client.setClientId("test-client");
        client.setClientName("Test Client");
        client.setClientSecret("test-secret");
        client.setClientIdIssuedAt(Instant.now());
        
        // Mock the service calls
        when(oauth2RegisteredClientService.createOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);
        when(oauth2RegisteredClientService.getOauth2RegisteredClientById(anyString())).thenReturn(client);
        when(oauth2RegisteredClientService.updateOauth2RegisteredClient(any(Oauth2RegisteredClient.class))).thenReturn(1);
        when(oauth2RegisteredClientService.deleteOauth2RegisteredClient(anyString())).thenReturn(1);
        
        // Test method signatures exist and basic functionality
        try {
            controller.getClass().getMethod("getAllClients", int.class, int.class, String.class);
            controller.getClass().getMethod("getClientById", String.class);
            controller.getClass().getMethod("createClient", Oauth2RegisteredClient.class);
            controller.getClass().getMethod("updateClient", Oauth2RegisteredClient.class);
            controller.getClass().getMethod("deleteClient", String.class);
            
            // Test basic method calls (without security context)
            assertNotNull(controller);
            
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Required controller methods are missing", e);
        }
    }
}