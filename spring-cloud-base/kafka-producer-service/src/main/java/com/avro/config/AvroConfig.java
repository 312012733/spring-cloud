package com.avro.config;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.git.config.JGitRepository;

@Configuration
// @EnableConfigurationProperties(AvroBean.class)
public class AvroConfig
{
    @Autowired
    private JGitRepository jGitRepository;
    
    @Bean
    public Schema getControlResultReportSchema() throws Exception
    {
        String schemaStr = jGitRepository.findOne("avro" + File.separator + "psgcar_control_result_report.avsc");
        
        return new Schema.Parser().parse(schemaStr);
    }
    
    @Value("classpath:/schema/psgcar_control_result_report.avsc")
    private Resource controlResultReport;
    
    @Value("classpath:/schema/psgcar_control_result_report_push.avsc")
    private Resource controlResultReportPush;
    
    // @Bean
    // public Schema getControlResultReportSchema() throws IOException
    // {
    // String schema = new
    // String(IOUtils.toByteArray(controlResultReport.getInputStream()),
    // "utf-8");
    // return new Schema.Parser().parse(schema);
    // }
    //
    @Bean
    public Schema getControlResultReportPushSchema() throws IOException
    {
        String schema = new String(IOUtils.toByteArray(controlResultReportPush.getInputStream()), "utf-8");
        return new Schema.Parser().parse(schema);
    }
    
}
