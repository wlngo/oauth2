package top.wei.oauth2.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;


@Data
@Accessors(chain = true)
@JsonSerialize
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class TokenSettingsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Duration authorizationCodeTimeToLive;

    private Duration accessTokenTimeToLive;

    /**
     * 只有两种 reference self-contained.
     */
    private String accessTokenFormat;

    private Duration deviceCodeTimeToLive;

    private Boolean isReuseRefreshTokens;

    private Duration refreshTokenTimeToLive;

    private SignatureAlgorithm idTokenSignatureAlgorithm;

    public TokenSettings builderTokenSettings() {
        TokenSettings.Builder builder = TokenSettings.builder();
        return builder
                .authorizationCodeTimeToLive(Optional.ofNullable(this.authorizationCodeTimeToLive)
                        .orElse(Duration.ofMinutes(5)))
                .accessTokenTimeToLive(Optional.ofNullable(this.accessTokenTimeToLive)
                        .orElse(Duration.ofMinutes(5)))
                .accessTokenFormat("reference".equals(accessTokenFormat) ? OAuth2TokenFormat.REFERENCE : OAuth2TokenFormat.SELF_CONTAINED)
                .deviceCodeTimeToLive(Optional.ofNullable(this.deviceCodeTimeToLive)
                        .orElse(Duration.ofMinutes(5)))
                .reuseRefreshTokens(Optional.ofNullable(this.isReuseRefreshTokens)
                        .orElse(true))
                .refreshTokenTimeToLive(Optional.ofNullable(this.refreshTokenTimeToLive)
                        .orElse(Duration.ofMinutes(60)))
                .idTokenSignatureAlgorithm(Optional.ofNullable(this.idTokenSignatureAlgorithm)
                        .orElse(SignatureAlgorithm.RS256)).build();
    }

    public TokenSettingsDto builderByTokenSettings(TokenSettings tokenSettings) {

        TokenSettingsDto tokenSettingsDto = new TokenSettingsDto();
        tokenSettingsDto.setAuthorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive())
                .setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive())
                .setAccessTokenFormat(tokenSettings.getAccessTokenFormat().getValue())
                .setDeviceCodeTimeToLive(tokenSettings.getDeviceCodeTimeToLive())
                .setIsReuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                .setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive())
                .setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm());
        return tokenSettingsDto;

    }


}
