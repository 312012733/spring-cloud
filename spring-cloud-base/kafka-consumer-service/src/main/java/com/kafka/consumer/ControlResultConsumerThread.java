package com.kafka.consumer;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.config.avro.AvroMessageConfig;
import com.config.kafka.consumer.KafkaConsumerConfig;
import com.config.kafka.consumer.KafkaConsumerThread;
import com.config.kafka.producer.KafkaProducerConfig;

import kafka.consumer.KafkaStream;

public class ControlResultConsumerThread extends KafkaConsumerThread
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ControlResultConsumerThread.class);
    
    public ControlResultConsumerThread(KafkaStream<byte[], byte[]> stream, Schema schema, AvroMessageConfig avroMessage,
            KafkaConsumerConfig kafkaConsumer, KafkaProducerConfig kafkaProducer)
    {
        super(stream, schema, avroMessage, kafkaConsumer, kafkaProducer);
    }
    
    @Override
    public void consumerService(JSONObject msgJson)
    {
        LOGGER.info("controlRecord save start. ");
        
        try
        {
            ControlResultKafkMsg kfkMsg = (ControlResultKafkMsg) JSONObject.toJavaObject(msgJson,
                    ControlResultKafkMsg.class);
            
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
