package top.wei.oauth2.security.oauth2.authentication.client.userinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.UserConvert;
import top.wei.oauth2.security.oauth2.authentication.client.userinfo.convert.impl.DefaultUserConvertImpl;

import java.util.List;

/**
 * UserConvertFactory.
 */
@Service
@RequiredArgsConstructor
public class UserConvertFactory {

    private final List<UserConvert> userConverts;

    private final DefaultUserConvertImpl defaultUserConvert;


    public UserConvert getUserConvert(String registrationId) {
        return userConverts.stream()
                .filter(convert -> convert.isSupport(registrationId))
                .findFirst()
                .orElse(defaultUserConvert);
    }
}
