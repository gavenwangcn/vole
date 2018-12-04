package com.github.vole.message.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 阿里大鱼短息服务配置
 */
@Data
@Configuration
@ConditionalOnExpression("!'${sms.aliyun}'.isEmpty()")
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsAliyunPropertiesConfig {
    /**
     * 应用ID
     */
    private String accessKey;

    /**
     * 应用秘钥
     */
    private String secretKey;

    /**
     * 短信模板配置
     */
    private Map<String, String> channels;
}
