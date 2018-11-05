package com;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.kafka.config.KafkaBean;
import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.utils.KafkaUtils;

@EnableEurekaClient
@SpringBootApplication
public class KafkaSpringConsumerApplication
{
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaSpringConsumerApplication.class);
    
    public static void main(String[] args) throws Exception
    {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaSpringConsumerApplication.class, args);
        
        // 创建topic
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        List<TopicBean> topics = kafkaBean.getTopics();
        KafkaUtils.createTopics(topics, kafkaBean.getZookeeper().getConnect());
        
        /********************************************************/
    }
    
}
