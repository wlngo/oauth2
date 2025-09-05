package top.wei.oauth2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import top.wei.oauth2.service.Oauth2RegisteredClientService;

/**
 * Oauth2RegisteredClientControllerTest.
 */
@WebMvcTest(Oauth2RegisteredClientController.class)
public class Oauth2RegisteredClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Oauth2RegisteredClientService oauth2RegisteredClientService;

    @Test
    public void contextLoads() {
        // This test just verifies that the Spring context loads with our new controller
        // and that the controller can be instantiated without errors
    }
}