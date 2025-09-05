package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class Oauth2RegisteredClientServiceTest {

    private final Oauth2RegisteredClientService oauth2RegisteredClientService;

    @Test
    public void testCreateOauth2RegisteredClient() {
        // 准备测试数据
        Oauth2RegisteredClient client = new Oauth2RegisteredClient()
                .setClientId("test-client-" + System.currentTimeMillis())
                .setClientSecret("{noop}test-secret")
                .setClientName("Test Client")
                .setClientAuthenticationMethods("client_secret_basic")
                .setAuthorizationGrantTypes("authorization_code,refresh_token")
                .setRedirectUris("http://localhost:8080/callback")
                .setScopes("read,write")
                .setClientSettings("{}")
                .setTokenSettings("{}");

        // 执行创建操作
        Integer result = oauth2RegisteredClientService.createOauth2RegisteredClient(client);

        // 验证结果
        assertEquals(1, result);
        assertNotNull(client.getId());
        assertNotNull(client.getClientIdIssuedAt());
    }

    @Test
    public void testGetOauth2RegisteredClientById() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        oauth2RegisteredClientService.createOauth2RegisteredClient(client);

        // 执行查询操作
        Oauth2RegisteredClient foundClient = oauth2RegisteredClientService.getOauth2RegisteredClientById(client.getId());

        // 验证结果
        assertNotNull(foundClient);
        assertEquals(client.getClientId(), foundClient.getClientId());
        assertEquals(client.getClientName(), foundClient.getClientName());
    }

    @Test
    public void testGetOauth2RegisteredClientByClientId() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        oauth2RegisteredClientService.createOauth2RegisteredClient(client);

        // 执行查询操作
        Oauth2RegisteredClient foundClient = oauth2RegisteredClientService.getOauth2RegisteredClientByClientId(client.getClientId());

        // 验证结果
        assertNotNull(foundClient);
        assertEquals(client.getId(), foundClient.getId());
        assertEquals(client.getClientName(), foundClient.getClientName());
    }

    @Test
    public void testSelectAllOauth2RegisteredClients() {
        // 准备测试数据
        Oauth2RegisteredClient client1 = createTestClient();
        client1.setClientId("search-test-1-" + System.currentTimeMillis());
        client1.setClientName("Search Test Client 1");
        oauth2RegisteredClientService.createOauth2RegisteredClient(client1);

        Oauth2RegisteredClient client2 = createTestClient();
        client2.setClientId("search-test-2-" + System.currentTimeMillis());
        client2.setClientName("Search Test Client 2");
        oauth2RegisteredClientService.createOauth2RegisteredClient(client2);

        // 执行查询操作 - 无关键词
        PageInfo<Oauth2RegisteredClient> pageInfo = oauth2RegisteredClientService.selectAllOauth2RegisteredClients(1, 10, null);

        // 验证结果
        assertNotNull(pageInfo);
        assertTrue(pageInfo.getList().size() >= 2);

        // 执行查询操作 - 有关键词
        PageInfo<Oauth2RegisteredClient> searchPageInfo = oauth2RegisteredClientService.selectAllOauth2RegisteredClients(1, 10, "search-test");

        // 验证结果
        assertNotNull(searchPageInfo);
        assertTrue(searchPageInfo.getList().size() >= 2);
    }

    @Test
    public void testUpdateOauth2RegisteredClient() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        oauth2RegisteredClientService.createOauth2RegisteredClient(client);

        // 修改数据
        String newClientName = "Updated Test Client";
        client.setClientName(newClientName);

        // 执行更新操作
        Integer result = oauth2RegisteredClientService.updateOauth2RegisteredClient(client);

        // 验证结果
        assertEquals(1, result);

        // 查询验证更新
        Oauth2RegisteredClient updatedClient = oauth2RegisteredClientService.getOauth2RegisteredClientById(client.getId());
        assertEquals(newClientName, updatedClient.getClientName());
    }

    @Test
    public void testDeleteOauth2RegisteredClient() {
        // 准备测试数据
        Oauth2RegisteredClient client = createTestClient();
        oauth2RegisteredClientService.createOauth2RegisteredClient(client);

        // 确认客户端存在
        Oauth2RegisteredClient foundClient = oauth2RegisteredClientService.getOauth2RegisteredClientById(client.getId());
        assertNotNull(foundClient);

        // 执行删除操作
        Integer result = oauth2RegisteredClientService.deleteOauth2RegisteredClient(client.getId());

        // 验证结果
        assertEquals(1, result);

        // 确认客户端已删除
        Oauth2RegisteredClient deletedClient = oauth2RegisteredClientService.getOauth2RegisteredClientById(client.getId());
        assertNull(deletedClient);
    }

    private Oauth2RegisteredClient createTestClient() {
        return new Oauth2RegisteredClient()
                .setId(UUID.randomUUID().toString())
                .setClientId("test-client-" + System.currentTimeMillis())
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
}