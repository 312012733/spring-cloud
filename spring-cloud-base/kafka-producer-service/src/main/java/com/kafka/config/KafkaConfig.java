package com.kafka.config;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.kafka.utils.KafkaUtils;

@Configuration
@EnableConfigurationProperties(KafkaBean.class)
public class KafkaConfig
{
    @Autowired
    private KafkaBean kafkaBean;
    
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @ConditionalOnProperty(matchIfMissing = false, prefix = "kafka.consumerProperties", name =
    { "bootstrap.servers" })
    @ConditionalOnMissingBean
    public KafkaConsumer<String, byte[]> kafkaConsumer()
    {
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(kafkaBean.getConsumerProperties());
        
        return consumer;
    }
    
    @Bean
    @ConditionalOnProperty(matchIfMissing = false, prefix = "kafka.producerProperties", name =
    { "bootstrap.servers" })
    @ConditionalOnMissingBean
    public KafkaProducer<String, byte[]> kafkaProducer()
    {
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(kafkaBean.getProducerProperties());
        return producer;
    }
    
    @PostConstruct
    public void createTopics()
    {
        KafkaUtils.createTopics(kafkaBean.getTopics(), kafkaBean.getZookeeper().getConnect());
    }
}
