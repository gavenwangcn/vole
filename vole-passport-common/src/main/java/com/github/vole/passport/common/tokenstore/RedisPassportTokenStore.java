package com.github.vole.passport.common.tokenstore;

import com.github.vole.passport.common.token.DefaultPassportToken;
import com.github.vole.passport.common.token.PassportToken;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisPassportTokenStore implements PassportTokenStore {

    private static final String PASSPORT_TOKEN = "passport_token:";

    private RedisTemplate<String, Object> redisTemplate;

    private TokenKeyGenerator keyGenerator = new DefaultTokenKeyGenerator();

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PassportToken storeToken(PassportToken token) {
        DefaultPassportToken defaultToken = (DefaultPassportToken) token;
        String key = keyGenerator.extractKey(token);
        defaultToken.setKey(key);
        Object value = redisTemplate.opsForValue().get(PASSPORT_TOKEN + key);
        if (!(value instanceof PassportToken)) {
            redisTemplate.opsForValue().set(PASSPORT_TOKEN + key, token);
        }
        if (defaultToken.getExpire() > 0) {
            redisTemplate.expire(PASSPORT_TOKEN + key, defaultToken.getExpire(), TimeUnit.SECONDS);
        }
        return token;
    }

    @Override
    public boolean deleteToken(PassportToken token) {
        String key = keyGenerator.extractKey(token);
        return redisTemplate.delete(PASSPORT_TOKEN + key);
    }

    @Override
    public boolean deleteToken(String tokenKey) {
        return redisTemplate.delete(PASSPORT_TOKEN + tokenKey);
    }

    @Override
    public PassportToken getToken(String tokenKey) {
        Object value = redisTemplate.opsForValue().get(PASSPORT_TOKEN + tokenKey);
        if (value instanceof PassportToken) {
            return (PassportToken) value;
        }
        return null;
    }
}
