package com.config.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
public class KafkaBean
{
    private List<String> topics = new ArrayList<>();
    private Properties consumerProperties = new Properties();
    private Properties producerProperties = new Properties();
    private ZookeeperBean zookeeper;
    
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
    
    public List<String> getTopics()
    {
        return topics;
    }
    
    public void setTopics(List<String> topics)
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
    
    @Override
    public String toString()
    {
        return "KafkaBean [topics=" + topics + ", consumerProperties=" + consumerProperties + ", producerProperties="
                + producerProperties + ", zookeeper=" + zookeeper + "]";
    }
    
}
