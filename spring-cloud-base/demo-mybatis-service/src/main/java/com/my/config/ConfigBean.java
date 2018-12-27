package com.my.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public class ConfigBean
{
    private String uploadDir;
    
    public String getUploadDir()
    {
        return uploadDir;
    }
    
    public void setUploadDir(String uploadDir)
    {
        this.uploadDir = uploadDir;
    }
    
}
