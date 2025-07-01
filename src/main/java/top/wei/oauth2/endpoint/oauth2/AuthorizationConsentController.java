//package top.wei.oauth2.endpoint.oauth2;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import top.wei.oauth2.model.entity.OAuth2Scope;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * 自定义用户确认页.
// */
//@Controller
//@RequiredArgsConstructor
//public class AuthorizationConsentController {
//    private final RegisteredClientRepository registeredClientRepository;
//
//    private final OAuth2AuthorizationConsentService authorizationConsentService;
//
//    private final AuthorizationServerSettings providerSettings;
//
//    /**
//     * {@link OAuth2AuthorizationEndpointFilter} 会302重定向到{@code  /oauth2/consent}并携带入参.
//     *
//     * @param principal 当前用户
//     * @param model     视图模型
//     * @param clientId  oauth2 client id
//     * @param scope     请求授权的scope
//     * @param state     state 值
//     * @param request   request
//     * @return 自定义授权确认页面 consent.html
//     */
//    @GetMapping(value = "/oauth2/consent")
//    public String consent(HttpServletRequest request, Principal principal, Model model,
//                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
//                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
//                          @RequestParam(OAuth2ParameterNames.STATE) String state) {
//
//        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
//        assert registeredClient != null;
//        OAuth2Scope oAuth2Scope1 = new OAuth2Scope();
//        oAuth2Scope1.setScope("openid");
//        oAuth2Scope1.setDescription("openid");
//        oAuth2Scope1.setClientId("oidc-client");
//
//        OAuth2Scope oAuth2Scope2 = new OAuth2Scope();
//        oAuth2Scope2.setScope("profile");
//        oAuth2Scope2.setDescription("profile");
//        OAuth2Scope oAuth2Scope3 = new OAuth2Scope();
//        oAuth2Scope3.setScope("email");
//        oAuth2Scope3.setDescription("email");
//        OAuth2Scope oAuth2Scope4 = new OAuth2Scope();
//        oAuth2Scope4.setScope("address");
//        oAuth2Scope4.setDescription("address");
//        OAuth2Scope oAuth2Scope5 = new OAuth2Scope();
//        oAuth2Scope5.setScope("message.read");
//        oAuth2Scope5.setDescription("message.read");
//
//        oAuth2Scope2.setClientId("oidc-client");
//        oAuth2Scope3.setClientId("oidc-client");
//        oAuth2Scope4.setClientId("oidc-client");
//        oAuth2Scope5.setClientId("oidc-client");
//        List<OAuth2Scope> oAuth2Scopes = new ArrayList<>();
//        oAuth2Scopes.add(oAuth2Scope1);
//        oAuth2Scopes.add(oAuth2Scope2);
//        oAuth2Scopes.add(oAuth2Scope3);
//        oAuth2Scopes.add(oAuth2Scope4);
//        oAuth2Scopes.add(oAuth2Scope5);
//
//        String id = registeredClient.getId();
//        OAuth2AuthorizationConsent currentAuthorizationConsent =
//                this.authorizationConsentService.findById(id, principal.getName());
//
//        Set<String> authorizedScopes = currentAuthorizationConsent != null ? currentAuthorizationConsent.getScopes() : Collections.emptySet();
//        Set<OAuth2Scope> scopesToApproves = new HashSet<>();
//        Set<OAuth2Scope> previouslyApprovedScopesSet = new HashSet<>();
//        oAuth2Scopes.forEach(oAuth2Scope -> {
//            if (authorizedScopes.contains(oAuth2Scope.getScope())) {
//                previouslyApprovedScopesSet.add(oAuth2Scope);
//            } else {
//                scopesToApproves.add(oAuth2Scope);
//            }
//        });
//
//        String clientName = registeredClient.getClientName();
//        model.addAttribute("authorizationEndpoint", "https://wlngo.top:9400/oauth2/oauth2/authorize");
//        model.addAttribute("clientId", clientId);
//        model.addAttribute("clientName", clientName);
//        model.addAttribute("state", state);
//        model.addAttribute("scopes", scopesToApproves);
//        model.addAttribute("previouslyApprovedScopes", previouslyApprovedScopesSet);
//        model.addAttribute("principalName", principal.getName());
//
//        return "consent";
//    }
//}
