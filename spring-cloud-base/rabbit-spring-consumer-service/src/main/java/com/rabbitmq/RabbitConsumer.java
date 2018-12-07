package com.rabbitmq;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avro.config.AvroConfig;
import com.avro.utils.AvroUtil;
import com.consumer.vo.ControlResultRabbitMsg;
import com.xiaolyuh.constants.RabbitConstants;

@Component
public class RabbitConsumer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);
    
    @Autowired
    private AvroConfig avroConfig;
    
    @RabbitListener(queues = RabbitConstants.QUEUE_NAME_DEAD_QUEUE, group = "a") // 监听器监听指定的Queue
    public void dead(String result,Message msg)
    {
        LOGGER.info("【----------------dead-----】--receive:{}--{}", result, msg);
    }
    
    @RabbitListener(queues = "direct", group = "a") // 监听器监听指定的Queue
    // public void direct1(Message msg)
    public void direct1(ControlResultRabbitMsg msg)
    {
        try
        {
            
            // LOGGER.info("【----------------controlResultKafkaConsumer1-----】--receive:{}--{}",
            // msg.getBody(), msg);
            LOGGER.info("【----------------direct1-----】--receive:{}", msg);
            
            // Schema schema = avroConfig.getControlResultReportSchema();
            // GenericRecord message = AvroUtil.bytesRead(msg.getBody(),
            // schema);
            //
            // LOGGER.info("【controlResultKafkaConsumer1--message-{}-】",
            // message);
            
            // JSONObject messageJson =
            // JSONObject.parseObject(message.toString());
            // consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        
    }
    
    @RabbitListener(queues = "direct", group = "a") // 监听器监听指定的Queue
    // public void direct1(Message msg)
    public void direct2(ControlResultRabbitMsg msg)
    {
        try
        {
            
            // LOGGER.info("【----------------controlResultKafkaConsumer1-----】--receive:{}--{}",
            // msg.getBody(), msg);
            LOGGER.info("【----------------direct2-----】--receive:{}", msg);
            
            // Schema schema = avroConfig.getControlResultReportSchema();
            // GenericRecord message = AvroUtil.bytesRead(msg.getBody(),
            // schema);
            //
            // LOGGER.info("【controlResultKafkaConsumer1--message-{}-】",
            // message);
            
            // JSONObject messageJson =
            // JSONObject.parseObject(message.toString());
            // consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
        
    }
    
    @RabbitListener(queues = "fanout.a", group = "a") // 监听器监听指定的Queue
    public void fanout1(Message msg)
    {
        LOGGER.info("【----------------fanout1-----】--receive:{}--", msg);
    }
    
    @RabbitListener(queues = "fanout.b", group = "b") // 监听器监听指定的Queue
    public void fanout2(Message msg)
    {
        LOGGER.info("【----------------fanout2-----】--receive:{}--", msg);
    }
    
    @RabbitListener(queues = "fanout.c", group = "c") // 监听器监听指定的Queue
    public void fanout3(Message msg)
    {
        LOGGER.info("【----------------fanout3-----】--receive:{}--", msg);
    }
    
    @RabbitListener(queues = "topic.a", group = "c") // 监听器监听指定的Queue
    public void topic1(Message msg)
    {
        LOGGER.info("【----------------topic1-----】--receive:{}--", msg);
    }
    
    @RabbitListener(queues = "topic.b", group = "c") // 监听器监听指定的Queue
    public void topic2(Message msg)
    {
        LOGGER.info("【----------------topic2-----】--receive:{}--", msg);
    }
    //
}
