package com.sy.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * RabbitMq控制层
 *
 * @author lfeiyang
 * @since 2022-05-06 23:38
 */
@RestController
public class SendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "direct message, hello!";

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", map);

        return "ok";
    }

    @GetMapping("/sendTopicMessageMan")
    public String sendTopicMessageMan() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";

        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);

        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);

        return "ok";
    }

    @GetMapping("/sendTopicMessageWoman")
    public String sendTopicMessageWoman() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";

        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);

        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);

        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("fanoutExchange", "", map);

        return "ok";
    }

    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("non-existent-exchange", "DirectRouting", map);

        return "ok";
    }

    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("lonelyDirectExchange", "DirectRouting", map);

        return "ok";
    }

}
