package com;

import java.util.List;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.avro.config.AvroConfig;
import com.kafka.config.KafkaBean;
import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.config.KafkaConfig;
import com.kafka.consumer.impl.ControlResultKafkaConsumerThread;
import com.kafka.consumer.impl.ControlResultPushKafkaConsumerThread;
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
        
        AvroConfig avroeConfig = context.getBean(AvroConfig.class);
        Schema controlReportSchema = avroeConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroeConfig.getControlResultReportPushSchema();
        
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        List<TopicBean> topics = kafkaBean.getTopics();
        
        for (TopicBean topicBean : topics)
        {
            String topic = topicBean.getName();
            
            if (topic.equals(controlReportSchema.getName()))
            {
                for (int i = 0; i < topicBean.getPartitions(); i++)
                {
                    Runnable task = new ControlResultKafkaConsumerThread(topic, null, controlReportSchema,
                            kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
                
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                for (int i = 0; i < topicBean.getPartitions(); i++)
                {
                    Runnable task = new ControlResultPushKafkaConsumerThread(topic, null, controlReportPushSchema,
                            kafkaConfig.kafkaConsumer(), context);
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
