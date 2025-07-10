package top.wei.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.wei.oauth2.utils.JackSonUtils;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.io.IOException;


/**
 * CustomLogoutSuccessHandler.
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Rest<Object> objectRest = RestBody.okData(null, "登出成功！");
        String resBody = JackSonUtils.getDefaultObjectMapper().writeValueAsString(objectRest);
        response.getWriter().write(resBody);
    }
}
