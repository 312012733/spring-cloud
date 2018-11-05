package com.kafka.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
public class KafkaBean
{
    private List<TopicBean> topics = new ArrayList<>();
    
    private Properties consumerProperties = new Properties();
    private Properties producerProperties = new Properties();
    
    private ZookeeperBean zookeeper;
    
    public static class TopicBean
    {
        private String name;
        private int partitions = 8;
        private int replicationFactor = 1;
        private Properties config = new Properties();
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public int getPartitions()
        {
            return partitions;
        }
        
        public void setPartitions(int partitions)
        {
            this.partitions = partitions;
        }
        
        public int getReplicationFactor()
        {
            return replicationFactor;
        }
        
        public void setReplicationFactor(int replicationFactor)
        {
            this.replicationFactor = replicationFactor;
        }
        
        public Properties getConfig()
        {
            return config;
        }
        
        public void setConfig(Properties config)
        {
            this.config = config;
        }
        
        @Override
        public String toString()
        {
            return "KafkaTopicBean [name=" + name + ", partitions=" + partitions + ", replicationFactor="
                    + replicationFactor + ", config=" + config + "]";
        }
        
    }
    
    public static class ZookeeperBean
    {
        private String connect;
        
        public String getConnect()
        {
            return connect;
        }
        
        public void setConnect(String connect)
        {
            this.connect = connect;
        }
        
        @Override
        public String toString()
        {
            return "ZookeeperBean [connect=" + connect + "]";
        }
        
    }
    
    public List<TopicBean> getTopics()
    {
        return topics;
    }
    
    public void setTopics(List<TopicBean> topics)
    {
        this.topics = topics;
    }
    
    public Properties getConsumerProperties()
    {
        return consumerProperties;
    }
    
    public void setConsumerProperties(Properties consumerProperties)
    {
        this.consumerProperties = consumerProperties;
    }
    
    public Properties getProducerProperties()
    {
        return producerProperties;
    }
    
    public void setProducerProperties(Properties producerProperties)
    {
        this.producerProperties = producerProperties;
    }
    
    public ZookeeperBean getZookeeper()
    {
        return zookeeper;
    }
    
    public void setZookeeper(ZookeeperBean zookeeper)
    {
        this.zookeeper = zookeeper;
    }
    
}
