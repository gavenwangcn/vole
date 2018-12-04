package com.github.vole.auth.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vole.auth.util.jackson2.AuthJackson2Module;
import com.github.vole.auth.util.UserDetailsImpl;
import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.jackson2.WebJackson2Module;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务器逻辑实现
 */

@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //token增强配置
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .tokenStore(redisTokenStore())
                //.tokenStore(new RedisTokenStore(jedisConnectionFactory))
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        AccessTokenConverter jwtAccessTokenConverter = new AccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(CommonConstant.SIGN_KEY);
        return jwtAccessTokenConverter;
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
        mapper.registerModule(new AuthJackson2Module());
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        RedisSerializer rs = new GenericJackson2JsonRedisSerializer(mapper);
        //template.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        template.setDefaultSerializer(rs);
        return template;
    }

    /**
     * tokenstore 定制化处理
     *
     * @return TokenStore
     * 1. 如果使用的 redis-cluster 模式请使用 VoleRedisTokenStore
     * VoleRedisTokenStore tokenStore = new VoleRedisTokenStore();
     * tokenStore.setRedisTemplate(redisTemplate);
     */
    @Bean
    public TokenStore redisTokenStore() {
        //VoleRedisTokenStore tokenStore = new VoleRedisTokenStore(redisConnectionFactory);
        //tokenStore.setPrefix(SecurityConstants.VOLE_PREFIX);
        VoleRedisTokenStore tokenStore = new VoleRedisTokenStore();
        tokenStore.setRedisTemplate(redisTemplate(redisConnectionFactory));
        return tokenStore;
    }

    /**
     * jwt 生成token 定制化处理
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(2);
            additionalInfo.put("license", SecurityConstants.Vole_LICENSE);
            UserDetailsImpl user = (UserDetailsImpl) authentication.getUserAuthentication().getPrincipal();
            if (user != null) {
                additionalInfo.put("userId", user.getUserId());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

}
