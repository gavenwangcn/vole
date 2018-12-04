package com.github.vole.passport.common.config.server;

import com.github.vole.passport.common.config.PassportSsoProperties;
import com.github.vole.passport.common.cookie.CookieOperations;
import com.github.vole.passport.common.cookie.DefaultCookieOperations;
import com.github.vole.passport.common.filter.PassportServerContextFilter;
import com.github.vole.passport.common.handler.*;
import com.github.vole.passport.common.tokenstore.PassportTokenStore;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;


public class PassportSecurityConfigurer {

    private ApplicationContext applicationContext;

    PassportSecurityConfigurer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void configure(HttpSecurity http) throws Exception {
        PassportSsoProperties sso = this.applicationContext
                .getBean(PassportSsoProperties.class);
        PassportServerProperties server = this.applicationContext
                .getBean(PassportServerProperties.class);
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //去除security session管理
        http.sessionManagement().disable();
        http.securityContext().disable();
        http.formLogin().loginPage(server.getLoginPage())
                .loginProcessingUrl(server.getLoginProcess())
                .successHandler(addSuccessHandler(http,sso))
                .failureHandler(addFailHandler(http,sso,server))
                .and()
                .logout().logoutUrl(server.getLogout())
                .logoutSuccessHandler(addLogoutSuccessHandler(http,sso))
                .logoutSuccessUrl(server.getLogoutSuccess())
                .invalidateHttpSession(true)
                .clearAuthentication(true);
        http.addFilterBefore(serverFilter(sso),
                AbstractPreAuthenticatedProcessingFilter.class);
    }


    private PassportTokenProcessingHandle addSuccessHandler(HttpSecurity http, PassportSsoProperties sso) throws Exception {
        PassportTokenStore passportTokenStore = this.applicationContext
                .getBean(PassportTokenStore.class);
        PassportTokenProcessingHandle handler = new PassportTokenProcessingHandle();
        if (!StringUtils.isEmpty(sso.getServiceParamName())) {
            handler.setServiceParamName(sso.getServiceParamName());
        }
        if (!StringUtils.isEmpty(sso.getTargetParamName())) {
            handler.setTargetParamName(sso.getTargetParamName());
        }
        if (!StringUtils.isEmpty(sso.getTokenParamName())) {
            handler.setTokenParamName(sso.getTokenParamName());
        }
        handler.setPassportTokenStore(passportTokenStore);
        return handler;
    }

    private ServerAuthenticationFailureHandler addFailHandler(HttpSecurity http, PassportSsoProperties sso ,PassportServerProperties server) throws Exception {
        ServerAuthenticationFailureHandler handler = new ServerAuthenticationFailureHandler(server.getLoginPage());
        if (!StringUtils.isEmpty(sso.getServiceParamName())) {
            handler.setServiceParamName(sso.getServiceParamName());
        }
        if (!StringUtils.isEmpty(sso.getTargetParamName())) {
            handler.setTargetParamName(sso.getTargetParamName());
        }
        return handler;
    }


    private ServerLogoutSuccessHandler addLogoutSuccessHandler(HttpSecurity http, PassportSsoProperties sso) throws Exception {
        PassportTokenStore passportTokenStore = this.applicationContext
                .getBean(PassportTokenStore.class);
        ServerLogoutSuccessHandler handler = new ServerLogoutSuccessHandler();
        handler.setPassportTokenStore(passportTokenStore);
        handler.setCookieOperations(createCookieOperations(sso));
        if (!StringUtils.isEmpty(sso.getLogoutServiceParamName())) {
            handler.setLogoutServiceParamName(sso.getLogoutServiceParamName());
        }
        if (!StringUtils.isEmpty(sso.getLogoutServiceParamName())) {
            handler.setLogoutServiceParamName(sso.getLogoutServiceParamName());
        }
        return handler;
    }

    private PassportServerContextFilter serverFilter(
            PassportSsoProperties sso) {
        PassportServerContextFilter passportFilter = new PassportServerContextFilter();
        if(!StringUtils.isEmpty(sso.getServiceParamName())){
            passportFilter.setServiceParamName(sso.getServiceParamName());
        }
        if(!StringUtils.isEmpty(sso.getTargetParamName())){
            passportFilter.setTargetParamName(sso.getTargetParamName());
        }
        return passportFilter;
    }

    private CookieOperations createCookieOperations(PassportSsoProperties sso) {

        DefaultCookieOperations cookieOperations = new DefaultCookieOperations();
        if (!StringUtils.isEmpty(sso.getCookieDomain())) {
            cookieOperations.setDomain(sso.getCookieDomain());
        }
        if (!StringUtils.isEmpty(sso.getCookieName())) {
            cookieOperations.setCookieName(sso.getCookieName());
        }
        if (!StringUtils.isEmpty(sso.getCookiePath())) {
            cookieOperations.setCookiePath(sso.getCookiePath());
        }
        if (sso.getCookieMaxage() != 0) {
            cookieOperations.setCookieMaxage(sso.getCookieMaxage());
        }
        return cookieOperations;
    }
}
