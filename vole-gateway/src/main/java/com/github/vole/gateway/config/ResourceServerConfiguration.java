package com.github.vole.gateway.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vole.common.bean.config.FilterIgnorePropertiesConfig;
import com.github.vole.gateway.handler.VoleAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.jackson2.WebJackson2Module;

import javax.annotation.Resource;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Resource
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private VoleAccessDeniedHandler voleAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        filterIgnorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        //registry.anyRequest().authenticated();
        registry.anyRequest().access("@volePermissionService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(voleAccessDeniedHandler);
    }

    /**
     * 配置解决 spring-security-oauth问题
     * https://github.com/spring-projects/spring-security-oauth/issues/730
     *
     * @param applicationContext ApplicationContext
     * @return OAuth2WebSecurityExpressionHandler
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        DelegatingPasswordEncoder delegatingPasswordEncoder =
                (DelegatingPasswordEncoder)  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return delegatingPasswordEncoder;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        RedisSerializer rs = new GenericJackson2JsonRedisSerializer();
        //template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        template.setDefaultSerializer(rs);
        return template;
    }
}