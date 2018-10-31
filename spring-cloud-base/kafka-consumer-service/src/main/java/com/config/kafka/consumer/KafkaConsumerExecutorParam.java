package com.config.kafka.consumer;

import com.config.kafka.KafkaTopicBean;

public class KafkaConsumerExecutorParam
{
    private KafkaTopicBean kafkaTopic;
    private KafkaConsumerThreadParam threadParam;
    
    public KafkaTopicBean getKafkaTopic()
    {
        return kafkaTopic;
    }
    
    public void setKafkaTopic(KafkaTopicBean kafkaTopic)
    {
        this.kafkaTopic = kafkaTopic;
    }
    
    public KafkaConsumerThreadParam getThreadParam()
    {
        return threadParam;
    }
    
    public void setThreadParam(KafkaConsumerThreadParam threadParam)
    {
        this.threadParam = threadParam;
    }
    
}
