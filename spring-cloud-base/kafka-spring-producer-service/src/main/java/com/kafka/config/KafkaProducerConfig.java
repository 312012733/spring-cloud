package com.kafka.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaBean.class)
public class KafkaProducerConfig
{
    @Autowired
    private KafkaBean kafkaBean;
    
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @Bean
    public ProducerFactory<String, byte[]> producerFactory()
    {
        Properties producerProperties = kafkaBean.getProducerProperties();
        Map producerMap = producerProperties;
        return new DefaultKafkaProducerFactory<>(producerMap);
    }
    
    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate()
    {
        return new KafkaTemplate<String, byte[]>(producerFactory());
    }
}
