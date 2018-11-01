package com;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.avro.AvroMessageConfig;
import com.config.avro.AvroUtil;
import com.config.kafka.consumer.KafkaConsumerConfig;
import com.config.kafka.producer.KafkaProducerConfig;
import com.kafka.consumer.ControlResultConsumer;

@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerApplication
{
    
    public static void main(String[] args) throws Exception
    {
        
        ConfigurableApplicationContext context = SpringApplication.run(KafkaConsumerApplication.class, args);
        
        KafkaConsumerConfig kafkaConsumerConfig = context.getBean(ControlResultConsumer.class);
        KafkaProducerConfig kafkaProducerConfig = context.getBean(KafkaProducerConfig.class);
        AvroMessageConfig avroMessageConfig = context.getBean(AvroMessageConfig.class);
        
        String topic = avroMessageConfig.getControlResultSchema().getNamespace();
        consumer(topic ,avroMessageConfig.getControlResultSchema());
        //
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
    
    public static void consumer(String topic, Schema schema) throws IOException
    {
        Properties properties = new Properties();
//        properties.put("bootstrap.servers","192.168.3.62:9092");
        properties.put("bootstrap.servers","172.16.5.220:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        
        final KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(properties);
        consumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener()
        {
            public void onPartitionsRevoked(Collection<TopicPartition> collection)
            {
            }
            
            public void onPartitionsAssigned(Collection<TopicPartition> collection)
            {
                // 将偏移设置到最开始
                consumer.seekToBeginning(collection);
            }
        });
        
        while (true)
        {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);

            for (ConsumerRecord<String, byte[]> record : records) {
                
                GenericRecord message = AvroUtil.bytesRead(record.value(), schema);;
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), message);
            }
        }
    }
}
