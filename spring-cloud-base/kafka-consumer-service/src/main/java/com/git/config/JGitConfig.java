package com.git.config;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableConfigurationProperties(JGitBean.class)
public class JGitConfig implements ResourceLoaderAware
{
    
    private ResourceLoader resourceLoader;
    
    @Autowired
    private JGitBean jGitBean;
    
    @Bean
    public JGitRepository jGitRepository() throws Exception
    {
        JGitUtis.cloneRepository(jGitBean.getUri(), jGitBean.getBasedir());
        
        return new JGitRepository()
        {
            
            @Override
            public String findOne(String fileNamePath) throws Exception
            {
                String file = jGitBean.getBasedir() + File.separator + fileNamePath;
                // Resource resource = resourceLoader.getResource(file);
                byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
                
                return new String(byteArray, "utf-8");
            }
        };
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
}
