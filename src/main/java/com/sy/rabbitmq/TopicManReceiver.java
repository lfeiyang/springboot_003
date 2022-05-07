package com.sy.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TopicMan主题接收器
 *
 * @author lfeiyang
 * @since 2022-05-08 0:34
 */
@Component
@RabbitListener(queues = "topic.man")
public class TopicManReceiver {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        System.out.println("TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }
}
