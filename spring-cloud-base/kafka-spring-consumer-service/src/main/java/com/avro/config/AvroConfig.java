package com.avro.config;

import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.git.config.JGitConfig;
import com.git.config.JGitRepository;

@Configuration
@EnableConfigurationProperties(AvroBean.class)
@ConditionalOnBean(JGitConfig.class)
public class AvroConfig
{
    @Autowired
    private AvroBean avroBean;
    
    @Autowired
    private JGitRepository jGitRepository;
    
    @Bean
    public Schema getControlResultReportSchema() throws Exception
    {
        String schemaStr = jGitRepository.findOne(avroBean.getControlReportAvsc());
        
        return new Schema.Parser().parse(schemaStr);
    }
    
    @Bean
    public Schema getControlResultReportPushSchema() throws Exception
    {
        String schemaStr = jGitRepository.findOne(avroBean.getControlReportPushAvsc());
        
        return new Schema.Parser().parse(schemaStr);
    }
    
    // @Value("classpath:/schema/psgcar_control_result_report.avsc")
    // private Resource controlResultReport;
    //
    // @Value("classpath:/schema/psgcar_control_result_report_push.avsc")
    // private Resource controlResultReportPush;
    //
    // @Bean
    // public Schema getControlResultReportSchema() throws IOException
    // {
    // String schema = new
    // String(IOUtils.toByteArray(controlResultReport.getInputStream()),
    // "utf-8");
    // return new Schema.Parser().parse(schema);
    // }
    //
    // @Bean
    // public Schema getControlResultReportPushSchema() throws IOException
    // {
    // String schema = new
    // String(IOUtils.toByteArray(controlResultReportPush.getInputStream()),
    // "utf-8");
    // return new Schema.Parser().parse(schema);
    // }
    
}
