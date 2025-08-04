package top.wei.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * OAuth2RememberMeSuccessHandler.
 */
public class OAuth2RememberMeSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RememberMeServices rememberMeServices;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Constructor for OAuth2RememberMeSuccessHandler.
     *
     * @param rememberMeServices the RememberMeServices to use
     */
    public OAuth2RememberMeSuccessHandler(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            rememberMeServices.loginSuccess(request, response, authentication);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        boolean haveTargetUrl = targetUrlParameter != null
                && StringUtils.hasText(request.getParameter(targetUrlParameter));
        if (isAlwaysUseDefaultTargetUrl()
                || haveTargetUrl) {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            rememberMeServices.loginSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);
        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        rememberMeServices.loginSuccess(request, response, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


}
