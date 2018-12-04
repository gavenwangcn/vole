package com.github.vole.passport.common.handler;

import com.github.vole.passport.common.contants.PassportConstants;
import com.github.vole.passport.common.cookie.CookieOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

    private String serverLogoutUrl;

    private String ssoLogoutPath;

    private String logoutServiceParamName;

    private CookieOperations cookieOperations;

    public void setCookieOperations(CookieOperations cookieOperations) {
        this.cookieOperations = cookieOperations;
    }

    public void setServerLogoutUrl(String serverLogoutUrl) {
        this.serverLogoutUrl = serverLogoutUrl;
    }

    public void setSsoLogoutPath(String ssoLogoutPath) {
        this.ssoLogoutPath = ssoLogoutPath;
    }
    public void setLogoutServiceParamName(String logoutServiceParamName) {
        this.logoutServiceParamName = logoutServiceParamName;
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        cookieOperations.removeCookie(request, response);
        if (StringUtils.isEmpty(serverLogoutUrl)) {
            if (StringUtils.isEmpty(ssoLogoutPath)) {
                return super.determineTargetUrl(request, response);
            }
            return ssoLogoutPath;
        } else {
            String serviceDefaultTargetUrl = getServiceDefaultTargetUrl(request, response);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverLogoutUrl);
            builder.queryParam(getLogoutServiceParamName(), serviceDefaultTargetUrl);
            return builder.build().encode().toUriString();
        }
    }


    private String getLogoutServiceParamName() {
        return !StringUtils.isEmpty(logoutServiceParamName) ? logoutServiceParamName : PassportConstants.DEFAULT_SERVICE_PARAM;
    }

    private String getServiceDefaultTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String requestUrl = request.getRequestURL().toString();
        UriComponentsBuilder localRequestbuilder = UriComponentsBuilder.fromHttpUrl(requestUrl);
        if (StringUtils.isEmpty(ssoLogoutPath)) {
            localRequestbuilder.replacePath(ssoLogoutPath);
        } else {
            localRequestbuilder.replacePath(super.determineTargetUrl(request, response));
        }
        localRequestbuilder.replaceQuery(null);
        return localRequestbuilder.build().toUriString();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.handle(request, response, authentication);
    }
}
