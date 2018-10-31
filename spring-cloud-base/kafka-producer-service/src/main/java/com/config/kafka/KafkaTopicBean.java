package com.config.kafka;

import java.util.Properties;

public class KafkaTopicBean
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
}
