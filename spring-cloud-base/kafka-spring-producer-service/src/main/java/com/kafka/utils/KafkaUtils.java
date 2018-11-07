package com.kafka.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
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
    private static final Logger LOG = LoggerFactory.getLogger(KafkaUtils.class);
    
    public static interface ConsumerSuccess
    {
        void onSuccess(ConsumerRecord<String, byte[]> record);
    }
    
    public static class MyConsumerRebalanceListener implements ConsumerRebalanceListener
    {
        private Consumer<?, ?> consumer;
        
        public MyConsumerRebalanceListener(Consumer<?, ?> consumer)
        {
            this.consumer = consumer;
        }
        
        public void onPartitionsRevoked(Collection<TopicPartition> partitions)
        {
            if (!partitions.isEmpty())
            {
                Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(partitions);
                Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions);
                
                Map<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>();
                
                for (TopicPartition partition : partitions)
                {
                    long offset = endOffsets.get(partition);
                    long offset2 = consumer.position(partition);
                    OffsetAndMetadata committed = consumer.committed(partition);
                    long offset3 = committed == null ? -1 : committed.offset();
                    LOG.info(
                            "----onPartitionsRevoked----partitions:{},\n beginningOffsets:{},\n endOffsets:{}, position:{},committed:{}",
                            partitions, beginningOffsets, endOffsets, offset2, offset3);
                    offsetMap.put(partition, new OffsetAndMetadata(offset));
                }
                
                consumer.commitSync(offsetMap);
            }
        }
        
        public void onPartitionsAssigned(Collection<TopicPartition> partitions)
        {
            if (!partitions.isEmpty())
            {
                Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(partitions);
                Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions);
                
                Map<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>();
                
                for (TopicPartition partition : partitions)
                {
                    long offset = endOffsets.get(partition);
                    long offset2 = consumer.position(partition);
                    OffsetAndMetadata committed = consumer.committed(partition);
                    long offset3 = committed == null ? -1 : committed.offset();
                    LOG.info(
                            "----onPartitionsAssigned----partitions:{},\n beginningOffsets:{},\n endOffsets:{}, position:{},committed:{}",
                            partitions, beginningOffsets, endOffsets, offset2, offset3);
                    offsetMap.put(partition, new OffsetAndMetadata(offset));
                }
                
                consumer.seekToEnd(partitions);
                consumer.commitSync(offsetMap);
            }
        }
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
        consumer.subscribe(Arrays.asList(topic), new MyConsumerRebalanceListener(consumer));
        
        while (true)
        {
            ConsumerRecords<String, byte[]> records = consumer.poll(1000);
            
            for (ConsumerRecord<String, byte[]> record : records)
            {
                consumerSuccess.onSuccess(record);
                
                consumer.commitAsync();
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
                    LOG.info("topic {} 已经存在 , 不予创建", topic);
                }
                else if (createTopic(topicBean, zkUtils))
                {
                    LOG.info("topic {} 已创建", topic);
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
            LOG.error("", e);
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
