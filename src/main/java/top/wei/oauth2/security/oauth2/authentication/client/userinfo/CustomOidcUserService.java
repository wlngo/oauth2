package top.wei.oauth2.security.oauth2.authentication.client.userinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.converter.ClaimConversionService;
import org.springframework.security.oauth2.core.converter.ClaimTypeConverter;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.wei.oauth2.model.dto.CustomOidcUserDto;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;
import top.wei.oauth2.model.entity.User;
import top.wei.oauth2.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


@Service
@RequiredArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final Converter<Map<String, Object>, Map<String, Object>> DEFAULT_CLAIM_TYPE_CONVERTER = new ClaimTypeConverter(createDefaultClaimTypeConverters());

    private final Set<String> accessibleScopes = new HashSet<>(Arrays.asList(OidcScopes.PROFILE, OidcScopes.EMAIL, OidcScopes.ADDRESS, OidcScopes.PHONE));

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService = new DefaultOAuth2UserService();

    private final Function<ClientRegistration, Converter<Map<String, Object>, Map<String, Object>>> claimTypeConverterFactory = clientRegistration -> DEFAULT_CLAIM_TYPE_CONVERTER;

    private final Predicate<OidcUserRequest> retrieveUserInfo = this::shouldRetrieveUserInfo;

    private final BiFunction<OidcUserRequest, OidcUserInfo, OidcUser> oidcUserMapper = this::getUser;


    private final UserService userService;

    private OidcUser getUser(OidcUserRequest userRequest, OidcUserInfo userInfo) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OidcUserAuthority(userRequest.getIdToken(), userInfo));
        OAuth2AccessToken token = userRequest.getAccessToken();
        for (String scope : token.getScopes()) {
            authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
        }
        ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
        String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> stringObjectMap = CustomOidcUserDto.collectClaims(userRequest.getIdToken(), userInfo);

        String uid = userRequest.getClientRegistration().getRegistrationId() + "_" + stringObjectMap.get(IdTokenClaimNames.SUB);
        if (StringUtils.hasText(userNameAttributeName)) {
            uid = userRequest.getClientRegistration().getRegistrationId() + "_" + stringObjectMap.get(userNameAttributeName);
        }
        User user = userService.getUserByUsername(uid);
        if (user == null) {
            // 如果用户不存在，则创建用户
            User userT = new User();
            PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            userT.setUsername(uid)
                    .setPassword(delegatingPasswordEncoder.encode(UUID.randomUUID().toString()))
                    .setNickName(userInfo.getNickName())
                    .setEmail(userInfo.getEmail())
                    .setEmailVerified(userInfo.getEmailVerified())
                    .setPhoneNumber(userInfo.getPhoneNumber())
                    .setPhoneNumberVerified(userInfo.getPhoneNumberVerified())
                    .setGender(convertGender(userInfo.getGender()))
                    .setBirthdate(convertBirthdate(userInfo.getBirthdate()))
                    .setAvatarUrl(userInfo.getPicture());
            userService.createUser(userT);
            user = userT;
        }
        UserLoginDto userLoginDto = userService.loadUserByUsername(user.getUsername());
        List<String> roleNames = userLoginDto.getRoleNames();
        List<PermissionDto> permissionByRoleNames = userService.getPermissionByRoleNames(roleNames);
        for (PermissionDto permissionByRoleName : permissionByRoleNames) {
            authorities.add(new SimpleGrantedAuthority(permissionByRoleName.getPermissionCode()));
        }
        return new CustomOidcUserDto(authorities, userRequest.getIdToken(), userInfo, userLoginDto.getUsername());
    }


    /**
     * convertBirthdate.
     *
     * @param birthdate birthdate
     * @return Date.
     */
    private Date convertBirthdate(String birthdate) {
        if (birthdate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(birthdate);
        } catch (ParseException ignored) {
        }
        return null;
    }

    /**
     * convertGender.
     *
     * @param gender gender
     * @return Byte.
     */
    private Byte convertGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return 1;
        }
        if ("female".equalsIgnoreCase(gender)) {
            return 0;
        }
        return -1;

    }

    /**
     * Returns the default {@link Converter}'s used for type conversion of claim values
     * for an {@link OidcUserInfo}.
     *
     * @return a {@link Map} of {@link Converter}'s keyed by {@link StandardClaimNames
     * claim name}
     * @since 5.2
     */
    public static Map<String, Converter<Object, ?>> createDefaultClaimTypeConverters() {
        Converter<Object, ?> booleanConverter = getConverter(TypeDescriptor.valueOf(Boolean.class));
        Converter<Object, ?> instantConverter = getConverter(TypeDescriptor.valueOf(Instant.class));
        Map<String, Converter<Object, ?>> claimTypeConverters = new HashMap<>();
        claimTypeConverters.put(StandardClaimNames.EMAIL_VERIFIED, booleanConverter);
        claimTypeConverters.put(StandardClaimNames.PHONE_NUMBER_VERIFIED, booleanConverter);
        claimTypeConverters.put(StandardClaimNames.UPDATED_AT, instantConverter);
        return claimTypeConverters;
    }

    private static Converter<Object, ?> getConverter(TypeDescriptor targetDescriptor) {
        TypeDescriptor sourceDescriptor = TypeDescriptor.valueOf(Object.class);
        return source -> ClaimConversionService.getSharedInstance().convert(source, sourceDescriptor, targetDescriptor);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        OidcUserInfo userInfo = null;
        if (this.retrieveUserInfo.test(userRequest)) {
            OAuth2User oauth2User = this.oauth2UserService.loadUser(userRequest);
            Map<String, Object> claims = getClaims(userRequest, oauth2User);
            userInfo = new OidcUserInfo(claims);
            // https://openid.net/specs/openid-connect-core-1_0.html#UserInfoResponse
            // 1) The sub (subject) Claim MUST always be returned in the UserInfo Response
            if (userInfo.getSubject() == null) {
                OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE);
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            }
            // 2) Due to the possibility of token substitution attacks (see Section
            // 16.11),
            // the UserInfo Response is not guaranteed to be about the End-User
            // identified by the sub (subject) element of the ID Token.
            // The sub Claim in the UserInfo Response MUST be verified to exactly match
            // the sub Claim in the ID Token; if they do not match,
            // the UserInfo Response values MUST NOT be used.
            if (!userInfo.getSubject().equals(userRequest.getIdToken().getSubject())) {
                OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE);
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            }
        }
        return this.oidcUserMapper.apply(userRequest, userInfo);
    }

    private Map<String, Object> getClaims(OidcUserRequest userRequest, OAuth2User oauth2User) {
        Converter<Map<String, Object>, Map<String, Object>> converter = this.claimTypeConverterFactory.apply(userRequest.getClientRegistration());
        if (converter != null) {
            return converter.convert(oauth2User.getAttributes());
        }
        return DEFAULT_CLAIM_TYPE_CONVERTER.convert(oauth2User.getAttributes());
    }

    private boolean shouldRetrieveUserInfo(OidcUserRequest userRequest) {
        // Auto-disabled if UserInfo Endpoint URI is not provided
        ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
        if (!StringUtils.hasLength(providerDetails.getUserInfoEndpoint().getUri())) {
            return false;
        }
        // The Claims requested by the profile, email, address, and phone scope values
        // are returned from the UserInfo Endpoint (as described in Section 5.3.2),
        // when a response_type value is used that results in an Access Token being
        // issued.
        // However, when no Access Token is issued, which is the case for the
        // response_type=id_token,
        // the resulting Claims are returned in the ID Token.
        // The Authorization Code Grant Flow, which is response_type=code, results in an
        // Access Token being issued.
        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(userRequest.getClientRegistration().getAuthorizationGrantType())) {
            // Return true if there is at least one match between the authorized scope(s)
            // and accessible scope(s)
            //
            // Also return true if authorized scope(s) is empty, because the provider has
            // not indicated which scopes are accessible via the access token
            // @formatter:off
            return this.accessibleScopes.isEmpty()
                    || CollectionUtils.isEmpty(userRequest.getAccessToken().getScopes())
                    || CollectionUtils.containsAny(userRequest.getAccessToken().getScopes(), this.accessibleScopes);
            // @formatter:on
        }
        return false;
    }


}
