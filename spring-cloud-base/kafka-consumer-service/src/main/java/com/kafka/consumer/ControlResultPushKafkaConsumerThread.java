package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.kafka.bean.ControlResultPushKafkMsg;

public class ControlResultPushKafkaConsumerThread extends KafkaConsumerThread
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ControlResultPushKafkaConsumerThread.class);
    
    public ControlResultPushKafkaConsumerThread(String topic, Schema schema, KafkaConsumer<String, byte[]> consumer,
            ApplicationContext context)
    {
        super(topic, schema, consumer, context);
    }
    
    @Override
    public void consumerService(JSONObject msgJson)
    {
        try
        {
            ControlResultPushKafkMsg kfkMsg = msgJson.toJavaObject(ControlResultPushKafkMsg.class);
            
//            LOGGER.info("kfkMsg:{}", kfkMsg);
            
            // ControlResultConsumer controlResultConsumer =
            // (ControlResultConsumer) kafkaConsumer;
            
            // 保存车控结果
            // controlResultConsumer.getControlRecordService().saveControlResult(kfkMsg);
            
        }
        catch (Exception e)
        {
            LOGGER.error("ControlResultPushKafkaConsumerThread error. msgJson:{} \n", msgJson, e);
        }
    }
}
