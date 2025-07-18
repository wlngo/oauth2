package top.wei.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.vo.AuthorizationConsentInfoVO;
import top.wei.oauth2.service.Oauth2ScopeService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.security.Principal;

/**
 * 授权同意控制器.
 */
@RestController
@RequiredArgsConstructor
public class AuthorizationConsentController {

    private final Oauth2ScopeService oauth2ScopeService;

    /**
     * 根据客户端ID和范围查询授权范围.
     *
     * @param principal 当前用户
     * @param clientId  clientId
     * @param scope     scope
     * @param state     state
     * @return list Oauth2Scope
     */
    @GetMapping("/oauth2/consent/scope")
    public Rest<AuthorizationConsentInfoVO> findByClientIdAndScope(Principal principal, String clientId, String scope, String state) {
        return RestBody.okData(oauth2ScopeService.findByClientIdAndScope(principal, clientId, scope, state));
    }
}
