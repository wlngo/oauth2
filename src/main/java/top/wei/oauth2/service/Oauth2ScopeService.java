package top.wei.oauth2.service;

import top.wei.oauth2.model.entity.Oauth2Scope;

import java.util.Collection;
import java.util.List;

/**
 * Oauth2ScopeService.
 */
public interface Oauth2ScopeService {

    /**
     * Find by client id and scope list.
     *
     * @param registeredClientId the registeredClient id
     * @param scope    the scope
     * @return the list
     */
    List<Oauth2Scope> findByClientIdAndScope(String registeredClientId, Collection<String> scope);

}
