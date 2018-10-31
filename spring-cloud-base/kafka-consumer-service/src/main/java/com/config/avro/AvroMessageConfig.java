package com.config.avro;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
// @EnableConfigurationProperties(AvroBean.class)
public class AvroMessageConfig
{
    // private ResourceLoader resourceLoader;
    
    @Value("classpath:psgcar_control_result_report.avsc")
    private Resource controlResultResource;
    
    // @Autowired
    // private AvroBean avroBean;
    
    // @Autowired
    // JGitRepository jGitRepository;
    //
    // @Bean
    // public Schema getControlResultSchema()
    // {
    // return new
    // Schema.Parser().parse(jGitRepository.findOne(avroBean.getCreateControlResultAvsc(),
    // "master"));
    // }
    @Bean
    public Schema getControlResultSchema() throws IOException
    {
        String schema = new String(IOUtils.toByteArray(controlResultResource.getInputStream()), "utf-8");
        return new Schema.Parser().parse(schema);
    }
    
    // @Override
    // public void setResourceLoader(ResourceLoader resourceLoader)
    // {
    // this.resourceLoader = resourceLoader;
    // }
    
}
