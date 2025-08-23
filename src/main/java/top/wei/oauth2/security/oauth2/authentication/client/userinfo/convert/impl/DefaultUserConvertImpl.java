package top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.impl;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.UserConvert;

import java.util.Map;
import java.util.UUID;

/**
 * DefaultUserConvertImpl.
 */
@Service
public class DefaultUserConvertImpl implements UserConvert {

    @Override
    public User createUser(String registrationId, String uid, Map<String, Object> additionalParameters) {
        User userT = new User();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userT.setUsername(uid)
                .setPassword(delegatingPasswordEncoder.encode(UUID.randomUUID().toString()));
        return userT;
    }

    @Override
    public boolean isSupport(String registrationId) {
        return false;
    }
}
