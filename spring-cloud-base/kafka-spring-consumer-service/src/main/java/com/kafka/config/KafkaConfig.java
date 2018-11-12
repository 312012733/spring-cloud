package com.kafka.config;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import com.kafka.utils.KafkaUtils;
import com.kafka.utils.KafkaUtils.MyConsumerRebalanceListener;

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaBean.class)
public class KafkaConfig
{
    @Autowired
    private KafkaBean kafkaBean;
    
    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, byte[]> concurrentKafkaListenerContainerFactory;
    
    @PostConstruct
    public void init()
    {
        ContainerProperties containerProperties = concurrentKafkaListenerContainerFactory.getContainerProperties();
        containerProperties.setConsumerRebalanceListener(new MyConsumerAwareRebalanceListener());
        
        createTopics();
    }
    
    public void createTopics()
    {
        KafkaUtils.createTopics(kafkaBean.getTopics(), kafkaBean.getZookeeper().getConnect());
    }
    
    public static class MyConsumerAwareRebalanceListener implements ConsumerAwareRebalanceListener
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
    
    // @SuppressWarnings(
    // { "unchecked", "rawtypes" })
    // @Bean
    // public ConsumerFactory<String, byte[]> consumerFactory()
    // {
    // Properties consumerProperties = kafkaBean.getConsumerProperties();
    // Map consumerMap = consumerProperties;
    //
    // return new DefaultKafkaConsumerFactory<>(consumerMap);
    // }
    //
    // @Bean
    // public
    // KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,
    // byte[]>> kafkaListenerContainerFactory()
    // {
    // ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new
    // ConcurrentKafkaListenerContainerFactory<>();
    //
    // factory.setConsumerFactory(consumerFactory());
    // factory.setConcurrency(countPartitions());
    //
    // ContainerProperties containerProperties =
    // factory.getContainerProperties();
    //
    // containerProperties.setPollTimeout(KafkaUtils.TIME_OUT);
    // containerProperties.setConsumerRebalanceListener(new
    // MyConsumerAwareRebalanceListener());
    // containerProperties.setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
    //
    // return factory;
    // }
    //
    // private int countPartitions()
    // {
    // List<TopicBean> topics = kafkaBean.getTopics();
    //
    // int count = 0;
    //
    // for (TopicBean topicBean : topics)
    // {
    // int partitions = topicBean.getPartitions();
    //
    // count += partitions;
    // return count;
    // }
    //
    // return count;
    // }
    
}
