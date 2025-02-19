package top.wei.oauth2.configure.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.wei.oauth2.mapper.Oauth2AuthorizationConsentMapper;
import top.wei.oauth2.model.entity.Oauth2AuthorizationConsent;
import top.wei.oauth2.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private final Oauth2AuthorizationConsentMapper oauth2AuthorizationConsentMapper;

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        OAuth2AuthorizationConsent existingAuthorizationConsent = findById(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
        if (Objects.isNull(existingAuthorizationConsent)) {
            Oauth2AuthorizationConsent oauth2AuthorizationConsent = ConvertUtils.springOauth2AuthorizationConsentToOauth2AuthorizationConsent(authorizationConsent);
            oauth2AuthorizationConsentMapper.insert(oauth2AuthorizationConsent);
        } else {
            String authorities = StringUtils.collectionToDelimitedString(authorizationConsent.getAuthorities(), ",");
            oauth2AuthorizationConsentMapper.update(new UpdateWrapper<Oauth2AuthorizationConsent>()
                    .set("authorities", authorities)
                    .eq("registered_client_id", authorizationConsent.getRegisteredClientId())
                    .eq("principal_name", authorizationConsent.getPrincipalName()));
        }
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        oauth2AuthorizationConsentMapper.delete(new QueryWrapper<Oauth2AuthorizationConsent>()
                .eq("registered_client_id", authorizationConsent.getRegisteredClientId())
                .eq("principal_name", authorizationConsent.getPrincipalName()));
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        List<Oauth2AuthorizationConsent> res = oauth2AuthorizationConsentMapper.selectList(new QueryWrapper<Oauth2AuthorizationConsent>()
                .eq("registered_client_id", registeredClientId)
                .eq("principal_name", principalName));
        ArrayList<OAuth2AuthorizationConsent> result = new ArrayList<>();
        res.forEach(oauth2AuthorizationConsent ->
                result.add(ConvertUtils.oauth2AuthorizationConsentToSpringOauth2AuthorizationConsent(oauth2AuthorizationConsent)));

        return !result.isEmpty() ? result.get(0) : null;
    }
}
