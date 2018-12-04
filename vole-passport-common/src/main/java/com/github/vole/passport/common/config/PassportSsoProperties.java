package com.github.vole.passport.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security.passport")
public class PassportSsoProperties {

    public static final String DEFAULT_LOGIN_PATH = "/login";

    private String loginPath = DEFAULT_LOGIN_PATH;

    private String serverLogoutPath;

    private String ssoLogoutPath;

    private String defaultRedirectUrl;

    private String serviceParamName;

    private String tokenParamName;

    private String targetParamName;

    private String logoutServiceParamName;

    private String cookieName;

    private String cookieDomain;

    private int cookieMaxage;

    private String cookiePath;
}
