package top.wei.oauth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;
import top.wei.oauth2.utils.JackSonUtils;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

public class RedirectLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache;


    public RedirectLoginAuthenticationSuccessHandler() {
        this(new HttpSessionRequestCache());
    }

    public RedirectLoginAuthenticationSuccessHandler(RequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache must not be null");
        this.requestCache = requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        String targetUrl = savedRequest == null ? null : savedRequest.getRedirectUrl();
        clearAuthenticationAttributes(request);
        this.write(RestBody.okData(Collections.singletonMap("targetUrl", targetUrl), "登录成功！"), response);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    private void write(Rest<?> body, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resBody = JackSonUtils.getDefaultObjectMapper().writeValueAsString(body);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
