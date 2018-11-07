package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaClient
@SpringBootApplication
public class KafkaSpringConsumerApplication
{
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaSpringConsumerApplication.class);
    
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception
    {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaSpringConsumerApplication.class, args);
        
        /********************************************************/
    }
    
}
