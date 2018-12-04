package com.github.vole.gateway.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul限流扩展--策略生成的key
 * 因为默认的策略key 在循环policy-list 的配置时会将当前的url对应的策略 policy 覆盖掉
 */
@Slf4j
@Configuration
public class RateLimitKeyConfig  {

    /*@Qualifier("RateLimit")
    @Bean
    public Cache<String, GridBucketState> cache (){
        return new Cache<String, GridBucketState>();
    }*/
    /**
     * control the key strategy beyond the options offered by the type property
     * @param properties
     * @param rateLimitUtils
     * @return
     */
    @Bean
    public RateLimitKeyGenerator ratelimitKeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {

        return new DefaultRateLimitKeyGenerator(properties, rateLimitUtils) {

            @Override
            public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
                String key = super.key(request, route, policy);
                log.info(key + ":" + request.getMethod());
                return key + ":" + request.getMethod();
            }
        };
    }

    /**
     * handle the errors differently
     * @return
     */
    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                log.error("handleSaveError: "+ key);
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                log.error("handleFetchError: "+ key);
            }

            @Override
            public void handleError(String msg, Exception e) {
                log.error("handleError: "+ msg);
            }
        };
    }
}
