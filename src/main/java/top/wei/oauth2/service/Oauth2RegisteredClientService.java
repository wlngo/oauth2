package top.wei.oauth2.service;

import com.github.pagehelper.PageInfo;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;

/**
 * Oauth2RegisteredClientService.
 */
public interface Oauth2RegisteredClientService {

    /**
     * 创建OAuth2注册客户端.
     *
     * @param oauth2RegisteredClient oauth2RegisteredClient
     * @return rows
     */
    Integer createOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient);

    /**
     * 根据ID获取OAuth2注册客户端.
     *
     * @param id id
     * @return Oauth2RegisteredClient
     */
    Oauth2RegisteredClient getOauth2RegisteredClientById(String id);

    /**
     * 查询所有OAuth2注册客户端.
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param keyword  keyword
     * @return PageInfo Oauth2RegisteredClient
     */
    PageInfo<Oauth2RegisteredClient> selectAllOauth2RegisteredClients(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新OAuth2注册客户端.
     *
     * @param oauth2RegisteredClient oauth2RegisteredClient
     * @return rows
     */
    Integer updateOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient);

    /**
     * 删除OAuth2注册客户端.
     *
     * @param id id
     * @return rows
     */
    Integer deleteOauth2RegisteredClient(String id);
}