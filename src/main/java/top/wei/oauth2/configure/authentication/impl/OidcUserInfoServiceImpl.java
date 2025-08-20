package top.wei.oauth2.configure.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import top.wei.oauth2.configure.authentication.OidcUserInfoService;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.entity.User;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * OidcUserInfoServiceImpl.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OidcUserInfoServiceImpl implements OidcUserInfoService {

    private final UserMapper userMapper;

    @Override
    public OidcUserInfo loadUser(String username) {
        return new OidcUserInfo(this.findByUsername(username));
    }

    private Map<String, Object> findByUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("deleted", 0));

//        DefaultAddressStandardClaim.Builder addressBuilder = new DefaultAddressStandardClaim.Builder();
//        AddressStandardClaim addressStandardClaim = addressBuilder.country("china").build();
        OidcUserInfo.Builder builder = OidcUserInfo.builder()
                .subject(username)
                .name(user.getUsername())
                .nickname(user.getNickName())
                .preferredUsername(username)
                .picture(user.getAvatarUrl())
                .email(user.getEmail())
                .emailVerified(user.getEmailVerified())
                .phoneNumber(user.getPhoneNumber())
                .phoneNumberVerified(user.getPhoneNumberVerified())
                //自定义claim
                .claim("username", username)
                .zoneinfo(TimeZone.getDefault().getID())
                .locale(Locale.getDefault().getLanguage());

        if (user.getGender() != null) {
            if (user.getGender().intValue() == 0) {
                builder.gender("女");
            }
            if (user.getGender().intValue() == 1) {
                builder.gender("男");
            }
        }
        if (user.getBirthdate() != null) {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String birthDate = ft.format(user.getBirthdate());
            builder.birthdate(birthDate);
        }

        if (user.getUpdatedAt() != null) {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String updateTime = ft.format(user.getUpdatedAt());
            builder.updatedAt(updateTime);
        }
        return builder.build().getClaims();
    }


}
