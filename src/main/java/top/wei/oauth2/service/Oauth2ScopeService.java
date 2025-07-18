package top.wei.oauth2.service;

import top.wei.oauth2.model.vo.AuthorizationConsentInfoVO;

import java.security.Principal;

/**
 * Oauth2ScopeService.
 */
public interface Oauth2ScopeService {

    /**
     * Find by client id and scope list.
     *
     * @param principal          the principal
     * @param registeredClientId the registeredClient id
     * @param scope              the scopes
     * @param state              the state
     * @return the list
     */
    AuthorizationConsentInfoVO findByClientIdAndScope(Principal principal, String registeredClientId, String scope, String state);

}
