package top.wei.oauth2.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import top.wei.oauth2.mapper.Oauth2ScopeMapper;
import top.wei.oauth2.model.entity.Oauth2Scope;
import top.wei.oauth2.model.vo.AuthorizationConsentInfoVO;
import top.wei.oauth2.service.Oauth2ScopeService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Oauth2ScopeServiceImpl.
 */
@Service
@RequiredArgsConstructor
public class Oauth2ScopeServiceImpl implements Oauth2ScopeService {

    private final Oauth2ScopeMapper oauth2ScopeMapper;

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2AuthorizationConsentService authorizationConsentService;

    private final AuthorizationServerSettings authorizationServerSettings;

    public AuthorizationConsentInfoVO findByClientIdAndScope(HttpServletRequest request, Principal principal, String clientId, String scope, String state) {
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        assert registeredClient != null;
        String id = registeredClient.getId();

        OAuth2AuthorizationConsent currentAuthorizationConsent = authorizationConsentService.findById(id, principal.getName());
        Set<String> authorizedScopes = currentAuthorizationConsent != null ? currentAuthorizationConsent.getScopes() : Collections.emptySet();

        List<String> scopeList = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).toList();
        List<Oauth2Scope> oAuth2Scopes = oauth2ScopeMapper.findByClientIdAndScope(id, scopeList);
        Set<Oauth2Scope> scopesToApproves = new HashSet<>();
        Set<Oauth2Scope> previouslyApprovedScopesSet = new HashSet<>();

        oAuth2Scopes.forEach(oAuth2Scope -> {
            if (authorizedScopes.contains(oAuth2Scope.getScope())) {
                previouslyApprovedScopesSet.add(oAuth2Scope);
            } else {
                scopesToApproves.add(oAuth2Scope);
            }
        });
        String issuer = resolve(request);
        AuthorizationConsentInfoVO info = new AuthorizationConsentInfoVO();
        info.setAuthorizationEndpoint(issuer + authorizationServerSettings.getAuthorizationEndpoint());
        info.setClientId(clientId);
        info.setClientName(registeredClient.getClientName());
        info.setState(state);
        info.setScopes(scopesToApproves);
        info.setPreviouslyApprovedScopes(previouslyApprovedScopesSet);
        info.setPrincipalName(principal.getName());

        return info;
    }

    private String resolve(HttpServletRequest request) {

        // Resolve Issuer Identifier dynamically from request
        String path = request.getRequestURI();
        if (!StringUtils.hasText(path)) {
            path = "";
        }
        // @formatter:off
        return UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(path)
                .replaceQuery(null)
                .fragment(null)
                .build()
                .toUriString();
        // @formatter:on
    }
}
