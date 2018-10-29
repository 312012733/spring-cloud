package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TempServiceApplication
{
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException
    {
        SpringApplication.run(TempServiceApplication.class, args);
        
    }
}
