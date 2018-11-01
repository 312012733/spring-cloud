package com;

import java.io.IOException;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.avro.AvroMessageConfig;
import com.config.avro.AvroUtil;
import com.config.kafka.consumer.KafkaConsumerConfig;
import com.config.kafka.producer.KafkaProducerConfig;
import com.kafka.consumer.ControlResultConsumer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

@EnableEurekaClient
@SpringBootApplication
public class KafkaProducerApplication
{
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducerApplication.class);
    
    public static void main(String[] args) throws Exception
    {
        
        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
        KafkaConsumerConfig kafkaConsumerConfig = context.getBean(ControlResultConsumer.class);
        KafkaProducerConfig kafkaProducerConfig = context.getBean(KafkaProducerConfig.class);
        AvroMessageConfig avroMessageConfig = context.getBean(AvroMessageConfig.class);
        
        Schema schema = avroMessageConfig.getControlResultSchema();
        String topic = schema.getNamespace();
        
        // ControlResultKafkMsg msg = new ControlResultKafkMsg();
        
        GenericRecord genericRecord = new GenericData.Record(schema);
        genericRecord.put("vin", "vin");
        genericRecord.put("uuid", "uuid");
        genericRecord.put("time", System.currentTimeMillis());
        genericRecord.put("result", "true");
        
//        sendPushMsg(kafkaProducerConfig.getProducer(), schema, topic, genericRecord);
        send(topic, AvroUtil.bytesWrite(schema, genericRecord));
        // List<KafkaTopicBean> consumerTopics =
        // kafkaConsumerConfig.getKafkaConsumerBean().getTopics();
        //
        // List<KafkaTopicBean> topics = new ArrayList<>();
        // topics.addAll(consumerTopics);
        //
        // // 自动创建topic
        // KafkaUtils.createTopics(topics,
        // kafkaConsumerConfig.getKafkaConsumerBean().getZookeeper());
        //
        // List<KafkaConsumerExecutorParam> params = new ArrayList<>();
        //
        // for (KafkaTopicBean topic : consumerTopics)
        // {
        // KafkaConsumerExecutorParam param = new KafkaConsumerExecutorParam();
        // KafkaConsumerThreadParam threadParam = new
        // KafkaConsumerThreadParam();
        //
        // param.setKafkaTopic(topic);
        // threadParam.setKafkaConsumer(kafkaConsumerConfig);
        // threadParam.setKafkaProducer(kafkaProducerConfig);
        // threadParam.setAvroMessage(avroMessageConfig);
        //
        // if
        // (topic.getName().equals(avroMessageConfig.getControlResultSchema().getName()))
        // {
        // threadParam.setSchema(avroMessageConfig.getControlResultSchema());
        // threadParam.setThreadClass(ControlResultConsumerThread.class);
        // }
        //
        // else
        // {
        // throw new IllegalArgumentException("topicName and schemaName did not
        // match up.");
        // }
        //
        // param.setThreadParam(threadParam);
        // params.add(param);
        // }
        //
        // kafkaConsumerConfig.executor(params);
        
    }
    
    static void sendPushMsg(Producer<String, byte[]> sender, Schema sendSchema, String topic,
            GenericRecord genericRecord) throws IOException
    {
        LOGGER.info(" send push msg start.  topic:" + topic);
        
        byte[] msg = AvroUtil.bytesWrite(sendSchema, genericRecord);
        sender.send(new KeyedMessage<String, byte[]>(topic, msg));
        
        LOGGER.info(" send push msg end. topic:" + topic + ", msg:" + new String(msg));
    }
    
    public static void send( String topic, byte[] value)
    {
        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "192.168.3.62:9092");
        properties.put("bootstrap.servers", "172.16.5.220:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        try
        {
            KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(properties);
            producer.send(new ProducerRecord<String, byte[]>(topic,"hello", value));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
        finally
        {
            // producer.close();
        }
        
    }
    
}
