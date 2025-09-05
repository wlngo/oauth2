package top.wei.oauth2.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Oauth2RegisteredClientControllerTest.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class Oauth2RegisteredClientControllerTest {

    @Mock
    private Oauth2RegisteredClientService oauth2RegisteredClientService;

    @InjectMocks
    private Oauth2RegisteredClientController oauth2RegisteredClientController;

    @Test
    void testCreateOauth2RegisteredClient() {
        // Given
        Oauth2RegisteredClient client = new Oauth2RegisteredClient();
        client.setId("test-id");
        client.setClientId("test-client");
        client.setClientName("Test Client");

        when(oauth2RegisteredClientService.createOauth2RegisteredClient(any())).thenReturn(1);

        // When
        Rest<Integer> result = oauth2RegisteredClientController.createOauth2RegisteredClient(client);

        // Then
        assertNotNull(result);
        assertEquals(1, ((RestBody<Integer>) result).getData());
        verify(oauth2RegisteredClientService, times(1)).createOauth2RegisteredClient(client);
    }

    @Test
    void testGetOauth2RegisteredClientById() {
        // Given
        String testId = "test-id";
        Oauth2RegisteredClient client = new Oauth2RegisteredClient();
        client.setId(testId);
        client.setClientId("test-client");

        when(oauth2RegisteredClientService.getOauth2RegisteredClientById(testId)).thenReturn(client);

        // When
        Rest<Oauth2RegisteredClient> result = oauth2RegisteredClientController.getOauth2RegisteredClientById(testId);

        // Then
        assertNotNull(result);
        assertNotNull(((RestBody<Oauth2RegisteredClient>) result).getData());
        assertEquals(testId, ((RestBody<Oauth2RegisteredClient>) result).getData().getId());
        verify(oauth2RegisteredClientService, times(1)).getOauth2RegisteredClientById(testId);
    }

    @Test
    void testUpdateOauth2RegisteredClient() {
        // Given
        Oauth2RegisteredClient client = new Oauth2RegisteredClient();
        client.setId("test-id");
        client.setClientName("Updated Test Client");

        when(oauth2RegisteredClientService.updateOauth2RegisteredClient(any())).thenReturn(1);

        // When
        Rest<Integer> result = oauth2RegisteredClientController.updateOauth2RegisteredClient(client);

        // Then
        assertNotNull(result);
        assertEquals(1, ((RestBody<Integer>) result).getData());
        verify(oauth2RegisteredClientService, times(1)).updateOauth2RegisteredClient(client);
    }

    @Test
    void testDeleteOauth2RegisteredClient() {
        // Given
        String testId = "test-id";

        when(oauth2RegisteredClientService.deleteOauth2RegisteredClient(testId)).thenReturn(1);

        // When
        Rest<Integer> result = oauth2RegisteredClientController.deleteOauth2RegisteredClient(testId);

        // Then
        assertNotNull(result);
        assertEquals(1, ((RestBody<Integer>) result).getData());
        verify(oauth2RegisteredClientService, times(1)).deleteOauth2RegisteredClient(testId);
    }
}