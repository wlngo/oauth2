package top.wei.oauth2.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author 魏亮宁
 * @date 2023年07月06日 10:45:00
 */
@Data
@Accessors(chain = true)
@JsonSerialize
public class ClientSettingsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean isRequireProofKey;

    private Boolean isRequireAuthorizationConsent;

    private String jwkSetUrl;

    private String tokenEndpointAuthenticationSigningAlgorithm;

    public ClientSettings builderClientSettings() {
        ClientSettings.Builder builder = ClientSettings.builder();

        builder.requireProofKey(Optional.ofNullable(this.isRequireProofKey)
                        .orElse(Boolean.FALSE))
                .requireAuthorizationConsent(Optional.ofNullable(this.isRequireAuthorizationConsent)
                        .orElse(Boolean.FALSE));

        if (StringUtils.isNotBlank(this.jwkSetUrl)) {
            builder.jwkSetUrl(this.jwkSetUrl);
        }

        if (StringUtils.isNotBlank(this.tokenEndpointAuthenticationSigningAlgorithm)) {
            JwsAlgorithm tokenEndpointAuthenticationSigningAlgorithm = MacAlgorithm.from(this.tokenEndpointAuthenticationSigningAlgorithm);
            if (tokenEndpointAuthenticationSigningAlgorithm == null) {
                tokenEndpointAuthenticationSigningAlgorithm = SignatureAlgorithm.from(this.tokenEndpointAuthenticationSigningAlgorithm);
            }
            builder.tokenEndpointAuthenticationSigningAlgorithm(tokenEndpointAuthenticationSigningAlgorithm);
        }

        return builder.build();
    }

    public ClientSettingsDto buildByClientSettingsDto(ClientSettings clientSettings) {

        ClientSettingsDto clientSettingsDto = new ClientSettingsDto();
        clientSettingsDto.setIsRequireProofKey(clientSettings.isRequireProofKey())
                .setIsRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        if (StringUtils.isNotBlank(clientSettings.getJwkSetUrl())) {
            clientSettingsDto.setJwkSetUrl(clientSettings.getJwkSetUrl());
        }
        if (clientSettings.getTokenEndpointAuthenticationSigningAlgorithm() != null) {
            clientSettingsDto.setTokenEndpointAuthenticationSigningAlgorithm(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm().getName());
        }
        return clientSettingsDto;
    }

}
