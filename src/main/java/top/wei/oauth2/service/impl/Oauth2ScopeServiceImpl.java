package top.wei.oauth2.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.Oauth2ScopeMapper;
import top.wei.oauth2.model.entity.Oauth2Scope;
import top.wei.oauth2.service.Oauth2ScopeService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Oauth2ScopeServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class Oauth2ScopeServiceImpl implements Oauth2ScopeService {

    private final Oauth2ScopeMapper oauth2ScopeMapper;

    @Override
    public List<Oauth2Scope> findByClientIdAndScope(String registeredClientId, Collection<String> scope) {
        if (StringUtils.isNotBlank(registeredClientId) && scope != null && !scope.isEmpty()) {
            return oauth2ScopeMapper.findByClientIdAndScope(registeredClientId, scope);
        }
        return Collections.emptyList();
    }
}
