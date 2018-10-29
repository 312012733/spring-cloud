package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableEurekaClient
// @EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication
{
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException
    {
        SpringApplication.run(ZuulApplication.class, args);
        
    }
}
