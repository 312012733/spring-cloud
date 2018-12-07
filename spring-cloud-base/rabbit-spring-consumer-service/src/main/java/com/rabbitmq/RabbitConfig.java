package com.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xiaolyuh.constants.RabbitConstants;

@Configuration
public class RabbitConfig
{
    public Queue deadQueue(String name)
    {
        Map<String, Object> args = new HashMap<>();
        // 设置死信队列
        args.put("x-dead-letter-exchange", RabbitConstants.MQ_EXCHANGE_DEAD_QUEUE);
        args.put("x-dead-letter-routing-key", RabbitConstants.MQ_ROUTING_KEY_DEAD_QUEUE);
        // 设置消息的过期时间， 单位是毫秒
        args.put("x-message-ttl", 5000);
        
        // 是否持久化
        boolean durable = true;
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = false;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = false;
        return new Queue(name, durable, exclusive, autoDelete, args);
    }
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory)
    {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 声明死信队列（Fanout类型的exchange）
        Queue deadQueue = new Queue(RabbitConstants.QUEUE_NAME_DEAD_QUEUE);
        // 死信队列交换机
        FanoutExchange deadExchange = new FanoutExchange(RabbitConstants.MQ_EXCHANGE_DEAD_QUEUE);
        rabbitAdmin.declareQueue(deadQueue);
        rabbitAdmin.declareExchange(deadExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(deadQueue).to(deadExchange));
        
        // 发放奖励队列交换机
        DirectExchange exchange = new DirectExchange(RabbitConstants.MQ_EXCHANGE_SEND_AWARD);
        
        // 声明发送优惠券的消息队列（Direct类型的exchange）
        Queue couponQueue = deadQueue(RabbitConstants.QUEUE_NAME_SEND_COUPON);
        rabbitAdmin.declareQueue(couponQueue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(couponQueue).to(exchange).with(RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON));
        
        return rabbitAdmin;
    }
    
    // ========================验证direct
    // Exchange的队列==================================
    @Bean
    public Queue directQueue()
    {
        return new Queue("direct");
    }
    
    // ========================验证topic
    // Exchange的队列==================================
    @Bean
    public Queue topicQueue1()
    {
        return new Queue("topic.a");
    }
    
    @Bean
    public Queue topicQueue2()
    {
        return new Queue("topic.b");
    }
    
    // =========================Fanout
    // Exchange的队列================================
    @Bean
    public Queue fanoutQueue1()
    {
        return new Queue("fanout.a");
    }
    
    @Bean
    public Queue fanoutQueue2()
    {
        return new Queue("fanout.b");
    }
    
    @Bean
    public Queue fanoutQueue3()
    {
        return new Queue("fanout.c");
    }
    
    // ========================================================================
    
    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange("directExchange");
    }
    
    @Bean
    public TopicExchange topicExchange()
    {
        return new TopicExchange("topicExchange");
    }
    
    @Bean
    public FanoutExchange fanoutExchange()
    {
        return new FanoutExchange("fanoutExchange");
    }
    
    // ========================================================================
    
    @Bean
    public Binding bindingDirectExchange(Queue directQueue, DirectExchange exchange)
    {
        return BindingBuilder.bind(directQueue).to(exchange).withQueueName();
    }
    
    // ========================================================================
    
    @Bean
    public Binding bindingTopicExchange1(Queue topicQueue1, TopicExchange exchange)
    {
        return BindingBuilder.bind(topicQueue1).to(exchange).with(topicQueue1.getName());
    }
    
    @Bean
    public Binding bindingTopicExchange2(Queue topicQueue2, TopicExchange exchange)
    {
        return BindingBuilder.bind(topicQueue2).to(exchange).with("topic.#");
    }
    
    // ========================================================================
    
    @Bean
    public Binding bindingFanoutExchange1(Queue fanoutQueue1, FanoutExchange fanoutExchange)
    {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
    
    @Bean
    public Binding bindingFanoutExchange2(Queue fanoutQueue2, FanoutExchange fanoutExchange)
    {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
    
    @Bean
    public Binding bindingFanoutExchange3(Queue fanoutQueue3, FanoutExchange fanoutExchange)
    {
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }
}
