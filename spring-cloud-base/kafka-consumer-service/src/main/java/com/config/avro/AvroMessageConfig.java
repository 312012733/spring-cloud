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
    
    @Value("classpath:/schema/psgcar_control_result_report.avsc")
    private Resource controlResultReport;
    
    @Value("classpath:/schema/psgcar_control_result_report_push.avsc")
    private Resource controlResultReportPush;
    
    @Bean
    public Schema getControlResultReportSchema() throws IOException
    {
        String schema = new String(IOUtils.toByteArray(controlResultReport.getInputStream()), "utf-8");
        return new Schema.Parser().parse(schema);
    }
    
    @Bean
    public Schema getControlResultReportPushSchema() throws IOException
    {
        String schema = new String(IOUtils.toByteArray(controlResultReportPush.getInputStream()), "utf-8");
        return new Schema.Parser().parse(schema);
    }
    
    // @Override
    // public void setResourceLoader(ResourceLoader resourceLoader)
    // {
    // this.resourceLoader = resourceLoader;
    // }
    
}
