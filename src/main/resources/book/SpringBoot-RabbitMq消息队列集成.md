# <font face=幼圆 color=white>[Sprongboot集成RabbitMq消息队列](https://spring.io/projects/spring-amqp)</font>

![](D:\project\springboot_003\src\main\resources\book\image\rabbitMq.png)

<font face=幼圆 color=white></font>



## <font face=幼圆 color=white>一、Direct Exchange直连交换机</font>

<font size=6 face=幼圆 color=red>Routing</font>

​		<font face=幼圆 color=white>直连型交换机，根据消息携带的路由键将消息投递给对应队列。</font>

​		<font face=幼圆 color=white>大致流程，有一个队列绑定到一个直连交换机上，同时赋予一个路由键 routing key 。
然后当一个消息携带着路由值为X，这个消息通过生产者发送给交换机时，交换机就会根据这个路由值X去寻找绑定值也是X的队列。</font>

### <font face=幼圆 color=white>1.1.Producer生产者</font>

```java
package com.sy.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连型交换机-配置
 *
 * @author lfeiyang
 * @since 2022-05-06 23:28
 */
@Configuration
public class DirectRabbitConfig {
    /**
     * 队列
     *
     * @return org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue DirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("DirectQueue", true);
    }

    /**
     * Direct交换机
     *
     * @return org.springframework.amqp.core.DirectExchange
     **/
    @Bean
    DirectExchange DirectExchange() {
        return new DirectExchange("DirectExchange", true, false);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键
     *
     * @return Binding
     **/
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(DirectQueue()).to(DirectExchange()).with("DirectRouting");
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }
}
```



### <font face=幼圆 color=white>1.2.Customer消费者</font>

```java
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
```



### <font face=幼圆 color=white>1.3.消息推送</font>

​		<font face=幼圆 color=white>接口调用，模拟发送信息。</font>

​		<font face=幼圆 color=red>可以看到是实现了轮询的方式对消息进行消费，而且不存在重复消费</font>

![](D:\project\springboot_003\src\main\resources\book\image\rabbit-direct-producer.png)

```java
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
}
```



## <font face=幼圆 color=white>二、Topic Exchange 主题交换机</font>

<font size=6 face=幼圆 color=red>Topic</font>

### <font face=幼圆 color=white>2.1.Producer生产者</font>

```java
package com.sy.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题交换机
 *
 * @author lfeiyang
 * @since 2022-05-08 0:24
 */
@Configuration
public class TopicRabbitConfig {
    //绑定键
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.woman);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }
}
```



### <font face=幼圆 color=white>2.2.Customer消费者</font>

#### <font face=幼圆 color=white>2.2.1.topic.man</font>

```java
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
```



#### <font face=幼圆 color=white>2.2.2.topic.#</font>

```java
package com.sy.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 主题接收器
 *
 * @author lfeiyang
 * @since 2022-05-08 0:37
 */
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }
}
```



### <font face=幼圆 color=white>2.3.消息推送</font>

```java
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
```



## <font face=幼圆 color=white>三、Fanout Exchang 扇型交换机</font>

<font size=6 face=幼圆 color=red>Fanout</font>

### <font face=幼圆 color=white>3.1.Producer生产者</font>

```java
package com.sy.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 扇形交换机
 *
 * @author lfeiyang
 * @since 2022-05-08 0:44
 */
@Configuration
public class FanoutRabbitConfig {
    /**
     * 创建三个队列 ：fanout.A   fanout.B  fanout.C
     * 将三个队列都绑定在交换机 fanoutExchange 上
     * 因为是扇型交换机, 路由键无需配置,配置也不起作用
     */


    @Bean
    public Queue queueA() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue queueB() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue queueC() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }
}
```



### <font face=幼圆 color=white>3.2.Customer消费者</font>

```java
package com.sy.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扇型交换机接收器A
 *
 * @author lfeiyang
 * @since 2022-05-08 0:49
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        System.out.println("FanoutReceiverA消费者收到消息  : " + testMessage.toString());
    }
}
```



```java
package com.sy.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扇型交换机接收器B
 *
 * @author lfeiyang
 * @since 2022-05-08 0:49
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        System.out.println("FanoutReceiverB消费者收到消息  : " + testMessage.toString());
    }
}
```



```java
package com.sy.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扇型交换机接收器C
 *
 * @author lfeiyang
 * @since 2022-05-08 0:49
 */
@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiverC {
    @RabbitHandler
    public void process(Map<?, ?> testMessage) {
        System.out.println("FanoutReceiverC消费者收到消息  : " + testMessage.toString());
    }
}
```



### <font face=幼圆 color=white>3.3.消息推送</font>

```java
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
```



## <font face=幼圆 color=white>四、消息确认</font>

### <font face=幼圆 color=white>4.1.推送消息存在四种情况</font>

<font face=幼圆 color=white>①消息推送到server，但是在server里找不到交换机</font>

<font face=幼圆 color=white>结论： ①这种情况触发的是 ConfirmCallback 回调函数</font>



<font face=幼圆 color=white>②消息推送到server，找到交换机了，但是没找到队列</font>

<font face=幼圆 color=white>结论：②这种情况触发的是 ConfirmCallback和RetrunCallback两个回调函数。</font>



<font face=幼圆 color=white>③消息推送到sever，交换机和队列啥都没找到</font>

<font face=幼圆 color=white>结论： ③这种情况触发的是 ConfirmCallback 回调函数。</font>



<font face=幼圆 color=white>④消息推送成功</font>

<font face=幼圆 color=white>结论： ④这种情况触发的是 ConfirmCallback 回调函数。</font>



```java
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
```



### <font face=幼圆 color=white>4.2.消息确认回调函数</font>

```java
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
```



### <font face=幼圆 color=white>4.3.消息确认机制三种模式</font>

#### <font face=幼圆 color=white>4.3.1.自动确认</font>

<font face=幼圆 color=white>这也是默认的消息确认情况。 AcknowledgeMode.NONE</font>
<font face=幼圆 color=white>RabbitMQ成功将消息发出（即将消息成功写入TCP Socket）中立即认为本次投递已经被正确处理，不管消费者端是否成功处理本次投递。</font>

<font face=幼圆 color=white>所以这种情况如果消费端消费逻辑抛出异常，也就是消费端没有处理成功这条消息，那么就相当于丢失了消息。</font>

<font face=幼圆 color=white>一般这种情况我们都是使用try catch捕捉异常后，打印日志用于追踪数据，这样找出对应数据再做后续处理。</font>



#### <font face=幼圆 color=white>4.3.2.根据情况确认</font>



#### <font face=幼圆 color=white>4.3.3.手动确认</font>

<font face=幼圆 color=white>这个比较关键，也是我们配置接收消息确认机制时，多数选择的模式。</font>

<font face=幼圆 color=white>消费者收到消息后，手动调用basic.ack/basic.nack/basic.reject后，RabbitMQ收到这些消息后，才认为本次投递成功。</font>

<font face=幼圆 color=white>basic.ack用于肯定确认 </font>

<font face=幼圆 color=white>basic.nack用于否定确认（注意：这是AMQP 0-9-1的RabbitMQ扩展） </font>

```text
第一个参数依然是当前消息到的数据的唯一id;
第二个参数是指是否针对多条消息；如果是true，也就是说一次性针对当前通道的消息的tagID小于当前这条消息的，
都拒绝确认。
第三个参数是指是否重新入列，也就是指不确认的消息是否重新丢回到队列里面去。

同样使用不确认后重新入列这个确认模式要谨慎，因为这里也可能因为考虑不周出现消息一直被重新丢回去的情况，导致积压。
```



<font face=幼圆 color=white>basic.reject用于否定确认，但与basic.nack相比有一个限制:一次只能拒绝单条消息 </font>

```text
	channel.basicReject(deliveryTag, true);  拒绝消费当前消息，如果第二参数传入true，就是将数据重新丢回
队列里，那么下次还会消费这消息。设置false，就是告诉服务器，我已经知道这条消息数据了，因为一些原因拒绝它，而且服
务器也把这个消息丢掉就行。 下次不想再消费这条消息了。

	使用拒绝后重新入列这个确认模式要谨慎，因为一般都是出现异常的时候，catch异常再拒绝入列，选择是否重入列。

	但是如果使用不当会导致一些每次都被你重入列的消息一直消费-入列-消费-入列这样循环，会导致消息积压。
```



### <font face=幼圆 color=white>4.4.消息确认监听</font>

```java
package com.sy.config.rabbitmq;

import com.sy.rabbitmq.MyAckReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq监听配置类
 *
 * @author lfeiyang
 * @since 2022-05-08 1:31
 */
@Configuration
public class MessageListenerConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MyAckReceiver myAckReceiver; //消息接收处理类

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息

        // 设置一个队列
        container.setQueueNames("DirectQueue", "fanout.A");
        // 如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        //  container.setQueueNames("TestDirectQueue","TestDirectQueue2","TestDirectQueue3");


        //另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        // container.setQueues(new Queue("TestDirectQueue",true));
        // container.addQueues(new Queue("TestDirectQueue2",true));
        // container.addQueues(new Queue("TestDirectQueue3",true));
        container.setMessageListener(myAckReceiver);

        return container;
    }
}
```



### <font face=幼圆 color=white>4.5.手动确认消息监听类</font>

![](D:\project\springboot_003\src\main\resources\book\image\ACK.png)

```java
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
```

