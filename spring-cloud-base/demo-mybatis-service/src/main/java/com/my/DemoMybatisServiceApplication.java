package com.my;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DemoMybatisServiceApplication
{
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException
    {
        SpringApplication.run(DemoMybatisServiceApplication.class, args);
        
    }
}
