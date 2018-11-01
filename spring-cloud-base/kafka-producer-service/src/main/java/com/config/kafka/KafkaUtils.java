package com.config.kafka;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;

public class KafkaUtils
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
    
//    public static void createTopics(List<KafkaTopicBean> topics, KafkaZookeeperBean zookeeper)
//    {
//        try
//        {
//            ZkClient zkClient = new ZkClient(zookeeper.getConnect(), Integer.parseInt(zookeeper.getSessionTimeout()),
//                    Integer.parseInt(zookeeper.getConnectTimeOut()), ZKStringSerializer$.MODULE$);
//            for (KafkaTopicBean topic : topics)
//            {
//                if (AdminUtils.topicExists(zkClient, topic.getName()))
//                {
//                    logger.info("topic {} 已经存在 , 不予创建", topic.getName());
//                }
//                else
//                {
//                    AdminUtils.createTopic(zkClient, topic.getName(), topic.getPartitions(),
//                            topic.getReplicationFactor(), topic.getConfig());
//                    logger.info("topic {} 已创建", topic.getName());
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            logger.error("创建kafka topic 失败 , 失败原因:{}", e.getMessage());
//            throw new KafkaTopicCreateException(e.getMessage());
//        }
//    }
    
}
