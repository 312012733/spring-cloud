package com.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages =
{ "com.dao" })
public class MybatisConfig
{
    
}
