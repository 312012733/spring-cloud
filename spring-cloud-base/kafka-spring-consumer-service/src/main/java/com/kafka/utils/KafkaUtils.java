package com.kafka.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.security.JaasUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kafka.config.KafkaBean.TopicBean;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZkUtils;

public class KafkaUtils
{
    public static final int TIME_OUT = 1000;
    private static final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
    
    public static interface ConsumerSuccess
    {
        void onSuccess(ConsumerRecord<String, byte[]> record);
    }
    
    public static void produce(String topic, KafkaProducer<String, byte[]> producer, byte[] value)
    {
        producer.send(new ProducerRecord<String, byte[]>(topic, value));
    }
    
    public static void consume(String topic, Integer partition, KafkaConsumer<String, byte[]> consumer,
            ConsumerSuccess consumerSuccess)
    {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        
        consumer.assign(Arrays.asList(topicPartition));
        
        while (true)
        {
            ConsumerRecords<String, byte[]> records = consumer.poll(TIME_OUT);
            
            for (ConsumerRecord<String, byte[]> record : records)
            {
                consumerSuccess.onSuccess(record);
                
            }
        }
    }
    
    public static void consume(String topic, KafkaConsumer<String, byte[]> consumer, ConsumerSuccess consumerSuccess)
    {
        consumer.subscribe(Arrays.asList(topic), new ConsumerRebalanceListener()
        {
            public void onPartitionsRevoked(Collection<TopicPartition> collection)
            {
                
            }
            
            public void onPartitionsAssigned(Collection<TopicPartition> collection)
            {
                // 将偏移设置到最开始
                // consumer.seekToBeginning(collection);
            }
        });
        
        while (true)
        {
            ConsumerRecords<String, byte[]> records = consumer.poll(1000);
            
            for (ConsumerRecord<String, byte[]> record : records)
            {
                consumerSuccess.onSuccess(record);
                
            }
        }
    }
    
    public static void createTopics(List<TopicBean> topics, String zookeeperConnection)
    {
        ZkUtils zkUtils = buildZkUtils(zookeeperConnection);
        
        try
        {
            for (TopicBean topicBean : topics)
            {
                String topic = topicBean.getName();
                
                if (topicExists(topic, zkUtils))
                {
                    logger.info("topic {} 已经存在 , 不予创建", topic);
                }
                else if (createTopic(topicBean, zkUtils))
                {
                    logger.info("topic {} 已创建", topic);
                }
            }
        }
        finally
        {
            if (null != zkUtils)
            {
                zkUtils.close();
            }
        }
    }
    
    private static boolean createTopic(TopicBean topicBean, ZkUtils zkUtils)
    {
        try
        {
            String topicName = topicBean.getName();
            int partitions = topicBean.getPartitions();
            int replicationFactor = topicBean.getReplicationFactor();
            Properties config = topicBean.getConfig();
            
            AdminUtils.createTopic(zkUtils, topicName, partitions, replicationFactor, config,
                    RackAwareMode.Enforced$.MODULE$);
            
            return true;
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        
        return false;
    }
    
    private static boolean topicExists(String topicName, ZkUtils zkUtils)
    {
        return AdminUtils.topicExists(zkUtils, topicName);
    }
    
    private static ZkUtils buildZkUtils(String zookeeperConnection)
    {
        return ZkUtils.apply(zookeeperConnection, 30000, 30000, JaasUtils.isZkSecurityEnabled());
    }
    
}
