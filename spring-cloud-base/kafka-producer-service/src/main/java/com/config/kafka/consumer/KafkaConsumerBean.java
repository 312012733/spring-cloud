package com.config.kafka.consumer;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.config.kafka.KafkaTopicBean;
import com.config.kafka.KafkaZookeeperBean;

@ConfigurationProperties("kafka.consumer")
public class KafkaConsumerBean
{
    private KafkaZookeeperBean zookeeper = new KafkaZookeeperBean();
    private String serializer = "kafka.serializer.DefaultEncoder";
    private String group;
    private String interval = "1000";
    private String offsetReset = "smallest";
    private List<KafkaTopicBean> topics;
    
    public String getOffsetReset()
    {
        return offsetReset;
    }
    
    public void setOffsetReset(String offsetReset)
    {
        this.offsetReset = offsetReset;
    }
    
    public List<KafkaTopicBean> getTopics()
    {
        return topics;
    }
    
    public void setTopics(List<KafkaTopicBean> topics)
    {
        this.topics = topics;
    }
    
    public KafkaZookeeperBean getZookeeper()
    {
        return zookeeper;
    }
    
    public void setZookeeper(KafkaZookeeperBean zookeeper)
    {
        this.zookeeper = zookeeper;
    }
    
    public String getSerializer()
    {
        return serializer;
    }
    
    public void setSerializer(String serializer)
    {
        this.serializer = serializer;
    }
    
    public String getGroup()
    {
        return group;
    }
    
    public void setGroup(String group)
    {
        this.group = group;
    }
    
    public String getInterval()
    {
        return interval;
    }
    
    public void setInterval(String interval)
    {
        this.interval = interval;
    }
}
