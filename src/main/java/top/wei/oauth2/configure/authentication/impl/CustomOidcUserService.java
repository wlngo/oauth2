package top.wei.oauth2.configure.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.service.UserService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        OidcUserInfo userInfo = oidcUser.getUserInfo();
        String name = oidcUser.getName();
        String uid = userRequest.getClientRegistration().getRegistrationId() + "_" + name;
        User user = userService.getUserById(uid);
        if (user == null) {
            // 如果用户不存在，则创建用户
            User userT = new User();
            PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            userT.setUserId(uid)
                    .setUsername(UUID.randomUUID().toString())
                    .setPassword(delegatingPasswordEncoder.encode(UUID.randomUUID().toString()));
            userService.createUser(userT);
            user = userT;
        }
        UserLoginDto userLoginDto = userService.loadUserByUsername(user.getUsername());
        List<String> roleNames = userLoginDto.getRoleNames();
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OidcUserAuthority(userRequest.getIdToken(), userInfo));
        OAuth2AccessToken token = userRequest.getAccessToken();
        for (String scope : token.getScopes()) {
            authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
        }
        List<PermissionDto> permissionByRoleNames = userService.getPermissionByRoleNames(roleNames);
        for (PermissionDto permissionByRoleName : permissionByRoleNames) {
            authorities.add(new SimpleGrantedAuthority(permissionByRoleName.getPermissionCode()));
        }

        ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
        String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
        if (StringUtils.hasText(userNameAttributeName)) {
            return new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo, userNameAttributeName);
        }
        return new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);
    }
}
