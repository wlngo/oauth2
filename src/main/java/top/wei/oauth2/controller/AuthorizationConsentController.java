package top.wei.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.entity.Oauth2Scope;
import top.wei.oauth2.service.Oauth2ScopeService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.util.Arrays;
import java.util.List;

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
     * @param clientId clientId
     * @param scope    scope
     * @return list Oauth2Scope
     */
    @GetMapping("/oauth2/consent/scope")
    public Rest<List<Oauth2Scope>> findByClientIdAndScope(String clientId, String scope) {
        List<String> scopeList = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).toList();
        return RestBody.okData(oauth2ScopeService.findByClientIdAndScope(clientId, scopeList));
    }
}
