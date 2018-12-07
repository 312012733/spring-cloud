package com.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer
{
    @Autowired
    private AmqpTemplate amqpTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);
    
    public void send(String routingKey, Object msg)
    {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor()
        {
            
            @Override
            public Message postProcessMessage(Message message) throws AmqpException
            {
                LOGGER.info("【-------postProcessMessage-------】 msg:{}", message);
                return message;
            }
        };
        
        amqpTemplate.convertAndSend(routingKey, msg, messagePostProcessor);
        Object result = null;
        // result = amqpTemplate.convertSendAndReceive( routingKey, msg,
        // messagePostProcessor);
        
        LOGGER.info("【-------result-------】 result:{}", result);
    }
}
