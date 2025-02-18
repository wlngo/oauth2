package top.wei.oauth2.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;

import java.io.IOException;

public class RememberMeRedirectLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache;


    public RememberMeRedirectLoginAuthenticationSuccessHandler() {
        this(new HttpSessionRequestCache());
    }

    public RememberMeRedirectLoginAuthenticationSuccessHandler(RequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache must not be null");
        this.requestCache = requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        String targetUrl = savedRequest == null ? null : savedRequest.getRedirectUrl();
        clearAuthenticationAttributes(request);
        this.redirectLogin(targetUrl, response);
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

    private void redirectLogin(String redirectUrl,HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }
        if (StringUtils.isNotBlank(redirectUrl)){
            response.sendRedirect(redirectUrl);
        }else {
            response.sendRedirect("/");
        }
    }
}
