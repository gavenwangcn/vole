package com.github.vole.passport.common.config;

import com.github.vole.passport.common.filter.PassportClientContextFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PassportClientConfiguration {

	@Bean
	public PassportClientContextFilter passportClientContextFilter() {
		PassportClientContextFilter filter = new PassportClientContextFilter();
		return filter;
	}

	@Bean
	public FilterRegistrationBean<PassportClientContextFilter> passportClientContextFilterRegistration(
			PassportClientContextFilter filter, SecurityProperties security) {
		FilterRegistrationBean<PassportClientContextFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.setOrder(security.getFilter().getOrder() - 10);
		return registration;
	}

}
