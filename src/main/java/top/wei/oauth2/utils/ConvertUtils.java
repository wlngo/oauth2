package top.wei.oauth2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.wei.oauth2.model.dto.ClientSettingsDto;
import top.wei.oauth2.model.dto.TokenDto;
import top.wei.oauth2.model.dto.TokenSettingsDto;
import top.wei.oauth2.model.entity.Oauth2Authorization;
import top.wei.oauth2.model.entity.Oauth2AuthorizationConsent;
import top.wei.oauth2.model.entity.Oauth2RegisteredClient;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ConvertUtils {

    public static OAuth2Authorization oAuth2AuthorizationToSpringOauth2Authorization(
            Oauth2Authorization oauth2Authorization, RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be empty");
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);

        builder.id(oauth2Authorization.getId())
                .principalName(oauth2Authorization.getPrincipalName())
                .authorizationGrantType(new AuthorizationGrantType(oauth2Authorization.getAuthorizationGrantType()))
                .authorizedScopes(getAuthorizedScopes(oauth2Authorization))
                .attributes(attr -> attr.putAll(getAttributes(oauth2Authorization)));

        handleState(oauth2Authorization, builder);
        handleAuthorizationCode(oauth2Authorization, builder);
        handleAccessToken(oauth2Authorization, builder);
        handleOidcIdToken(oauth2Authorization, builder);
        handleRefreshToken(oauth2Authorization, builder);
        handleUserCode(oauth2Authorization, builder);
        handleDeviceCode(oauth2Authorization, builder);

        return builder.build();
    }

    private static Set<String> getAuthorizedScopes(Oauth2Authorization oauth2Authorization) {
        if (oauth2Authorization.getAuthorizedScopes() != null) {
            return StringUtils.commaDelimitedListToSet(oauth2Authorization.getAuthorizedScopes());
        }
        return Collections.emptySet();
    }

    private static Map<String, Object> getAttributes(Oauth2Authorization oauth2Authorization) {
        return readMap(byteToString(oauth2Authorization.getAttributes()));
    }

    private static void handleState(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String state = oauth2Authorization.getState();
        if (StringUtils.hasText(state)) {
            builder.attribute(OAuth2ParameterNames.STATE, state);
        }
    }

    private static void handleAuthorizationCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String authorizationCodeValue = byteToString(oauth2Authorization.getAuthorizationCodeValue());
        if (StringUtils.hasText(authorizationCodeValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getAuthorizationCodeIssuedAt().toInstant();
            Instant tokenExpiresAt = oauth2Authorization.getAuthorizationCodeExpiresAt().toInstant();
            Map<String, Object> authorizationCodeMetadata = readMap(byteToString(oauth2Authorization.getAuthorizationCodeMetadata()));

            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    authorizationCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(authorizationCode, metadata -> metadata.putAll(authorizationCodeMetadata));
        }
    }

    private static void handleAccessToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String accessTokenValue = byteToString(oauth2Authorization.getAccessTokenValue());
        if (StringUtils.hasText(accessTokenValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getAccessTokenIssuedAt().toInstant();
            Instant tokenExpiresAt = oauth2Authorization.getAccessTokenExpiresAt().toInstant();

            OAuth2AccessToken.TokenType tokenType = getTokenType(oauth2Authorization);
            Assert.notNull(tokenType, "tokenType cannot be null");

            Set<String> scopes = getAccessTokenScopes(oauth2Authorization);
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes);
            Map<String, Object> accessTokenMetadata = readMap(byteToString(oauth2Authorization.getAccessTokenMetadata()));
            builder.token(accessToken, metadata -> metadata.putAll(accessTokenMetadata));
        }
    }

    private static OAuth2AccessToken.TokenType getTokenType(Oauth2Authorization oauth2Authorization) {
        String accessTokenType = oauth2Authorization.getAccessTokenType();
        return OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(accessTokenType)
                ? OAuth2AccessToken.TokenType.BEARER : null;
    }

    private static Set<String> getAccessTokenScopes(Oauth2Authorization oauth2Authorization) {
        String accessTokenScopes = oauth2Authorization.getAccessTokenScopes();
        if (accessTokenScopes != null) {
            return StringUtils.commaDelimitedListToSet(accessTokenScopes);
        }
        return Collections.emptySet();
    }

    @SuppressWarnings("unchecked")
    private static void handleOidcIdToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String oidcIdTokenValue = byteToString(oauth2Authorization.getOidcIdTokenValue());
        if (StringUtils.hasText(oidcIdTokenValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getOidcIdTokenIssuedAt().toInstant();
            Instant tokenExpiresAt = oauth2Authorization.getOidcIdTokenExpiresAt().toInstant();
            Map<String, Object> oidcTokenMetadata = readMap(byteToString(oauth2Authorization.getOidcIdTokenMetadata()));

            OidcIdToken oidcToken = new OidcIdToken(
                    oidcIdTokenValue, tokenIssuedAt, tokenExpiresAt, (Map<String, Object>) oidcTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, metadata -> metadata.putAll(oidcTokenMetadata));
        }
    }

    private static void handleRefreshToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String refreshTokenValue = byteToString(oauth2Authorization.getRefreshTokenValue());
        if (StringUtils.hasText(refreshTokenValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getRefreshTokenIssuedAt().toInstant();
            Instant tokenExpiresAt = getRefreshTokenExpiresAt(oauth2Authorization);

            Map<String, Object> refreshTokenMetadata = readMap(byteToString(oauth2Authorization.getRefreshTokenMetadata()));
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(refreshTokenValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(refreshToken, metadata -> metadata.putAll(refreshTokenMetadata));
        }
    }

    private static Instant getRefreshTokenExpiresAt(Oauth2Authorization oauth2Authorization) {
        Date refreshTokenExpiresAt = oauth2Authorization.getRefreshTokenExpiresAt();
        return refreshTokenExpiresAt != null ? refreshTokenExpiresAt.toInstant() : null;
    }

    private static void handleUserCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String userCodeValue = byteToString(oauth2Authorization.getUserCodeValue());
        if (StringUtils.hasText(userCodeValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getUserCodeIssuedAt().toInstant();
            Instant tokenExpiresAt = oauth2Authorization.getUserCodeExpiresAt().toInstant();
            Map<String, Object> userCodeMetadata = readMap(byteToString(oauth2Authorization.getUserCodeMetadata()));

            OAuth2UserCode userCode = new OAuth2UserCode(userCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(userCode, metadata -> metadata.putAll(userCodeMetadata));
        }
    }

    private static void handleDeviceCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization.Builder builder) {
        String deviceCodeValue = byteToString(oauth2Authorization.getDeviceCodeValue());
        if (StringUtils.hasText(deviceCodeValue)) {
            Instant tokenIssuedAt = oauth2Authorization.getDeviceCodeIssuedAt().toInstant();
            Instant tokenExpiresAt = oauth2Authorization.getDeviceCodeExpiresAt().toInstant();
            Map<String, Object> deviceCodeMetadata = readMap(byteToString(oauth2Authorization.getDeviceCodeMetadata()));

            OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(deviceCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(deviceCode, metadata -> metadata.putAll(deviceCodeMetadata));
        }
    }



    public static Oauth2Authorization springOauth2AuthorizationToOauth2Authorization(OAuth2Authorization authorization) {

        Oauth2Authorization oauth2Authorization = new Oauth2Authorization();
        oauth2Authorization.setId(authorization.getId())
                .setRegisteredClientId(authorization.getRegisteredClientId())
                .setPrincipalName(authorization.getPrincipalName())
                .setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());

        String authorizedScopes = null;
        if (!CollectionUtils.isEmpty(authorization.getAuthorizedScopes())) {
            authorizedScopes = StringUtils.collectionToDelimitedString(authorization.getAuthorizedScopes(), ",");
        }

        oauth2Authorization.setAuthorizedScopes(authorizedScopes);
        String attributes = writeMap(authorization.getAttributes());
        oauth2Authorization.setAttributes(stringToByte(attributes));

        String state = null;
        String authorizationState = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(authorizationState)) {
            state = authorizationState;
        }
        oauth2Authorization.setState(state);
        oauth2Authorization.setStateIndexSha256(state != null ? DigestUtils.sha256Hex(state) : null);

        setAuthorizationCode(oauth2Authorization, authorization);

        setAccessToken(oauth2Authorization, authorization);

        setOidcIdToken(oauth2Authorization, authorization);

        setRefreshToken(oauth2Authorization, authorization);

        setUserCode(oauth2Authorization, authorization);

        setDeviceCode(oauth2Authorization, authorization);

        return oauth2Authorization;

    }


    /**
     * 设备代码 OAuth2DeviceCode  OAuth 2.0 设备授权授予的一部分.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setDeviceCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);

        TokenDto tokenDto = getToken(deviceCode);

        oauth2Authorization.setDeviceCodeValue(stringToByte(tokenDto.getTokenValue()))
                .setDeviceCodeValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setDeviceCodeIssuedAt(tokenDto.getTokenIssuedAt())
                .setDeviceCodeExpiresAt(tokenDto.getTokenExpiresAt())
                .setDeviceCodeMetadata(stringToByte(tokenDto.getMetadata()));
    }


    /**
     * 用户代码 OAuth2UserCode  OAuth 2.0 设备授权授予的一部分.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setUserCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);

        TokenDto tokenDto = getToken(userCode);

        oauth2Authorization.setUserCodeValue(stringToByte(tokenDto.getTokenValue()))
                .setUserCodeValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setUserCodeIssuedAt(tokenDto.getTokenIssuedAt())
                .setUserCodeExpiresAt(tokenDto.getTokenExpiresAt())
                .setUserCodeMetadata(stringToByte(tokenDto.getMetadata()));
    }

    /**
     * 刷新令牌 OAuth2RefreshToke.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setRefreshToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();

        TokenDto tokenDto = getToken(refreshToken);

        oauth2Authorization.setRefreshTokenValue(stringToByte(tokenDto.getTokenValue()))
                .setRefreshTokenValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setRefreshTokenIssuedAt(tokenDto.getTokenIssuedAt())
                .setRefreshTokenExpiresAt(tokenDto.getTokenExpiresAt())
                .setRefreshTokenMetadata(stringToByte(tokenDto.getMetadata()));
    }

    /**
     * OpenID Connect Core 1.0 ID 令牌 OidcIdToken.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setOidcIdToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = authorization.getToken(OidcIdToken.class);

        TokenDto tokenDto = getToken(oidcIdToken);

        oauth2Authorization.setOidcIdTokenValue(stringToByte(tokenDto.getTokenValue()))
                .setOidcIdTokenValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setOidcIdTokenIssuedAt(tokenDto.getTokenIssuedAt())
                .setOidcIdTokenExpiresAt(tokenDto.getTokenExpiresAt())
                .setOidcIdTokenMetadata(stringToByte(tokenDto.getMetadata()));
    }

    /**
     * 访问令牌 OAuth2AccessToken.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setAccessToken(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                authorization.getToken(OAuth2AccessToken.class);

        TokenDto tokenDto = getToken(accessToken);

        String accessTokenType = null;
        String accessTokenScopes = null;
        if (accessToken != null) {
            accessTokenType = accessToken.getToken().getTokenType().getValue();
            if (!CollectionUtils.isEmpty(accessToken.getToken().getScopes())) {
                accessTokenScopes = StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ",");
            }
        }

        oauth2Authorization.setAccessTokenValue(stringToByte(tokenDto.getTokenValue()))
                .setAccessTokenValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setAccessTokenIssuedAt(tokenDto.getTokenIssuedAt())
                .setAccessTokenExpiresAt(tokenDto.getTokenExpiresAt())
                .setAccessTokenMetadata(stringToByte(tokenDto.getMetadata()))
                .setAccessTokenType(accessTokenType)
                .setAccessTokenScopes(accessTokenScopes);
    }


    /**
     * 授权码 AuthorizationCode.
     *
     * @param oauth2Authorization 数据库实体
     * @param authorization       authorization
     */
    private static void setAuthorizationCode(Oauth2Authorization oauth2Authorization, OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);

        TokenDto tokenDto = getToken(authorizationCode);

        oauth2Authorization.setAuthorizationCodeValue(stringToByte(tokenDto.getTokenValue()))
                .setAuthorizationCodeValueIndexSha256(tokenDto.getTokenValue() != null ? DigestUtils.sha256Hex(tokenDto.getTokenValue()) : null)
                .setAuthorizationCodeIssuedAt(tokenDto.getTokenIssuedAt())
                .setAuthorizationCodeExpiresAt(tokenDto.getTokenExpiresAt())
                .setAuthorizationCodeMetadata(stringToByte(tokenDto.getMetadata()));
    }

    /**
     * token 实体化.
     *
     * @param token OAuth2Authorization.Token
     * @return TokenDto
     */
    private static TokenDto getToken(OAuth2Authorization.Token<?> token) {

        String tokenValue = null;
        Timestamp tokenIssuedAt = null;
        Timestamp tokenExpiresAt = null;
        String metadata = null;

        if (token != null) {
            tokenValue = token.getToken().getTokenValue();
            if (token.getToken().getIssuedAt() != null) {
                tokenIssuedAt = Timestamp.from(token.getToken().getIssuedAt());
            }
            if (token.getToken().getExpiresAt() != null) {
                tokenExpiresAt = Timestamp.from(token.getToken().getExpiresAt());
            }
            metadata = writeMap(token.getMetadata());
        }
        return new TokenDto().setTokenValue(tokenValue)
                .setTokenIssuedAt(tokenIssuedAt)
                .setTokenExpiresAt(tokenExpiresAt)
                .setMetadata(metadata);

    }


    public static Oauth2RegisteredClient springOauth2RegisteredClientToOauth2RegisteredClient(RegisteredClient registeredClient) {
        List<String> clientAuthenticationMethods = new ArrayList<>(registeredClient.getClientAuthenticationMethods().size());
        registeredClient.getClientAuthenticationMethods().forEach(clientAuthenticationMethod ->
                clientAuthenticationMethods.add(clientAuthenticationMethod.getValue()));

        List<String> authorizationGrantTypes = new ArrayList<>(registeredClient.getAuthorizationGrantTypes().size());
        registeredClient.getAuthorizationGrantTypes().forEach(authorizationGrantType ->
                authorizationGrantTypes.add(authorizationGrantType.getValue()));

        Oauth2RegisteredClient oauth2RegisteredClient = new Oauth2RegisteredClient();

        oauth2RegisteredClient.setClientId(registeredClient.getId())
                .setClientId(registeredClient.getClientId())
                .setClientIdIssuedAt(registeredClient.getClientIdIssuedAt() != null
                        ? registeredClient.getClientIdIssuedAt() : Instant.now())
                .setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt() != null ? registeredClient.getClientSecretExpiresAt() : null)
                .setClientSecret(registeredClient.getClientSecret())
                .setClientName(registeredClient.getClientName())
                .setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods))
                .setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes))
                .setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()))
                .setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()))
                .setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()))
                .setClientSettings(writeMap(new ClientSettingsDto().buildByClientSettingsDto(registeredClient.getClientSettings())))
                .setTokenSettings(writeMap(new TokenSettingsDto().builderByTokenSettings(registeredClient.getTokenSettings())));
        return oauth2RegisteredClient;
    }

    public static RegisteredClient oauth2RegisteredClientToSpringOauth2RegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {

        List<String> clientAuthenticationMethodsList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(oauth2RegisteredClient.getClientAuthenticationMethods()));
        List<ClientAuthenticationMethod> clientAuthenticationMethodsRes = new ArrayList<>();
        clientAuthenticationMethodsList.forEach(method -> clientAuthenticationMethodsRes.add(new ClientAuthenticationMethod(method)));

        List<String> authorizationGrantTypesList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(oauth2RegisteredClient.getAuthorizationGrantTypes()));
        ArrayList<AuthorizationGrantType> authorizationGrantTypesRes = new ArrayList<>();
        authorizationGrantTypesList.forEach(authorizationGrantType -> authorizationGrantTypesRes.add(new AuthorizationGrantType(authorizationGrantType)));

        List<String> redirectUrisList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(oauth2RegisteredClient.getRedirectUris()));
        List<String> postLogoutRedirectUrisList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(oauth2RegisteredClient.getPostLogoutRedirectUris()));
        List<String> scopesList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(oauth2RegisteredClient.getScopes()));

        return RegisteredClient.withId(oauth2RegisteredClient.getId())
                .clientId(oauth2RegisteredClient.getClientId())
                .clientIdIssuedAt(oauth2RegisteredClient.getClientIdIssuedAt())
                .clientSecret(oauth2RegisteredClient.getClientSecret())
                .clientSecretExpiresAt(oauth2RegisteredClient.getClientSecretExpiresAt())
                .clientName(oauth2RegisteredClient.getClientName())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(clientAuthenticationMethodsRes))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(authorizationGrantTypesRes))
                .redirectUris(redirectUris -> redirectUris.addAll(redirectUrisList))
                .postLogoutRedirectUris(postLogoutRedirectUris -> postLogoutRedirectUris.addAll(postLogoutRedirectUrisList))
                .scopes(scopes -> scopes.addAll(scopesList))
                .clientSettings(read(oauth2RegisteredClient.getClientSettings(), ClientSettingsDto.class).builderClientSettings())
                .tokenSettings(read(oauth2RegisteredClient.getTokenSettings(), TokenSettingsDto.class).builderTokenSettings())
                .build();

    }

    public static Oauth2AuthorizationConsent springOauth2AuthorizationConsentToOauth2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        Set<GrantedAuthority> authorities = oAuth2AuthorizationConsent.getAuthorities();
        String authoritiesStr = StringUtils.collectionToDelimitedString(authorities, ",");
        Oauth2AuthorizationConsent oauth2AuthorizationConsent = new Oauth2AuthorizationConsent();
        oauth2AuthorizationConsent.setRegisteredClientId(oAuth2AuthorizationConsent.getRegisteredClientId());
        oauth2AuthorizationConsent.setPrincipalName(oAuth2AuthorizationConsent.getPrincipalName());
        oauth2AuthorizationConsent.setAuthorities(authoritiesStr);
        return oauth2AuthorizationConsent;
    }

    public static OAuth2AuthorizationConsent oauth2AuthorizationConsentToSpringOauth2AuthorizationConsent(Oauth2AuthorizationConsent oauth2AuthorizationConsent) {
        String authorities = oauth2AuthorizationConsent.getAuthorities();
        String principalName = oauth2AuthorizationConsent.getPrincipalName();
        String registeredClientId = oauth2AuthorizationConsent.getRegisteredClientId();
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(registeredClientId, principalName);
        if (authorities != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorities)) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }
        return builder.build();
    }

    public static String writeMap(Map<String, Object> data) {
        try {
            return JackSonUtils.getSecurityObjectMapper().writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static String writeMap(Object data) {
        try {
            return JackSonUtils.getSecurityObjectMapper().writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static Map<String, Object> readMap(String data) {
        try {

            return JackSonUtils.getSecurityObjectMapper().readValue(data, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static <T> T read(String data, Class<T> t) {
        try {
            return JackSonUtils.getSecurityObjectMapper().readValue(data, t);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }


    /**
     * 字符串转Byte.
     *
     * @param str 字符串
     * @return string非hasText返回null 其他返回byte[]
     */
    public static byte[] stringToByte(String str) {
        return StringUtils.hasText(str) ? str.getBytes(StandardCharsets.UTF_8) : null;
    }

    public static String byteToString(byte[] bytes) {
        return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : null;
    }


}
