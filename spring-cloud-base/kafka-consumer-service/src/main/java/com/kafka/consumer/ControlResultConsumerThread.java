package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ControlResultConsumerThread extends KafkaConsumerThread
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ControlResultConsumerThread.class);
    
    public ControlResultConsumerThread(String topic, Schema schema, KafkaConsumer<String, byte[]> consumer)
    {
        super(topic, schema, consumer);
    }
    
    @Override
    public void consumerService(JSONObject msgJson)
    {
        LOGGER.info("controlRecord save start. ");
        
        try
        {
            ControlResultKafkMsg kfkMsg = msgJson.toJavaObject(ControlResultKafkMsg.class);
            
            LOGGER.info("kfkMsg:{}", kfkMsg);
            
            // ControlResultConsumer controlResultConsumer =
            // (ControlResultConsumer) kafkaConsumer;
            
            // 保存车控结果
            // controlResultConsumer.getControlRecordService().saveControlResult(kfkMsg);
            
            LOGGER.info("controlRecord save end. \n");
        }
        catch (Exception e)
        {
            LOGGER.error("save controlRecord error. msgJson:{} \n", msgJson, e);
        }
    }
}
