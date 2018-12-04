package com.github.vole.passport.common.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PassportClientConfiguration.class)
public @interface EnablePassportClient {

}
