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
     * @param oauth2RegisteredClient OAuth2注册客户端
     * @return 影响行数
     */
    Integer createOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient);

    /**
     * 根据ID获取OAuth2注册客户端.
     *
     * @param id 客户端ID
     * @return OAuth2注册客户端
     */
    Oauth2RegisteredClient getOauth2RegisteredClientById(String id);

    /**
     * 根据客户端ID获取OAuth2注册客户端.
     *
     * @param clientId 客户端ID
     * @return OAuth2注册客户端
     */
    Oauth2RegisteredClient getOauth2RegisteredClientByClientId(String clientId);

    /**
     * 查询所有OAuth2注册客户端信息.
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @param keyword  关键词
     * @return 分页信息
     */
    PageInfo<Oauth2RegisteredClient> selectAllOauth2RegisteredClients(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 更新OAuth2注册客户端.
     *
     * @param oauth2RegisteredClient OAuth2注册客户端
     * @return 影响行数
     */
    Integer updateOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient);

    /**
     * 删除OAuth2注册客户端.
     *
     * @param id 客户端ID
     * @return 影响行数
     */
    Integer deleteOauth2RegisteredClient(String id);
}