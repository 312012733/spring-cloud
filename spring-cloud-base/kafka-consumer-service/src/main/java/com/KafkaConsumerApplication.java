package com;

import java.util.List;

import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.avro.AvroMessageConfig;
import com.config.kafka.KafkaBean;
import com.config.kafka.KafkaConfig;
import com.kafka.consumer.ControlResultKafkaConsumerThread;
import com.kafka.consumer.ControlResultPushKafkaConsumerThread;
import com.utils.ThreadUtils;

@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerApplication
{
    
    public static void main(String[] args) throws Exception
    {
        
        ConfigurableApplicationContext context = SpringApplication.run(KafkaConsumerApplication.class, args);
        
        KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        KafkaConsumer<String, byte[]> kafkaConsumer = kafkaConfig.kafkaConsumer();
        
        AvroMessageConfig avroMessageConfig = context.getBean(AvroMessageConfig.class);
        Schema controlReportSchema = avroMessageConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroMessageConfig.getControlResultReportPushSchema();
        
        List<String> topics = kafkaBean.getTopics();
        
        for (String topic : topics)
        {
            
            if (topic.equals(controlReportSchema.getName()))
            {
                Runnable task = new ControlResultKafkaConsumerThread(topic, controlReportSchema, kafkaConsumer,
                        context);
                ThreadUtils.execute(task);
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                Runnable task = new ControlResultPushKafkaConsumerThread(topic, controlReportPushSchema, kafkaConsumer,
                        context);
                ThreadUtils.execute(task);
            }
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
        }
        
    }
    
}