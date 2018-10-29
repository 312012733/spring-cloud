package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class FeignApplication
{
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException
    {
        SpringApplication.run(FeignApplication.class, args);
        
    }
}
