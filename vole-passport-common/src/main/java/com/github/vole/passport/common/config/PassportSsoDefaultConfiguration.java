package com.github.vole.passport.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Conditional(PassportSsoDefaultConfiguration.NeedsWebSecurityCondition.class)
public class PassportSsoDefaultConfiguration extends WebSecurityConfigurerAdapter {

	private final ApplicationContext applicationContext;

	public PassportSsoDefaultConfiguration(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
		new PassportSsoSecurityConfigurer(this.applicationContext).configure(http);
	}

	protected static class NeedsWebSecurityCondition extends EnablePassportSsoCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			return ConditionOutcome.inverse(super.getMatchOutcome(context, metadata));
		}

	}

}
