package com;

import java.util.List;

import org.apache.avro.Schema;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.avro.config.AvroConfig;
import com.consumer.ControlResultKafkaConsumerThread;
import com.consumer.ControlResultPushKafkaConsumerThread;
import com.kafka.config.KafkaBean;
import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.config.KafkaConfig;
import com.kafka.utils.KafkaUtils;
import com.utils.ThreadUtils;

@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerApplication
{
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerApplication.class);
    
    public static void main(String[] args) throws Exception
    {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaConsumerApplication.class, args);
        
        KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        
        AvroConfig avroeConfig = context.getBean(AvroConfig.class);
        Schema controlReportSchema = avroeConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroeConfig.getControlResultReportPushSchema();
        
        List<TopicBean> topics = kafkaBean.getTopics();
        
        KafkaUtils.createTopics(topics, kafkaBean.getZookeeper().getConnect());
        
        KafkaConsumer<String, byte[]> kafkaConsumer = kafkaConfig.kafkaConsumer();
        
        for (TopicBean topicBean : topics)
        {
            String topic = topicBean.getName();
            
            if (topic.equals(controlReportSchema.getName()))
            {
                List<PartitionInfo> partitions = kafkaConsumer.partitionsFor(topic);
                
                for (PartitionInfo partitionInfo : partitions)
                {
                    Runnable task = new ControlResultKafkaConsumerThread(topic, partitionInfo.partition(),
                            controlReportSchema, kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                List<PartitionInfo> partitions = kafkaConsumer.partitionsFor(topic);
                
                for (PartitionInfo partitionInfo : partitions)
                {
                    Runnable task = new ControlResultPushKafkaConsumerThread(topic, partitionInfo.partition(),
                            controlReportPushSchema, kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
            }
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
        }
        
        /****************************************************/
        
    }
    
}
