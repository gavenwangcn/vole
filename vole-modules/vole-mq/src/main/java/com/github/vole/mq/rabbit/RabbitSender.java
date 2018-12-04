package com.github.vole.mq.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Rabbit Demo: 生产者
 */
@Component
@RestController
public class RabbitSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    private static int count = 1;

    @RequestMapping("/send")
    public void send() {
        String context = count++ + ">>>hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
