package com.config.kafka;

public class KafkaTopicCreateException extends RuntimeException
{
    private static final long serialVersionUID = 5450478180696358602L;
    
    public KafkaTopicCreateException(String string)
    {
        super(string);
    }
    
}
