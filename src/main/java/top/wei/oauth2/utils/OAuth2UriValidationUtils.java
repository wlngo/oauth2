package top.wei.oauth2.utils;

import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OAuth2 客户端 URI 验证工具类.
 */
public class OAuth2UriValidationUtils {

    /**
     * 验证重定向 URI 格式是否正确.
     *
     * @param redirectUris 逗号分隔的重定向URI字符串
     * @return 验证结果列表，包含无效的URI
     */
    public static List<String> validateRedirectUris(String redirectUris) {
        List<String> invalidUris = new ArrayList<>();
        
        if (!StringUtils.hasText(redirectUris)) {
            invalidUris.add("重定向URI不能为空");
            return invalidUris;
        }

        String[] uris = StringUtils.commaDelimitedListToStringArray(redirectUris);
        if (uris.length == 0) {
            invalidUris.add("至少需要一个重定向URI");
            return invalidUris;
        }

        for (String uri : uris) {
            String trimmedUri = uri.trim();
            if (!StringUtils.hasText(trimmedUri)) {
                invalidUris.add("重定向URI不能为空字符串");
                continue;
            }

            List<String> uriValidationErrors = validateSingleUri(trimmedUri, "重定向URI");
            invalidUris.addAll(uriValidationErrors);
        }

        return invalidUris;
    }

    /**
     * 验证登出重定向 URI 格式是否正确.
     *
     * @param postLogoutRedirectUris 逗号分隔的登出重定向URI字符串
     * @return 验证结果列表，包含无效的URI
     */
    public static List<String> validatePostLogoutRedirectUris(String postLogoutRedirectUris) {
        List<String> invalidUris = new ArrayList<>();
        
        // 登出重定向URI可以为空
        if (!StringUtils.hasText(postLogoutRedirectUris)) {
            return invalidUris;
        }

        String[] uris = StringUtils.commaDelimitedListToStringArray(postLogoutRedirectUris);

        for (String uri : uris) {
            String trimmedUri = uri.trim();
            if (!StringUtils.hasText(trimmedUri)) {
                invalidUris.add("登出重定向URI不能为空字符串");
                continue;
            }

            List<String> uriValidationErrors = validateSingleUri(trimmedUri, "登出重定向URI");
            invalidUris.addAll(uriValidationErrors);
        }

        return invalidUris;
    }

    /**
     * 验证单个 URI 的格式.
     *
     * @param uri     要验证的URI
     * @param uriType URI类型描述（用于错误信息）
     * @return 验证错误列表
     */
    private static List<String> validateSingleUri(String uri, String uriType) {
        List<String> errors = new ArrayList<>();

        try {
            URI parsedUri = new URI(uri);
            
            // 检查scheme
            if (parsedUri.getScheme() == null) {
                errors.add(uriType + " [" + uri + "] 缺少协议 (如 http:// 或 https://)");
            } else {
                String scheme = parsedUri.getScheme().toLowerCase();
                if (!Arrays.asList("http", "https").contains(scheme)) {
                    errors.add(uriType + " [" + uri + "] 只支持 HTTP 或 HTTPS 协议");
                }
            }

            // 检查host
            if (parsedUri.getHost() == null) {
                errors.add(uriType + " [" + uri + "] 缺少主机名");
            }

            // 检查是否包含fragment（OAuth2规范不允许）
            if (parsedUri.getFragment() != null) {
                errors.add(uriType + " [" + uri + "] 不能包含 fragment (#)");
            }

        } catch (URISyntaxException e) {
            errors.add(uriType + " [" + uri + "] 格式无效: " + e.getMessage());
        }

        return errors;
    }

    /**
     * 规范化 URI 字符串，移除空白字符并确保格式一致.
     *
     * @param uris 逗号分隔的URI字符串
     * @return 规范化后的URI字符串
     */
    public static String normalizeUris(String uris) {
        if (!StringUtils.hasText(uris)) {
            return uris;
        }

        String[] uriArray = StringUtils.commaDelimitedListToStringArray(uris);
        List<String> normalizedUris = new ArrayList<>();

        for (String uri : uriArray) {
            String trimmedUri = uri.trim();
            if (StringUtils.hasText(trimmedUri)) {
                normalizedUris.add(trimmedUri);
            }
        }

        return StringUtils.collectionToCommaDelimitedString(normalizedUris);
    }

    /**
     * 检查是否包含重复的 URI.
     *
     * @param uris 逗号分隔的URI字符串
     * @return 重复的URI列表
     */
    public static List<String> findDuplicateUris(String uris) {
        List<String> duplicates = new ArrayList<>();
        
        if (!StringUtils.hasText(uris)) {
            return duplicates;
        }

        String[] uriArray = StringUtils.commaDelimitedListToStringArray(uris);
        List<String> seen = new ArrayList<>();

        for (String uri : uriArray) {
            String trimmedUri = uri.trim();
            if (seen.contains(trimmedUri)) {
                if (!duplicates.contains(trimmedUri)) {
                    duplicates.add(trimmedUri);
                }
            } else {
                seen.add(trimmedUri);
            }
        }

        return duplicates;
    }
}