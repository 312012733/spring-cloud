package com.config.kafka.producer;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

@Configuration
@EnableConfigurationProperties(KafkaProducerBean.class)
public class KafkaProducerConfig
{
    @Autowired
    KafkaProducerBean kafkaProducerBean;
    
    @Bean
    public ProducerConfig getProducerConfig()
    {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", kafkaProducerBean.getBroker().getConnect());
        properties.put("serializer.class", kafkaProducerBean.getSerializer());
        properties.put("request.required.acks", kafkaProducerBean.getAcks());
        properties.put("partitioner.class", kafkaProducerBean.getPartitioner());
        properties.put("num.partitions", kafkaProducerBean.getPartitions());
        return new ProducerConfig(properties);
    }
    
    @Bean
    public Producer<String, byte[]> getProducer()
    {
        return new Producer<>(this.getProducerConfig());
    }
    
    @Bean
    public KafkaProducerBean getProducerBean()
    {
        return this.kafkaProducerBean;
    }
    
}
