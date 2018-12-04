package com.github.vole.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉服务配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sms.dingtalk")
public class DingTalkPropertiesConfig {
    /**
     * webhook
     */
    private String webhook;

}
