package com.sy.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * 手动确认消息监听类
 *
 * @author lfeiyang
 * @since 2022-05-08 1:34
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {
    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            byte[] messageBody = message.getBody();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(messageBody);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            Map<String, String> msgMap = (Map<String, String>) in.readObject();

            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");

            if ("DirectQueue".equals(message.getMessageProperties().getConsumerQueue())) {
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行DirectQueue中的消息的业务处理流程......");
            }

            if ("fanout.A".equals(message.getMessageProperties().getConsumerQueue())) {
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行fanout.A中的消息的业务处理流程......");
            }

            channel.basicAck(deliveryTag, true);  // 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            // channel.basicReject(deliveryTag, true); // 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
}
