package top.wei.oauth2.security.oauth2.authentication.client.endpoint;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 */
public class CustomOAuth2AccessTokenResponseHttpMessageConverter extends AbstractHttpMessageConverter<OAuth2AccessTokenResponse> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    private GenericHttpMessageConverter<Object> jsonMessageConverter = getJsonMessageConverter();

    private Converter<Map<String, Object>, OAuth2AccessTokenResponse> accessTokenResponseConverter = new DefaultMapOAuth2AccessTokenResponseConverter();

    private Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter = new DefaultOAuth2AccessTokenResponseMapConverter();

    public CustomOAuth2AccessTokenResponseHttpMessageConverter() {
        super(DEFAULT_CHARSET, MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
    }

    private static GenericHttpMessageConverter<Object> getJsonMessageConverter() {
        ClassLoader classLoader = CustomOAuth2AccessTokenResponseHttpMessageConverter.class.getClassLoader();

        // 检查 Jackson2 是否存在
        boolean jackson2Present = isClassPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)
                && isClassPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);

        if (jackson2Present) {
            return new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter();
        }

        // 检查 Gson 是否存在
        boolean gsonPresent = isClassPresent("com.google.gson.Gson", classLoader);
        if (gsonPresent) {
            return new org.springframework.http.converter.json.GsonHttpMessageConverter();
        }

        // 检查 JSON-B 是否存在
        boolean jsonbPresent = isClassPresent("jakarta.json.bind.Jsonb", classLoader);
        if (jsonbPresent) {
            return new org.springframework.http.converter.json.JsonbHttpMessageConverter();
        }

        throw new IllegalStateException("No JSON message converter available");
    }

    private static boolean isClassPresent(String className, ClassLoader classLoader) {
        try {
            Class.forName(className, false, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAuth2AccessTokenResponse.class.isAssignableFrom(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAuth2AccessTokenResponse readInternal(Class<? extends OAuth2AccessTokenResponse> clazz,
                                                     HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        try {
            Map<String, Object> tokenResponseParameters = (Map<String, Object>) this.jsonMessageConverter
                    .read(STRING_OBJECT_MAP.getType(), null, inputMessage);
            return this.accessTokenResponseConverter.convert(tokenResponseParameters);
        } catch (Exception ex) {
            throw new HttpMessageNotReadableException(
                    "An error occurred reading the OAuth 2.0 Access Token Response: " + ex.getMessage(), ex,
                    inputMessage);
        }
    }

    @Override
    protected void writeInternal(OAuth2AccessTokenResponse tokenResponse, HttpOutputMessage outputMessage)
            throws HttpMessageNotWritableException {
        try {
            Map<String, Object> tokenResponseParameters = this.accessTokenResponseParametersConverter
                    .convert(tokenResponse);
            this.jsonMessageConverter.write(tokenResponseParameters, STRING_OBJECT_MAP.getType(),
                    MediaType.APPLICATION_JSON, outputMessage);
        } catch (Exception ex) {
            throw new HttpMessageNotWritableException(
                    "An error occurred writing the OAuth 2.0 Access Token Response: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sets the {@link Converter} used for converting the OAuth 2.0 Access Token Response
     * parameters to an {@link OAuth2AccessTokenResponse}.
     *
     * @param accessTokenResponseConverter the {@link Converter} used for converting to an
     *                                     {@link OAuth2AccessTokenResponse}
     * @since 5.6
     */
    public final void setAccessTokenResponseConverter(
            Converter<Map<String, Object>, OAuth2AccessTokenResponse> accessTokenResponseConverter) {
        Assert.notNull(accessTokenResponseConverter, "accessTokenResponseConverter cannot be null");
        this.accessTokenResponseConverter = accessTokenResponseConverter;
    }

    /**
     * Sets the {@link Converter} used for converting the
     * {@link OAuth2AccessTokenResponse} to a {@code Map} representation of the OAuth 2.0
     * Access Token Response parameters.
     *
     * @param accessTokenResponseParametersConverter the {@link Converter} used for
     *                                               converting to a {@code Map} representation of the Access Token Response parameters
     * @since 5.6
     */
    public final void setAccessTokenResponseParametersConverter(
            Converter<OAuth2AccessTokenResponse, Map<String, Object>> accessTokenResponseParametersConverter) {
        Assert.notNull(accessTokenResponseParametersConverter, "accessTokenResponseParametersConverter cannot be null");
        this.accessTokenResponseParametersConverter = accessTokenResponseParametersConverter;
    }
}
