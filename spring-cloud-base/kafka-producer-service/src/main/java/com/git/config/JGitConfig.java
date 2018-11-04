package com.git.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JGitBean.class)
public class JGitConfig
{
    @Autowired
    private JGitBean jGitBean;
    
    @Bean
    public JGitRepository jGitRepository() throws Exception
    {
        JGitUtis jGitUtis = new JGitUtis(jGitBean);
        
        return jGitUtis;
    }
    
}
