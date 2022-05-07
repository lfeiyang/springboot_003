package com.sy.config.rabbitmq;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

/**
 * RabbitMq消息确认回调函数
 *
 * @author lfeiyang
 * @since 2022-05-08 0:56
 */
@Configuration
public class RabbitConfig {
    // 先从总体的情况分析，推送消息存在四种情况：
    // ①消息推送到server，但是在server里找不到交换机
    // 结论： ①这种情况触发的是 ConfirmCallback 回调函数
    // ②消息推送到server，找到交换机了，但是没找到队列
    // 结论：②这种情况触发的是 ConfirmCallback和RetrunCallback两个回调函数。
    // ③消息推送到sever，交换机和队列啥都没找到
    // 结论： ③这种情况触发的是 ConfirmCallback 回调函数。
    // ④消息推送成功
    // 结论： ④这种情况触发的是 ConfirmCallback 回调函数。


    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
                System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
                System.out.println("ConfirmCallback:     " + "原因：" + cause);
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(@Nonnull ReturnedMessage returned) {
                System.out.println("ReturnCallback:     " + "消息：" + returned.getMessage());
                System.out.println("ReturnCallback:     " + "回应码：" + returned.getReplyCode());
                System.out.println("ReturnCallback:     " + "回应信息：" + returned.getReplyText());
                System.out.println("ReturnCallback:     " + "交换机：" + returned.getExchange());
                System.out.println("ReturnCallback:     " + "路由键：" + returned.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }
}
