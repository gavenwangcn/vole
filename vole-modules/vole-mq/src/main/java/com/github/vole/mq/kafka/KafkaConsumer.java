package com.github.vole.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka Demo: 消费者
 */
@Slf4j
//@Component  // todo 用时打开
public class KafkaConsumer {

    @Value("${spring.kafka.template.default-topic}")
    private static final String defaultTopic = "test1";
    private static int number =1;
    /**
     * 监听test主题,有消息就读取
     * @param message
     */
//    @KafkaListener(topics = {defaultTopic})
    public void consumer(String message){
        log.info(number++ + ">>>Receiver topic:"+ defaultTopic +" message:" + message);
    }
}
