package com.github.vole.passport.common.config.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security.passport.server")
public class PassportServerProperties {

    private String loginPage = "/passport/login";

    private String loginProcess = "/passport/form";

    private String logout = "/logout";

    private String logoutSuccess="/passport/login";
}
