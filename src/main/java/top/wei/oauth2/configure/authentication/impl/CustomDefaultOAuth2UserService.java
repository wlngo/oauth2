package top.wei.oauth2.configure.authentication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * CustomDefaultOAuth2UserService.
 */
@Service
@RequiredArgsConstructor
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String name = oAuth2User.getName();
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
        List<PermissionDto> permissionByRoleNames = userService.getPermissionByRoleNames(roleNames);
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (PermissionDto permissionByRoleName : permissionByRoleNames) {
            authorities.add(new SimpleGrantedAuthority(permissionByRoleName.getPermissionCode()));
        }
        Collection<? extends GrantedAuthority> authoritiesRes = oAuth2User.getAuthorities();
        for (GrantedAuthority authoritiesRe : authoritiesRes) {
            authorities.add(new SimpleGrantedAuthority(authoritiesRe.getAuthority()));
        }
        Map<String, Object> map = new HashMap<>(oAuth2User.getAttributes());
        map.put("uid", uid);
        Map<String, Object> immutableAttributes = Collections.unmodifiableMap(map);
        return new DefaultOAuth2User(authorities, immutableAttributes, "uid");

    }
}
