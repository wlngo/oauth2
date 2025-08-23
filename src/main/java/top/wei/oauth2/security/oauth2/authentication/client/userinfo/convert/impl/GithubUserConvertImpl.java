package top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.UserConvert;

import java.util.Map;
import java.util.UUID;

/**
 * GithubUserConvertImpl.
 */
@Service
public class GithubUserConvertImpl implements UserConvert {

    private static final String GITHUB_USER_ATTRIBUTE_NAME = "github";

    @Override
    public User createUser(String registrationId, String uid, Map<String, Object> additionalParameters) {
        User userT = new User();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userT.setUsername(uid)
                .setNickName(additionalParameters.get("name").toString())
                .setPassword(delegatingPasswordEncoder.encode(UUID.randomUUID().toString()))
                .setEmail((String) additionalParameters.get("email"))
                .setEmailVerified(StringUtils.isNotBlank((String) additionalParameters.get("email")))
                .setAvatarUrl((String) additionalParameters.get("avatar_url"));
        return userT;
    }

    @Override
    public boolean isSupport(String registrationId) {
        return GITHUB_USER_ATTRIBUTE_NAME.equalsIgnoreCase(registrationId);
    }
}
