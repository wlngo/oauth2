package top.wei.oauth2.security.oauth2.authorization;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.wei.oauth2.mapper.Oauth2AuthorizationMapper;
import top.wei.oauth2.model.entity.Oauth2Authorization;
import top.wei.oauth2.utils.ConvertUtils;

import java.util.List;


/**
 * impl 负责管理 OAuth 2.0 Authorization(s).
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthorizationServiceImpl implements OAuth2AuthorizationService {


    private final Oauth2AuthorizationMapper oauth2AuthorizationMapper;

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        boolean exists = oauth2AuthorizationMapper.exists(new QueryWrapper<Oauth2Authorization>().eq("id", authorization.getId()));
        Oauth2Authorization oauth2Authorization = ConvertUtils.springOauth2AuthorizationToOauth2Authorization(authorization);
        if (exists) {
            oauth2AuthorizationMapper.updateById(oauth2Authorization);
        } else {
            oauth2AuthorizationMapper.insert(oauth2Authorization);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        oauth2AuthorizationMapper.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be null");
        List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>().eq("id", id));
        return findBy(oauth2AuthorizationList);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be null");
        String tokenSha265 = DigestUtils.sha256Hex(token);
        if (tokenType == null) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.findByTokenType(tokenSha265);
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("state_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("authorization_code_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("access_token_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("oidc_id_token_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("refresh_token_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("user_code_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            List<Oauth2Authorization> oauth2AuthorizationList = oauth2AuthorizationMapper.selectList(new QueryWrapper<Oauth2Authorization>()
                    .eq("device_code_value_index_sha256", tokenSha265));
            return findBy(oauth2AuthorizationList);
        }
        return null;
    }

    public OAuth2Authorization findBy(List<Oauth2Authorization> oauth2AuthorizationList) {

        Oauth2Authorization oauth2Authorization = !oauth2AuthorizationList.isEmpty() ? oauth2AuthorizationList.getFirst() : null;
        OAuth2Authorization result = null;
        if (oauth2Authorization != null) {
            String registeredClientId = oauth2Authorization.getRegisteredClientId();
            RegisteredClient registeredClient = registeredClientRepository.findById(registeredClientId);
            if (registeredClient == null) {
                throw new DataRetrievalFailureException(
                        "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
            }
            result = ConvertUtils.oAuth2AuthorizationToSpringOauth2Authorization(oauth2Authorization, registeredClient);

        }
        return result;
    }
}
