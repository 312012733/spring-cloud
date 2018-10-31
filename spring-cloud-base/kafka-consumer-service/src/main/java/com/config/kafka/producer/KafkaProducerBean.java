package com.config.kafka.producer;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.config.kafka.KafkaTopicBean;
import com.config.kafka.KafkaZookeeperBean;

@ConfigurationProperties("kafka.producer")
public class KafkaProducerBean
{
    private KafkaProducerBrokerBean broker = new KafkaProducerBrokerBean();
    private KafkaZookeeperBean zookeeper = new KafkaZookeeperBean();
    private String serializer = "kafka.serializer.DefaultEncoder";
    private String acks = "0";
    private String partitioner = "com.changhongit.tcar.kafka.MessagePartitioner";
    private String partitions = "8";
    
    private List<KafkaTopicBean> topics;
    
    public static class KafkaProducerBrokerBean
    {
        private String connect = "172.28.24.48:9092,172.28.24.49:9092,172.28.24.50:9092";
        
        public String getConnect()
        {
            return connect;
        }
        
        public void setConnect(String connect)
        {
            this.connect = connect;
        }
    }
    
    public KafkaZookeeperBean getZookeeper()
    {
        return zookeeper;
    }
    
    public void setZookeeper(KafkaZookeeperBean zookeeper)
    {
        this.zookeeper = zookeeper;
    }
    
    public List<KafkaTopicBean> getTopics()
    {
        return topics;
    }
    
    public void setTopics(List<KafkaTopicBean> topics)
    {
        this.topics = topics;
    }
    
    public String getSerializer()
    {
        return serializer;
    }
    
    public void setSerializer(String serializer)
    {
        this.serializer = serializer;
    }
    
    public String getAcks()
    {
        return acks;
    }
    
    public void setAcks(String acks)
    {
        this.acks = acks;
    }
    
    public String getPartitioner()
    {
        return partitioner;
    }
    
    public void setPartitioner(String partitioner)
    {
        this.partitioner = partitioner;
    }
    
    public String getPartitions()
    {
        return partitions;
    }
    
    public void setPartitions(String partitions)
    {
        this.partitions = partitions;
    }
    
    public KafkaProducerBrokerBean getBroker()
    {
        return broker;
    }
    
    public void setBroker(KafkaProducerBrokerBean broker)
    {
        this.broker = broker;
    }
}
