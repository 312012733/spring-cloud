package com;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import com.avro.config.AvroConfig;
import com.avro.utils.AvroUtil;

@EnableEurekaClient
@SpringBootApplication
public class KafkaSpringProducerApplication
{
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaSpringProducerApplication.class);
    
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception
    {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaSpringProducerApplication.class, args);
        
        /********************************************************/
        
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, byte[]> kafkaTemplate = context.getBean(KafkaTemplate.class);
        
        AvroConfig avroeConfig = context.getBean(AvroConfig.class);
        Schema controlReportSchema = avroeConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroeConfig.getControlResultReportPushSchema();
        
        testSendControlReport(kafkaTemplate, controlReportSchema, 8);
        testSendControlReportPush(kafkaTemplate, controlReportPushSchema, 8);
        
        Thread.sleep(1000 * 10);
        context.close();
        System.exit(0);
        
    }
    
    public static void testSendControlReport(KafkaTemplate<String, byte[]> kafkaTemplate, Schema controlReportSchema,
            int threadCount) throws IOException
    {
        // KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
        
        for (int i = 0; i < threadCount; i++)
        {
            final int index = i;
            new Thread()
            {
                public void run()
                {
                    try
                    {
                        GenericRecord record = new GenericData.Record(controlReportSchema);
                        record.put("vin", "vin" + index);
                        record.put("uuid", "飞哥" + index);
                        record.put("time", System.currentTimeMillis() + index);
                        record.put("result", "true" + index);
                        
                        kafkaTemplate.send(controlReportSchema.getName(),
                                AvroUtil.bytesWrite(controlReportSchema, record));
                        
                        // KafkaUtils.produce(controlReportSchema.getName(),
                        // producer,
                        // AvroUtil.bytesWrite(controlReportSchema, record));
                    }
                    catch (IOException e)
                    {
                        LOGGER.error("", e);
                    }
                };
                
            }.start();
        }
        
        LOGGER.info("-----------------testSendControlReport---------over------------");
    }
    
    public static void testSendControlReportPush(KafkaTemplate<String, byte[]> kafkaTemplate,
            Schema controlReportPushSchema, int threadCount) throws IOException
    {
        // KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
        
        for (int i = 0; i < threadCount; i++)
        {
            final int index = i;
            
            new Thread()
            {
                public void run()
                {
                    try
                    {
                        GenericRecord record = new GenericData.Record(controlReportPushSchema);
                        record.put("vin", "vin" + index);
                        record.put("action", 6 + index);
                        record.put("uuid", "uuid" + index);
                        record.put("result", "true" + index);
                        record.put("time", System.currentTimeMillis() + index);
                        
                        kafkaTemplate.send(controlReportPushSchema.getName(),
                                AvroUtil.bytesWrite(controlReportPushSchema, record));
                        
                        // KafkaUtils.produce(controlReportPushSchema.getName(),
                        // producer,
                        // AvroUtil.bytesWrite(controlReportPushSchema,
                        // record));
                    }
                    catch (IOException e)
                    {
                        LOGGER.error("", e);
                    }
                };
                
            }.start();
        }
        
        LOGGER.info("-----------------testSendControlReport---------over------------");
    }
}
