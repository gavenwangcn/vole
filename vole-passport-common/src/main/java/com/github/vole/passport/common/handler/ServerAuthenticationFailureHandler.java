package com.github.vole.passport.common.handler;

import com.github.vole.passport.common.contants.PassportConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServerAuthenticationFailureHandler implements AuthenticationFailureHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());
    private String defaultFailureUri;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private String serviceParamName;
    private String targetParamName;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void setTargetParamName(String targetParamName) {
        this.targetParamName = targetParamName;
    }

    public void setServiceParamName(String serviceParamName) {
        this.serviceParamName = serviceParamName;
    }

    public ServerAuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUri = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String redirect = obtainRedirect(request);
        String target  = obtainTarget(request);
        if (defaultFailureUri == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");

            response.sendError(HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(defaultFailureUri);
            if (!StringUtils.isEmpty(redirect)) {
                builder.queryParam(getServiceParamName(), redirect);
            }
            if (!StringUtils.isEmpty(target)) {
                builder.queryParam(getTargetParamName(), target);
            }
            saveException(request, exception);

            if (forwardToDestination) {
                logger.debug("Forwarding to " + builder.build().encode().getPath());

                request.getRequestDispatcher(builder.build().encode().getPath())
                        .forward(request, response);
            } else {
                logger.debug("Redirecting to " + builder.build().encode().toUriString());
                redirectStrategy.sendRedirect(request, response, builder.build().encode().toUriString());
            }
        }
    }

    protected final void saveException(HttpServletRequest request,
                                       AuthenticationException exception) {
        if (forwardToDestination) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        } else {
            HttpSession session = request.getSession(false);

            if (session != null || allowSessionCreation) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                        exception);
            }
        }
    }


    protected String obtainRedirect(HttpServletRequest request) {

        return request.getParameter(getServiceParamName());
    }

    protected String obtainTarget(HttpServletRequest request) {

        return request.getParameter(getTargetParamName());
    }

    protected String getTargetParamName() {
        return !StringUtils.isEmpty(targetParamName) ? targetParamName : PassportConstants.DEFAULT_TARGET_PARAM;
    }

    protected String getServiceParamName() {
        return !StringUtils.isEmpty(serviceParamName) ? serviceParamName : PassportConstants.DEFAULT_SERVICE_PARAM;
    }
}
