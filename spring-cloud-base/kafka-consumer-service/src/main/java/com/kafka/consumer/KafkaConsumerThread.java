package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.avro.utils.AvroUtil;
import com.kafka.utils.KafkaUtils;
import com.kafka.utils.KafkaUtils.ConsumerSuccess;

public abstract class KafkaConsumerThread implements Runnable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerThread.class);
    private String topic;
    private Integer partition;
    private final Schema schema;
    private KafkaConsumer<String, byte[]> consumer;
    protected ApplicationContext context;
    
    public KafkaConsumerThread(String topic, Integer partition, Schema schema, KafkaConsumer<String, byte[]> consumer,
            ApplicationContext context)
    {
        this.schema = schema;
        this.topic = topic;
        this.consumer = consumer;
        this.partition = partition;
        this.context = context;
    }
    
    @Override
    public void run()
    {
        ConsumerSuccess consumerSuccess = new ConsumerSuccess()
        {
            @Override
            public void onSuccess(ConsumerRecord<String, byte[]> record)
            {
                try
                {
                    GenericRecord message = AvroUtil.bytesRead(record.value(), schema);
                    
                    LOGGER.info("【{}-----offset={}, partition={}, key={}, value={}】",
                            KafkaConsumerThread.this.getClass().getSimpleName(), record.offset(), record.partition(),
                            record.key(), message);
                    
                    JSONObject messageJson = JSONObject.parseObject(message.toString());
                    consumerService(messageJson);
                }
                catch (Exception e)
                {
                    LOGGER.error("", e);
                }
            }
        };
        
        if (partition == null)
        {
            KafkaUtils.consume(topic, consumer, consumerSuccess);
        }
        else
        {
            KafkaUtils.consume(topic, partition, consumer, consumerSuccess);
        }
        
    }
    
    protected abstract void consumerService(JSONObject messageJson);
}
