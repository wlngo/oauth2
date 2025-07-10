package top.wei.oauth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import top.wei.oauth2.utils.JackSonUtils;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;


/**
 * LogoutSuccessHandler.
 */
public class LogoutSuccessHandler implements LogoutHandler {
    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Rest<Object> objectRest = RestBody.okData(null, "登出成功！");
        String resBody = JackSonUtils.getDefaultObjectMapper().writeValueAsString(objectRest);
        response.getWriter().write(resBody);

    }
}
