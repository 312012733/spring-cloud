package com.config.kafka.consumer;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.config.avro.AvroMessageConfig;
import com.config.avro.AvroUtil;
import com.config.kafka.producer.KafkaProducerConfig;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public abstract class KafkaConsumerThread implements Runnable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerThread.class);
    
    private KafkaStream<byte[], byte[]> stream = null;
    private Schema schema;
    protected AvroMessageConfig avroMessage;
    protected KafkaConsumerConfig kafkaConsumer;
    protected KafkaProducerConfig kafkaProducer;
    
    public KafkaConsumerThread(KafkaStream<byte[], byte[]> stream, Schema schema, AvroMessageConfig avroMessage,
            KafkaConsumerConfig kafkaConsumer, KafkaProducerConfig kafkaProducer)
    {
        this.stream = stream;
        this.schema = schema;
        this.avroMessage = avroMessage;
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
    }
    
    @Override
    public void run()
    {
        if (null != stream)
        {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext())
            {
                disposeMessageAvro(it);
            }
        }
        
    }
    
    private void disposeMessageAvro(ConsumerIterator<byte[], byte[]> it)
    {
        byte[] receivedMessage = null;
        try
        {
            receivedMessage = it.next().message();
            GenericRecord message = AvroUtil.bytesRead(receivedMessage, schema);
            JSONObject messageJson = JSONObject.parseObject(message.toString());
            consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("get kafka error. " + this.getClass().getSimpleName());
            LOGGER.error(
                    "get kafka error. receivedMessage:" + receivedMessage == null ? null : new String(receivedMessage),
                    e);
        }
    }
    
    protected void sendPushMsg(Schema sendSchema, String topic, GenericRecord genericRecord) throws IOException
    {
        LOGGER.info(getClass().getSimpleName() + " send push msg start. topic:" + topic);
        
        byte[] msg = AvroUtil.bytesWrite(sendSchema, genericRecord);
        Producer<String, byte[]> sender = this.kafkaProducer.getProducer();
        sender.send(new KeyedMessage<String, byte[]>(topic, msg));
        
        LOGGER.info(getClass().getSimpleName() + " send push msg end. topic:" + topic + ", msg:" + new String(msg));
    }
    
    protected abstract void consumerService(JSONObject messageJson);
}
