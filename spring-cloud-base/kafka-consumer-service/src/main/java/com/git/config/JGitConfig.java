package com.git.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.git.utils.JGitUtis;

@Configuration
@ConditionalOnProperty(matchIfMissing = false, prefix = "service.git", name =
{ "uri", "basedir" })
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
