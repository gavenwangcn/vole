package com.github.vole.passport.server.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vole.common.bean.config.FilterIgnorePropertiesConfig;
import com.github.vole.passport.common.config.server.EnablePassportServer;
import com.github.vole.passport.common.utils.jackson2.PassportJackson2Module;
import com.github.vole.passport.common.tokenstore.PassportTokenStore;
import com.github.vole.passport.common.tokenstore.RedisPassportTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.jackson2.WebJackson2Module;

import javax.annotation.Resource;

@Configuration
@EnablePassportServer
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Resource
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.authorizeRequests();
        filterIgnorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CoreJackson2Module());
        mapper.registerModule(new WebJackson2Module());
        mapper.registerModule(new PassportJackson2Module());
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        RedisSerializer rs = new GenericJackson2JsonRedisSerializer(mapper);
        template.setDefaultSerializer(rs);
        return template;
    }

    @Bean
    public PassportTokenStore passportTokenStore(RedisTemplate redisTemplate){
        RedisPassportTokenStore tokenStore  =new RedisPassportTokenStore();
        tokenStore.setRedisTemplate(redisTemplate);
        return tokenStore;
    }
}
