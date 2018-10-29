package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableEurekaClient
// @EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
public class HystrixApplication
{
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException
    {
        SpringApplication.run(HystrixApplication.class, args);
        
    }
}
