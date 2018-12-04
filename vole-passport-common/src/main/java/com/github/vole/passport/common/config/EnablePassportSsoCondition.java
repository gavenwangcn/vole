package com.github.vole.passport.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

class EnablePassportSsoCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		String[] enablers = context.getBeanFactory()
				.getBeanNamesForAnnotation(EnablePassportSso.class);
		ConditionMessage.Builder message = ConditionMessage
				.forCondition("@EnablePassport2Sso Condition");
		for (String name : enablers) {
			if (context.getBeanFactory().isTypeMatch(name,
					WebSecurityConfigurerAdapter.class)) {
				return ConditionOutcome.match(message
						.found("@EnablePassport2Sso annotation on WebSecurityConfigurerAdapter")
						.items(name));
			}
		}
		return ConditionOutcome.noMatch(message.didNotFind(
				"@EnablePassport2Sso annotation " + "on any WebSecurityConfigurerAdapter")
				.atAll());
	}

}
