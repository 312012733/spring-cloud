package com.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer
{
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    public void send(String topic, Object msg)
    {
        amqpTemplate.convertAndSend(topic, msg);
    }
}
