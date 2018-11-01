package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.kafka.bean.ControlResultKafkMsg;

public class ControlResultKafkaConsumerThread extends KafkaConsumerThread
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ControlResultKafkaConsumerThread.class);
    
    public ControlResultKafkaConsumerThread(String topic, Schema schema, KafkaConsumer<String, byte[]> consumer,
            ApplicationContext context)
    {
        super(topic, schema, consumer, context);
    }
    
    @Override
    public void consumerService(JSONObject msgJson)
    {
        
        try
        {
            ControlResultKafkMsg kfkMsg = msgJson.toJavaObject(ControlResultKafkMsg.class);
            
            LOGGER.info("kfkMsg:{}", kfkMsg);
            
            // ControlResultConsumer controlResultConsumer =
            // (ControlResultConsumer) kafkaConsumer;
            
            // 保存车控结果
            // controlResultConsumer.getControlRecordService().saveControlResult(kfkMsg);
            
        }
        catch (Exception e)
        {
            LOGGER.error("ControlResultKafkaConsumerThread error. msgJson:{} \n", msgJson, e);
        }
    }
}
