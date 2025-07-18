package top.wei.oauth2.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import top.wei.oauth2.model.entity.Oauth2Scope;

import java.util.Set;

/**
 * AuthorizationConsentInfo.
 */
@Data
@Accessors(chain = true)
public class AuthorizationConsentInfoVO {

    private String authorizationEndpoint;

    private String clientId;

    private String clientName;

    private String state;

    private Set<Oauth2Scope> scopes;

    private Set<Oauth2Scope> previouslyApprovedScopes;

    private String principalName;

}
