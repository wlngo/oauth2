package top.wei.oauth2.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

/**
 * Oauth2RegisteredClientController.
 */
@RestController
@RequestMapping("/api/oauth2/registered-clients")
@RequiredArgsConstructor
public class Oauth2RegisteredClientController {

    private final Oauth2RegisteredClientService oauth2RegisteredClientService;

    /**
     * 获取所有OAuth2注册客户端.
     *
     * @param page    page
     * @param size    size
     * @param keyword keyword
     * @return clients
     */
    @PostMapping("/getAllClients")
    @PreAuthorize("hasAuthority('oauth2:client:view')")
    public Rest<PageInfo<Oauth2RegisteredClient>> getAllClients(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String keyword) {
        return RestBody.okData(oauth2RegisteredClientService.selectAllOauth2RegisteredClients(page, size, keyword));
    }

    /**
     * 根据ID获取OAuth2注册客户端.
     *
     * @param id id
     * @return client
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('oauth2:client:view')")
    public Rest<Oauth2RegisteredClient> getClientById(@PathVariable String id) {
        return RestBody.okData(oauth2RegisteredClientService.getOauth2RegisteredClientById(id));
    }

    /**
     * 创建OAuth2注册客户端.
     *
     * @param oauth2RegisteredClient oauth2RegisteredClient
     * @return rows
     */
    @PostMapping("/createClient")
    @PreAuthorize("hasAuthority('oauth2:client:create')")
    public Rest<Integer> createClient(@RequestBody Oauth2RegisteredClient oauth2RegisteredClient) {
        return RestBody.okData(oauth2RegisteredClientService.createOauth2RegisteredClient(oauth2RegisteredClient));
    }

    /**
     * 更新OAuth2注册客户端.
     *
     * @param oauth2RegisteredClient oauth2RegisteredClient
     * @return rows
     */
    @PostMapping("/updateClient")
    @PreAuthorize("hasAuthority('oauth2:client:update')")
    public Rest<Integer> updateClient(@RequestBody Oauth2RegisteredClient oauth2RegisteredClient) {
        return RestBody.okData(oauth2RegisteredClientService.updateOauth2RegisteredClient(oauth2RegisteredClient));
    }

    /**
     * 删除OAuth2注册客户端.
     *
     * @param id id
     * @return rows
     */
    @DeleteMapping("/deleteClient/{id}")
    @PreAuthorize("hasAuthority('oauth2:client:delete')")
    public Rest<Integer> deleteClient(@PathVariable String id) {
        return RestBody.okData(oauth2RegisteredClientService.deleteOauth2RegisteredClient(id));
    }
}