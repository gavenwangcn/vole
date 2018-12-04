package com.github.vole.passport.common.config;

import com.github.vole.passport.common.cookie.CookieOperations;
import com.github.vole.passport.common.cookie.DefaultCookieOperations;
import com.github.vole.passport.common.cookie.PassportCookieServices;
import com.github.vole.passport.common.filter.PassportClientAuthProcessingFilter;
import com.github.vole.passport.common.filter.PassportCookieAuthenticationFilter;
import com.github.vole.passport.common.handler.ClientAuthenticationSuccessHandler;
import com.github.vole.passport.common.handler.ClientLoginUrlAuthenticationEntryPoint;
import com.github.vole.passport.common.handler.ClientLogoutSuccessHandler;
import com.github.vole.passport.common.permission.AccessPermission;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.util.Collections;

public class PassportSsoSecurityConfigurer {

    private ApplicationContext applicationContext;

    PassportSsoSecurityConfigurer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void configure(HttpSecurity http) throws Exception {
        PassportSsoProperties sso = this.applicationContext
                .getBean(PassportSsoProperties.class);
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //去除security session管理
        http.sessionManagement().disable();
        http.securityContext().disable();
        http.requestCache().disable();
        http.rememberMe().disable();
        http.apply(new PassportClientAuthConfigurer(authFilter(sso),ssoFilter(sso)));
        addAuthenticationEntryPoint(http, sso);
        addLogoutSuccessHandler(http,sso);
        String[] permissions = applicationContext.getBeanNamesForType(AccessPermission.class);
        if(permissions!=null&&permissions.length>0){
            String beanName = permissions[0];
            String call = "@"+beanName+"."+"hasPermission(request,authentication)";
            http.authorizeRequests().anyRequest().access(call);
        }
    }

    private void addAuthenticationEntryPoint(HttpSecurity http, PassportSsoProperties sso)
            throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> exceptions = http.exceptionHandling();
        ContentNegotiationStrategy contentNegotiationStrategy = http
                .getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy, MediaType.APPLICATION_XHTML_XML,
                new MediaType("image", "*"), MediaType.TEXT_HTML, MediaType.TEXT_PLAIN);
        preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        ClientLoginUrlAuthenticationEntryPoint entryPoint =
                new ClientLoginUrlAuthenticationEntryPoint(sso.getLoginPath());
        if(!StringUtils.isEmpty(sso.getTargetParamName())){
            entryPoint.setTargetParamName(sso.getTargetParamName());
        }
        exceptions.defaultAuthenticationEntryPointFor(entryPoint, preferredMatcher);
        // When multiple entry points are provided the default is the first one
        //ajax 不支持
        exceptions.defaultAuthenticationEntryPointFor(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
    }

    private void addLogoutSuccessHandler(HttpSecurity http, PassportSsoProperties sso) throws Exception {
        ClientLogoutSuccessHandler handler = new ClientLogoutSuccessHandler();
        handler.setCookieOperations(createCookieOperations(sso));
        if(!StringUtils.isEmpty(sso.getLogoutServiceParamName())){
            handler.setLogoutServiceParamName(sso.getLogoutServiceParamName());
        }
        if(!StringUtils.isEmpty(sso.getServerLogoutPath())){
            handler.setServerLogoutUrl(sso.getServerLogoutPath());
        }

        if(!StringUtils.isEmpty(sso.getSsoLogoutPath())){
            handler.setSsoLogoutPath(sso.getSsoLogoutPath());
        }
        http.logout().logoutSuccessHandler(handler);
    }

    private PassportClientAuthProcessingFilter ssoFilter(
            PassportSsoProperties sso) {

        PassportCookieServices passportCookieServices = this.applicationContext
                .getBean(PassportCookieServices.class);
        PassportClientAuthProcessingFilter passportClientFilter = new PassportClientAuthProcessingFilter(
                sso.getLoginPath());
        passportClientFilter.setPassportCookieServices(passportCookieServices);
        if(!StringUtils.isEmpty(sso.getServiceParamName())){
            passportClientFilter.setServiceParamName(sso.getServiceParamName());
        }
        if(!StringUtils.isEmpty(sso.getTargetParamName())){
            passportClientFilter.setTargetParamName(sso.getTargetParamName());
        }
        if(!StringUtils.isEmpty(sso.getTokenParamName())){
            passportClientFilter.setTokenParamName(sso.getTokenParamName());
        }
        if(!StringUtils.isEmpty(sso.getDefaultRedirectUrl())){
            passportClientFilter.setRedirectUrl(sso.getDefaultRedirectUrl());
        }
        passportClientFilter.setCookieOperations(createCookieOperations(sso));
        ClientAuthenticationSuccessHandler handler = new ClientAuthenticationSuccessHandler();
        if(!StringUtils.isEmpty(sso.getTargetParamName())){
            handler.setTargetParamName(sso.getTargetParamName());
        }
        passportClientFilter.setAuthenticationSuccessHandler(handler);
        passportClientFilter.setApplicationEventPublisher(this.applicationContext);
        return passportClientFilter;
    }


    private PassportCookieAuthenticationFilter authFilter(
            PassportSsoProperties sso) {

        PassportCookieServices passportCookieServices = this.applicationContext
                .getBean(PassportCookieServices.class);
        PassportCookieAuthenticationFilter passportAuthFilter = new PassportCookieAuthenticationFilter();
        passportAuthFilter.setPassportCookieServices(passportCookieServices);
        passportAuthFilter.setCookieOperations(createCookieOperations(sso));
        ClientAuthenticationSuccessHandler handler = new ClientAuthenticationSuccessHandler();
        if(!StringUtils.isEmpty(sso.getTargetParamName())){
            handler.setTargetParamName(sso.getTargetParamName());
        }
        return passportAuthFilter;
    }

    private static class PassportClientAuthConfigurer
            extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private PassportClientAuthProcessingFilter filter;

        private PassportCookieAuthenticationFilter authFilter;

        PassportClientAuthConfigurer(PassportCookieAuthenticationFilter authFilter,
                                     PassportClientAuthProcessingFilter filter) {
            this.authFilter=authFilter;
            this.filter = filter;
        }

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            PassportClientAuthProcessingFilter ssoFilter = this.filter;
            PassportCookieAuthenticationFilter cookieFilter = this.authFilter;
            builder.addFilterBefore(cookieFilter,
                    AbstractPreAuthenticatedProcessingFilter.class);
            builder.addFilterAfter(ssoFilter,
                    AbstractPreAuthenticatedProcessingFilter.class);

        }

    }

    private CookieOperations createCookieOperations(PassportSsoProperties sso) {

        DefaultCookieOperations cookieOperations = new DefaultCookieOperations();
        if(!StringUtils.isEmpty(sso.getCookieDomain())){
            cookieOperations.setDomain(sso.getCookieDomain());
        }
        if(!StringUtils.isEmpty(sso.getCookieName())){
            cookieOperations.setCookieName(sso.getCookieName());
        }
        if(!StringUtils.isEmpty(sso.getCookiePath())){
            cookieOperations.setCookiePath(sso.getCookiePath());
        }
        if(sso.getCookieMaxage()!=0){
            cookieOperations.setCookieMaxage(sso.getCookieMaxage());
        }
        return cookieOperations;
    }
}
