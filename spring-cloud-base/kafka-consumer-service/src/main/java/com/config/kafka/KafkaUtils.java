package com.config.kafka;

import java.util.List;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.config.kafka.consumer.KafkaConsumerConfig;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class KafkaUtils
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
    
    // @Autowired
    // private KafkaConsumerConfig consumerConfig;
    // private ZooKeeper zooKeeper = null;
    
    public static boolean createTopic(String topicName, String zookeeperConn)
    {
        ZkUtils zkUtils = ZkUtils.apply(zookeeperConn, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        
        try
        { //
            AdminUtils.createTopic(zkUtils, topicName, 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
            return true;
        }
        catch (RuntimeException e)
        {
            
        }
        return false;
    }
    
    public static boolean topicExists(String topicName, String zookeeperConn)
    {
        ZkUtils zkUtils = ZkUtils.apply(zookeeperConn, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        boolean exists = AdminUtils.topicExists(zkUtils, topicName);
        return exists;
    }
    
    public static void createTopics(List<KafkaTopicBean> topics, KafkaZookeeperBean zookeeper)
    {
        try
        {
            // ZkClient zkClient = new ZkClient(zookeeper.getConnect(),
            // Integer.parseInt(zookeeper.getSessionTimeout()),
            // Integer.parseInt(zookeeper.getConnectTimeOut()),
            // ZKStringSerializer$.MODULE$);
            for (KafkaTopicBean topic : topics)
            {
                if (topicExists(topic.getName(), zookeeper.getConnect()))
                {
                    logger.info("topic {} 已经存在 , 不予创建", topic.getName());
                }
                else
                {
                    // AdminUtils.createTopic(zkClient, topic.getName(),
                    // topic.getPartitions(),
                    // topic.getReplicationFactor(), topic.getConfig());
                    
                    createTopic(topic.getName(), zookeeper.getConnect());
                    logger.info("topic {} 已创建", topic.getName());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("创建kafka topic 失败 , 失败原因:{}", e.getMessage());
            throw new KafkaTopicCreateException(e.getMessage());
        }
    }
    
}
