package com.kafka.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.kafka.utils.KafkaUtils;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaBean.class)
public class KafkaConsumerConfig
{
    @Autowired
    private KafkaBean kafkaBean;
    
    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory()
    {
        Properties consumerProperties = kafkaBean.getConsumerProperties();
        Map consumerMap = consumerProperties;
        return new DefaultKafkaConsumerFactory<>(consumerMap);
    }
    
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, byte[]>> kafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(kafkaBean.getTopics().get(0).getPartitions());
        factory.getContainerProperties().setPollTimeout(KafkaUtils.TIME_OUT);
        
        return factory;
    }
    
}
