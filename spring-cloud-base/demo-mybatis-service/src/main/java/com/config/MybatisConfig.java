package com.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages =
{ "com.dao", "com.dao2" })
public class MybatisConfig
{
    
}
