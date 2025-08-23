package top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert;

import top.wei.oauth2.model.entity.User;

import java.util.Map;

/**
 * UserConvert.
 */
public interface UserConvert {

    /**
     * Convert OAuth2User to User.
     *
     * @param registrationId the registration ID
     * @param uid            the user ID
     * @param additionalParameters additional parameters
     * @return User
     */
    User createUser(String registrationId, String uid, Map<String, Object> additionalParameters);

    /**
     *isSupport.
     *
     * @param registrationId the registration ID
     * @return UserLoginDto
     */
    boolean isSupport(String registrationId);
}
