package com.github.vole.passport.common.filter;

import com.github.vole.passport.common.auth.PassportAuthentication;
import com.github.vole.passport.common.auth.PassportAuthenticationDetailsSource;
import com.github.vole.passport.common.contants.PassportConstants;
import com.github.vole.passport.common.cookie.CookieOperations;
import com.github.vole.passport.common.cookie.DefaultCookieOperations;
import com.github.vole.passport.common.cookie.PassportCookieServices;
import com.github.vole.passport.common.exception.ClientRedirectRequiredException;
import com.github.vole.passport.common.exception.PassportAuthFailureException;
import org.springframework.context.*;
import org.springframework.util.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PassportClientAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {


    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new PassportAuthenticationDetailsSource();

    private PassportCookieServices passportCookieServices;

    private String tokenParamName;

    private String serviceParamName;

    private String targetParamName;

    private String redirectUrl;

    private String defaultFilterProcessesUrl;

    private CookieOperations cookieOperations;

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setTokenParamName(String tokenParamName) {
        this.tokenParamName = tokenParamName;
    }

    public void setTargetParamName(String targetParamName) {
        this.targetParamName = targetParamName;
    }

    public void setServiceParamName(String serviceParamName) {
        this.serviceParamName = serviceParamName;
    }

    public void setCookieOperations(CookieOperations cookieOperations) {
        this.cookieOperations = cookieOperations;
    }

    public void setPassportCookieServices(PassportCookieServices passportCookieServices) {
        this.passportCookieServices = passportCookieServices;
    }
    private ApplicationEventPublisher applicationEventPublisher;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public PassportClientAuthProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.defaultFilterProcessesUrl=defaultFilterProcessesUrl;
    }

    @Override
    public void afterPropertiesSet() {

    }

    private void publish(ApplicationEvent event) {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(event);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        logger.debug("start client passport authentication");
        String httpTokenKey = obtainToken(request);
        if (httpTokenKey != null) {
            PassportAuthentication result = passportCookieServices.loadAuthentication(httpTokenKey);
            if (result == null) {
                throw new PassportAuthFailureException("error cookiestore not load any value");
            }
            cookieOperations.storeCookie(response, httpTokenKey);
            publish(new AuthenticationSuccessEvent(result));
            logger.debug("end client passport authentication");
            return result;
        } else {
            String targetUrl = obtainTarget(request);
            Map<String, String> queryParam = new HashMap<String, String>();
            String serviceUrl = buildFullServiceUrl(request);
            queryParam.put(getServiceParamName(), serviceUrl);
            queryParam.put(getTargetParamName(), targetUrl);
            throw new ClientRedirectRequiredException(getRedirectUrl(), queryParam);
        }

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }

    }

    protected String obtainToken(HttpServletRequest request) {

        return request.getParameter(getTokenParamName());
    }

    protected String obtainTarget(HttpServletRequest request) {

        return request.getParameter(getTargetParamName());
    }

    protected String getTokenParamName() {
        return !StringUtils.isEmpty(tokenParamName) ? tokenParamName : PassportConstants.DEFAULT_TOKEN_PARAM;
    }

    protected String getServiceParamName() {
        return !StringUtils.isEmpty(serviceParamName) ? serviceParamName : PassportConstants.DEFAULT_SERVICE_PARAM;
    }

    protected String getTargetParamName() {
        return !StringUtils.isEmpty(targetParamName) ? targetParamName : PassportConstants.DEFAULT_TARGET_PARAM;
    }

    protected String getRedirectUrl() {
        return !StringUtils.isEmpty(redirectUrl) ? redirectUrl : PassportConstants.DEFAULT_REDIRECT_URL;
    }

    private static String buildFullServiceUrl(HttpServletRequest request) {
        UriComponentsBuilder builder =UriComponentsBuilder
                .fromHttpUrl(request.getRequestURL().toString()).replaceQuery(null);
        return  builder.toUriString();
    }
}
