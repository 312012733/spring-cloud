package com;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.avro.AvroMessageConfig;
import com.config.kafka.KafkaTopicBean;
import com.config.kafka.KafkaUtils;
import com.config.kafka.consumer.KafkaConsumerConfig;
import com.config.kafka.consumer.KafkaConsumerExecutorParam;
import com.config.kafka.consumer.KafkaConsumerThreadParam;
import com.config.kafka.producer.KafkaProducerConfig;
import com.kafka.consumer.ControlResultConsumer;
import com.kafka.consumer.ControlResultConsumerThread;

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
        
        List<KafkaTopicBean> consumerTopics = kafkaConsumerConfig.getKafkaConsumerBean().getTopics();
        
        List<KafkaTopicBean> topics = new ArrayList<>();
        topics.addAll(consumerTopics);
        
        // 自动创建topic
        KafkaUtils.createTopics(topics, kafkaConsumerConfig.getKafkaConsumerBean().getZookeeper());
        
        List<KafkaConsumerExecutorParam> params = new ArrayList<>();
        
        for (KafkaTopicBean topic : consumerTopics)
        {
            KafkaConsumerExecutorParam param = new KafkaConsumerExecutorParam();
            KafkaConsumerThreadParam threadParam = new KafkaConsumerThreadParam();
            
            param.setKafkaTopic(topic);
            threadParam.setKafkaConsumer(kafkaConsumerConfig);
            threadParam.setKafkaProducer(kafkaProducerConfig);
            threadParam.setAvroMessage(avroMessageConfig);
            
            if (topic.getName().equals(avroMessageConfig.getControlResultSchema().getName()))
            {
                threadParam.setSchema(avroMessageConfig.getControlResultSchema());
                threadParam.setThreadClass(ControlResultConsumerThread.class);
            }
            
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
            
            param.setThreadParam(threadParam);
            params.add(param);
        }
        
        kafkaConsumerConfig.executor(params);
        
    }
}
