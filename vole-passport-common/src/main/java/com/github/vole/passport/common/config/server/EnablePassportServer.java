package com.github.vole.passport.common.config.server;

import com.github.vole.passport.common.config.EnablePassportClient;
import com.github.vole.passport.common.config.PassportSsoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnablePassportClient
@EnableConfigurationProperties({PassportSsoProperties.class,PassportServerProperties.class})
@Import({PassportServerConfiguration.class})
public @interface EnablePassportServer {

}
