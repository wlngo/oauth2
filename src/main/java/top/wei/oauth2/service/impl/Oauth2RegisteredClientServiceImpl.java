package top.wei.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.Oauth2RegisteredClientMapper;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;
import top.wei.oauth2.service.Oauth2RegisteredClientService;

import java.time.Instant;
import java.util.List;

/**
 * Oauth2RegisteredClientServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class Oauth2RegisteredClientServiceImpl implements Oauth2RegisteredClientService {

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    @Override
    public Integer createOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        if (oauth2RegisteredClient.getClientIdIssuedAt() == null) {
            oauth2RegisteredClient.setClientIdIssuedAt(Instant.now());
        }
        return oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
    }

    @Override
    public Oauth2RegisteredClient getOauth2RegisteredClientById(String id) {
        return oauth2RegisteredClientMapper.selectById(id);
    }

    @Override
    public PageInfo<Oauth2RegisteredClient> selectAllOauth2RegisteredClients(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Oauth2RegisteredClient> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper.like("client_id", keyword)
                    .or().like("client_name", keyword)
                    .or().like("scopes", keyword));
        }
        queryWrapper.orderByDesc("client_id_issued_at");
        List<Oauth2RegisteredClient> clients = oauth2RegisteredClientMapper.selectList(queryWrapper);
        return new PageInfo<>(clients);
    }

    @Override
    public Integer updateOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        return oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);
    }

    @Override
    public Integer deleteOauth2RegisteredClient(String id) {
        return oauth2RegisteredClientMapper.deleteById(id);
    }
}