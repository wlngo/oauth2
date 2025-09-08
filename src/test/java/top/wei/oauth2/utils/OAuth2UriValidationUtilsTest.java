package top.wei.oauth2.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * OAuth2UriValidationUtils 测试类.
 */
public class OAuth2UriValidationUtilsTest {

    @Test
    public void testValidateRedirectUrisValid() {
        String validUris = "http://localhost:8080/callback,https://example.com/oauth/callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(validUris);
        assertTrue(errors.isEmpty(), "Valid URIs should not have any errors");
    }

    @Test
    public void testValidateRedirectUrisEmpty() {
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris("");
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("不能为空"));
    }

    @Test
    public void testValidateRedirectUrisNull() {
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(null);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("不能为空"));
    }

    @Test
    public void testValidateRedirectUrisInvalidScheme() {
        String invalidUris = "ftp://example.com/callback,http://localhost:8080/callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(invalidUris);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("只支持 HTTP 或 HTTPS 协议"));
    }

    @Test
    public void testValidateRedirectUrisNoScheme() {
        String invalidUris = "example.com/callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(invalidUris);
        // This URI will generate 2 errors: missing scheme and missing host (because without scheme, host parsing fails)
        assertTrue(errors.size() >= 1);
        assertTrue(errors.stream().anyMatch(error -> error.contains("缺少协议") || error.contains("格式无效")));
    }

    @Test
    public void testValidateRedirectUrisWithFragment() {
        String invalidUris = "http://example.com/callback#fragment";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(invalidUris);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("不能包含 fragment"));
    }

    @Test
    public void testValidateRedirectUrisNoHost() {
        String invalidUris = "http:///callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(invalidUris);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("缺少主机名"));
    }

    @Test
    public void testValidatePostLogoutRedirectUrisValid() {
        String validUris = "http://localhost:8080/logout,https://example.com/logout";
        List<String> errors = OAuth2UriValidationUtils.validatePostLogoutRedirectUris(validUris);
        assertTrue(errors.isEmpty(), "Valid post logout URIs should not have any errors");
    }

    @Test
    public void testValidatePostLogoutRedirectUrisEmpty() {
        // 登出重定向URI可以为空
        List<String> errors = OAuth2UriValidationUtils.validatePostLogoutRedirectUris("");
        assertTrue(errors.isEmpty(), "Empty post logout URIs should be allowed");
    }

    @Test
    public void testValidatePostLogoutRedirectUrisNull() {
        // 登出重定向URI可以为null
        List<String> errors = OAuth2UriValidationUtils.validatePostLogoutRedirectUris(null);
        assertTrue(errors.isEmpty(), "Null post logout URIs should be allowed");
    }

    @Test
    public void testNormalizeUris() {
        String uris = " http://localhost:8080/callback , https://example.com/oauth/callback , ";
        String normalized = OAuth2UriValidationUtils.normalizeUris(uris);
        assertEquals("http://localhost:8080/callback,https://example.com/oauth/callback", normalized);
    }

    @Test
    public void testNormalizeUrisEmptyElements() {
        String uris = "http://localhost:8080/callback,,https://example.com/oauth/callback";
        String normalized = OAuth2UriValidationUtils.normalizeUris(uris);
        assertEquals("http://localhost:8080/callback,https://example.com/oauth/callback", normalized);
    }

    @Test
    public void testFindDuplicateUris() {
        String uris = "http://localhost:8080/callback,https://example.com/oauth/callback,http://localhost:8080/callback";
        List<String> duplicates = OAuth2UriValidationUtils.findDuplicateUris(uris);
        assertEquals(1, duplicates.size());
        assertEquals("http://localhost:8080/callback", duplicates.get(0));
    }

    @Test
    public void testFindDuplicateUrisNoDuplicates() {
        String uris = "http://localhost:8080/callback,https://example.com/oauth/callback";
        List<String> duplicates = OAuth2UriValidationUtils.findDuplicateUris(uris);
        assertTrue(duplicates.isEmpty(), "Should not find any duplicates");
    }

    @Test
    public void testValidateRedirectUrisMixedValidAndInvalid() {
        String mixedUris = "http://localhost:8080/callback,invalid-uri,https://example.com/oauth/callback#fragment";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(mixedUris);
        // "invalid-uri" will likely generate multiple errors (no scheme, potentially no host)
        // "https://example.com/oauth/callback#fragment" will generate fragment error
        assertTrue(errors.size() >= 2);
        // 应该包含无效URI和fragment错误
        assertTrue(errors.stream().anyMatch(error -> error.contains("格式无效") || error.contains("缺少协议")));
        assertTrue(errors.stream().anyMatch(error -> error.contains("不能包含 fragment")));
    }

    @Test
    public void testValidateRedirectUrisLocalhostAndIpAddress() {
        String validUris = "http://localhost:8080/callback,http://127.0.0.1:8080/callback,http://192.168.1.100:8080/callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(validUris);
        assertTrue(errors.isEmpty(), "Localhost and IP addresses should be valid");
    }

    @Test
    public void testValidateRedirectUrisWithPort() {
        String validUris = "http://example.com:8080/callback,https://example.com:443/callback";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(validUris);
        assertTrue(errors.isEmpty(), "URIs with ports should be valid");
    }

    @Test
    public void testValidateRedirectUrisWithQueryParameters() {
        String validUris = "http://localhost:8080/callback?param1=value1&param2=value2";
        List<String> errors = OAuth2UriValidationUtils.validateRedirectUris(validUris);
        assertTrue(errors.isEmpty(), "URIs with query parameters should be valid");
    }
}