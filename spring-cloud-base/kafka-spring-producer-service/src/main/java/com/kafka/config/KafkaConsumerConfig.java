package com.kafka.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.utils.KafkaUtils;
import com.kafka.utils.KafkaUtils.MyConsumerRebalanceListener;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaBean.class)
@ConditionalOnProperty(matchIfMissing = false, prefix = "kafka.consumerProperties", name =
{ "bootstrap.servers" })
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
        factory.setConcurrency(countPartitions());
        
        ContainerProperties containerProperties = factory.getContainerProperties();
        
        containerProperties.setPollTimeout(KafkaUtils.TIME_OUT);
        containerProperties.setConsumerRebalanceListener(new MyConsumerAwareRebalanceListener());
        containerProperties.setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
        
        return factory;
    }
    
    @PostConstruct
    public void createTopics()
    {
        KafkaUtils.createTopics(kafkaBean.getTopics(), kafkaBean.getZookeeper().getConnect());
    }
    
    private static class MyConsumerAwareRebalanceListener implements ConsumerAwareRebalanceListener
    {
        
        @Override
        public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions)
        {
            MyConsumerRebalanceListener myConsumerRebalanceListener = new MyConsumerRebalanceListener(consumer);
            myConsumerRebalanceListener.onPartitionsRevoked(partitions);
        }
        
        @Override
        public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions)
        {
            MyConsumerRebalanceListener myConsumerRebalanceListener = new MyConsumerRebalanceListener(consumer);
            myConsumerRebalanceListener.onPartitionsAssigned(partitions);
        }
        
    }
    
    private int countPartitions()
    {
        List<TopicBean> topics = kafkaBean.getTopics();
        
        int count = 0;
        
        for (TopicBean topicBean : topics)
        {
            int partitions = topicBean.getPartitions();
            
            count += partitions;
            return count;
        }
        
        return count;
    }
    
}
