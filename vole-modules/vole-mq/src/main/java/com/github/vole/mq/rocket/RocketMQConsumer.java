package com.github.vole.mq.rocket;

import com.github.vole.mq.rocket.config.RocketmqEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Rocket Demo: 消费者
 */
@Slf4j
@Component
public class RocketMQConsumer {
    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && #event.msgs[0].tags=='TagA'")
    public void rocketmqMsgListen(RocketmqEvent event) {
        //      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            log.info("Receive topic：" + event.getTopic(0)
                + " message id:" + event.getMsgs().get(0).getMsgId()
                + " message:" + new String(event.getMsgs().get(0).getBody()), "utf-8");
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}