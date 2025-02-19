package top.wei.oauth2.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问被拒绝时的处理逻辑.
 */
public class SimpleAccessDeniedHandler extends ResponseWriter implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.write(request, response);
    }

    @Override
    protected Rest<?> body(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(1);
        map.put("uri", request.getRequestURI());
        return RestBody.build(HttpStatus.FORBIDDEN.value(), map, "禁止访问", false);
    }
}
