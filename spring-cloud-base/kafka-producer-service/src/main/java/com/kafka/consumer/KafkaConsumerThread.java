package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.config.avro.AvroUtil;
import com.config.kafka.KafkaUtils;
import com.config.kafka.KafkaUtils.ConsumerSuccess;

public abstract class KafkaConsumerThread implements Runnable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerThread.class);
    private String topic;
    private final Schema schema;
    private KafkaConsumer<String, byte[]> consumer;
    protected ApplicationContext context;
    
    public KafkaConsumerThread(String topic, Schema schema, KafkaConsumer<String, byte[]> consumer,
            ApplicationContext context)
    {
        this.schema = schema;
        this.topic = topic;
        this.consumer = consumer;
        this.context = context;
    }
    
    @Override
    public void run()
    {
        KafkaUtils.consume(topic, consumer, new ConsumerSuccess()
        {
            
            @Override
            public void onSuccess(ConsumerRecord<String, byte[]> record)
            {
                try
                {
                    GenericRecord message = AvroUtil.bytesRead(record.value(), schema);
                    LOGGER.info(this.getClass().getName() + " offset = {}, pa={}, key = {}, value = {}",
                            record.offset(), record.partition(), record.key(), message);
                    
                    JSONObject messageJson = JSONObject.parseObject(message.toString());
                    consumerService(messageJson);
                }
                catch (Exception e)
                {
                    LOGGER.error("", e);
                }
            }
        });
        
    }
    
    protected abstract void consumerService(JSONObject messageJson);
}
