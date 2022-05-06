package com.sy.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息接收监听类
 *
 * @author lfeiyang
 * @since 2022-05-06 23:47
 */
@Slf4j
@Component
// 监听的队列名称 TestDirectQueue
@RabbitListener(queues = "DirectQueue")
public class DirectReceiver {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        log.info("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
