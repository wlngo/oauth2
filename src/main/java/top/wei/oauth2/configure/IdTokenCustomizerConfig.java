package top.wei.oauth2.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author 魏亮宁
 * @date 2023年07月07日 16:27:00
 */
@Configuration
public class IdTokenCustomizerConfig {

    private static final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

    @Bean
    @SuppressWarnings("unchecked")
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
                String issuer;
                if (context.getAuthorizationServerContext() != null) {
                    issuer = context.getAuthorizationServerContext().getIssuer();
                    claimsBuilder.issuer(issuer);
                }
                Instant issuedAt = Instant.now();
                Instant expiresAt;
                expiresAt = issuedAt.plus(30, ChronoUnit.MINUTES);

                RegisteredClient registeredClient = context.getRegisteredClient();
                claimsBuilder
                        .subject(context.getPrincipal().getName())
                        .audience(Collections.singletonList(registeredClient.getClientId()))
                        .issuedAt(issuedAt)
                        .expiresAt(expiresAt)
                        .id(UUID.randomUUID().toString());
                JwtClaimsSet build = claimsBuilder.claim(IdTokenClaimNames.AZP, registeredClient.getClientId()).build();

                context.getClaims().claims(claims ->
                        claims.putAll(build.getClaims()));
            }
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims((claims) -> {
                    Set<String> rolesScope = (Set<String>) claims.get("scope");
                    HashSet<String> rolesScopePrefix = new HashSet<>(rolesScope.size());
                    rolesScope.forEach(
                            role -> rolesScopePrefix.add(SCOPE_AUTHORITY_PREFIX + role)
                    );
                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities());
                    roles.addAll(rolesScopePrefix);
                    Set<String> collect = roles.stream()
                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));


                    claims.put("roles", collect);
                });
            }
        };

    }

//    static final class FederatedIdentityIdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
//
//        private static final Set<String> ID_TOKEN_CLAIMS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
//                IdTokenClaimNames.ISS,
//                IdTokenClaimNames.SUB,
//                IdTokenClaimNames.AUD,
//                IdTokenClaimNames.EXP,
//                IdTokenClaimNames.IAT,
//                IdTokenClaimNames.AUTH_TIME,
//                IdTokenClaimNames.NONCE,
//                IdTokenClaimNames.ACR,
//                IdTokenClaimNames.AMR,
//                IdTokenClaimNames.AZP,
//                IdTokenClaimNames.AT_HASH,
//                IdTokenClaimNames.C_HASH
//        )));
//
//        @Override
//        public void customize(JwtEncodingContext context) {
//            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
//                Map<String, Object> thirdPartyClaims = extractClaims(context.getPrincipal());
//                context.getClaims().claims(existingClaims -> {
//                    // Remove conflicting claims set by this authorization server
//                    existingClaims.keySet().forEach(thirdPartyClaims::remove);
//
//                    // Remove standard id_token claims that could cause problems with clients
//                    ID_TOKEN_CLAIMS.forEach(thirdPartyClaims::remove);
//
//                    // Add all other claims directly to id_token
//                    existingClaims.putAll(thirdPartyClaims);
//                });
//            }
//        }
//
//        private Map<String, Object> extractClaims(Authentication principal) {
//            Map<String, Object> claims;
//            if (principal.getPrincipal() instanceof OidcUser) {
//                OidcUser oidcUser = (OidcUser) principal.getPrincipal();
//                OidcIdToken idToken = oidcUser.getIdToken();
//                claims = idToken.getClaims();
//            } else if (principal.getPrincipal() instanceof OAuth2User) {
//                OAuth2User oauth2User = (OAuth2User) principal.getPrincipal();
//                claims = oauth2User.getAttributes();
//            } else {
//                claims = Collections.emptyMap();
//            }
//
//            return new HashMap<>(claims);
//        }
//
//    }

}
