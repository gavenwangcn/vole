package com.github.vole.mq.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Kafka Demo: 生产者
 */
//@Component
//@EnableScheduling // todo 用时打开
public class KafkaSender {

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static int count =1;
    /**
     * 发送消息到kafka,主题为defaultTopic
     */
    //然后每隔10秒执行一次
//    @Scheduled(fixedRate = 1000 * 10) // todo 用时打开
    public void sendTest(){
        String message = count++ + ">>>Send topic:"+defaultTopic+" message: hello world "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        ListenableFuture future = kafkaTemplate.send(defaultTopic, message);
        future.addCallback(o -> System.out.println("success: "+message)
            , throwable -> System.out.println("fail: " + message));
    }
}