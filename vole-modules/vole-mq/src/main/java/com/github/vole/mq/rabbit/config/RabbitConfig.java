package com.github.vole.mq.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ的配置类:用来配置队列、交换器、路由等高级信息
 */
@Configuration
public class RabbitConfig {

    /**
     * 配置队列
     * @return
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

}