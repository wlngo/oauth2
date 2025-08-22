package top.wei.oauth2.security.oauth2.authentication.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.wei.oauth2.mapper.Oauth2RegisteredClientMapper;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.utils.ConvertUtils;

import java.util.List;


/**
 * RegisteredClientRepositoryImpl.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {


    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        boolean exists = oauth2RegisteredClientMapper.exists(new QueryWrapper<Oauth2RegisteredClient>().eq("id", registeredClient.getId()));
        Oauth2RegisteredClient oauth2RegisteredClient = ConvertUtils.springOauth2RegisteredClientToOauth2RegisteredClient(registeredClient);
        if (exists) {
            oauth2RegisteredClient.setClientId(null)
                    .setClientIdIssuedAt(null);
            oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);
        } else {
            oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        List<Oauth2RegisteredClient> oauth2RegisteredClient = oauth2RegisteredClientMapper.selectList(new QueryWrapper<Oauth2RegisteredClient>().eq("id", id));
        return !oauth2RegisteredClient.isEmpty() ? ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2RegisteredClient.get(0)) : null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        List<Oauth2RegisteredClient> oauth2RegisteredClient = oauth2RegisteredClientMapper.selectList(new QueryWrapper<Oauth2RegisteredClient>().eq("client_id", clientId));
        return !oauth2RegisteredClient.isEmpty() ? ConvertUtils.oauth2RegisteredClientToSpringOauth2RegisteredClient(oauth2RegisteredClient.get(0)) : null;
    }


}
