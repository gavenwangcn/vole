package com.github.vole.passport.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnablePassportClient
@EnableConfigurationProperties(PassportSsoProperties.class)
@Import({PassportSsoDefaultConfiguration.class, PassportSsoCustomConfiguration.class})
public @interface EnablePassportSso {

}
