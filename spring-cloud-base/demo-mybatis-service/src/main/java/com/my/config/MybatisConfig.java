package com.my.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages =
{ "com.my.dao" })
public class MybatisConfig
{
    
}
