package com.github.vole.passport.common.handler;

import com.github.vole.passport.common.contants.PassportConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String index = "/index.html";

    protected final Log logger = LogFactory.getLog(this.getClass());

    private String targetParamName;

    public void setTargetParamName(String targetParamName) {
        this.targetParamName = targetParamName;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException, ServletException {

        String targetUrl = obtainTarget(request);
        if (!StringUtils.isEmpty(targetUrl)) {
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(index);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
    }

    protected String obtainTarget(HttpServletRequest request) {

        return request.getParameter(getTargetParamName());
    }

    protected String getTargetParamName() {
        return !StringUtils.isEmpty(targetParamName) ? targetParamName : PassportConstants.DEFAULT_TARGET_PARAM;
    }
}
