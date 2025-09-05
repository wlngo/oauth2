package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.wei.oauth2.mapper.Oauth2RegisteredClientMapper;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Oauth2RegisteredClientServiceImpl.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2RegisteredClientServiceImpl implements Oauth2RegisteredClientService {

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    @Override
    public Integer createOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        if (oauth2RegisteredClient.getId() == null) {
            oauth2RegisteredClient.setId(UUID.randomUUID().toString());
        }
        if (oauth2RegisteredClient.getClientIdIssuedAt() == null) {
            oauth2RegisteredClient.setClientIdIssuedAt(Instant.now());
        }
        oauth2RegisteredClient.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(oauth2RegisteredClient.getClientSecret()));
        return oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
    }

    @Override
    public Oauth2RegisteredClient getOauth2RegisteredClientById(String id) {
        return oauth2RegisteredClientMapper.selectById(id);
    }

    @Override
    public Oauth2RegisteredClient getOauth2RegisteredClientByClientId(String clientId) {
        QueryWrapper<Oauth2RegisteredClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", clientId);
        List<Oauth2RegisteredClient> clients = oauth2RegisteredClientMapper.selectList(queryWrapper);
        return clients.isEmpty() ? null : clients.get(0);
    }

    @Override
    public PageInfo<Oauth2RegisteredClient> selectAllOauth2RegisteredClients(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Oauth2RegisteredClient> queryWrapper = new QueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like("client_id", keyword)
                .or()
                .like("client_name", keyword));
        }
        
        List<Oauth2RegisteredClient> clients = oauth2RegisteredClientMapper.selectList(queryWrapper);
        return new PageInfo<>(clients);
    }

    @Override
    public Integer updateOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        // Prevent updating client secret here
        oauth2RegisteredClient.setClientSecret(null);
        return oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);
    }

    @Override
    public Integer deleteOauth2RegisteredClient(String id) {
        return oauth2RegisteredClientMapper.deleteById(id);
    }
}