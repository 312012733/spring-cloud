package com.config.kafka.consumer;

import org.apache.avro.Schema;

import com.config.avro.AvroMessageConfig;
import com.config.kafka.producer.KafkaProducerConfig;

public class KafkaConsumerThreadParam
{
    
    private KafkaConsumerConfig kafkaConsumer;
    private KafkaProducerConfig kafkaProducer;
    private Schema schema;
    private AvroMessageConfig avroMessage;
    private Class<? extends KafkaConsumerThread> threadClass;
    
    public KafkaConsumerThreadParam()
    {
    }
    
    public Schema getSchema()
    {
        return schema;
    }
    
    public void setSchema(Schema schema)
    {
        this.schema = schema;
    }
    
    public KafkaConsumerConfig getKafkaConsumer()
    {
        return kafkaConsumer;
    }
    
    public void setKafkaConsumer(KafkaConsumerConfig kafkaConsumer)
    {
        this.kafkaConsumer = kafkaConsumer;
    }
    
    public KafkaProducerConfig getKafkaProducer()
    {
        return kafkaProducer;
    }
    
    public void setKafkaProducer(KafkaProducerConfig kafkaProducer)
    {
        this.kafkaProducer = kafkaProducer;
    }
    
    public AvroMessageConfig getAvroMessage()
    {
        return avroMessage;
    }
    
    public void setAvroMessage(AvroMessageConfig avroMessage)
    {
        this.avroMessage = avroMessage;
    }
    
    public Class<? extends KafkaConsumerThread> getThreadClass()
    {
        return threadClass;
    }
    
    public void setThreadClass(Class<? extends KafkaConsumerThread> threadClass)
    {
        this.threadClass = threadClass;
    }
    
}
