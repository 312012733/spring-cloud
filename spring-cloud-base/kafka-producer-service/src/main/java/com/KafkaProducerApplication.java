package com;

import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.avro.config.AvroConfig;
import com.avro.utils.AvroUtil;
import com.kafka.config.KafkaBean;
import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.config.KafkaConfig;
import com.kafka.consumer.impl.ControlResultKafkaConsumerThread;
import com.kafka.consumer.impl.ControlResultPushKafkaConsumerThread;
import com.kafka.utils.KafkaUtils;
import com.utils.ThreadUtils;

@EnableEurekaClient
@SpringBootApplication
public class KafkaProducerApplication
{
    final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducerApplication.class);
    
    public static void main(String[] args) throws Exception
    {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
        
        KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
        
        AvroConfig avroeConfig = context.getBean(AvroConfig.class);
        Schema controlReportSchema = avroeConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroeConfig.getControlResultReportPushSchema();
        
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        List<TopicBean> topics = kafkaBean.getTopics();
        
        for (TopicBean topicBean : topics)
        {
            String topic = topicBean.getName();
            
            if (topic.equals(controlReportSchema.getName()))
            {
                for (int i = 0; i < topicBean.getPartitions(); i++)
                {
                    Runnable task = new ControlResultKafkaConsumerThread(topic, null, controlReportSchema,
                            kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
                
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                for (int i = 0; i < topicBean.getPartitions(); i++)
                {
                    Runnable task = new ControlResultPushKafkaConsumerThread(topic, null, controlReportPushSchema,
                            kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
                
            }
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
        }
        
        /********************************************************/
        
        testSendControlReport(kafkaConfig, controlReportSchema, 8);
        testSendControlReportPush(kafkaConfig, controlReportPushSchema, 8);
        
        Thread.sleep(1000 * 10);
        context.close();
        System.exit(0);
        
    }
    
    public static void testSendControlReport(KafkaConfig kafkaConfig, Schema controlReportSchema, int threadCount)
            throws IOException
    {
        KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
        
        for (int i = 0; i < threadCount; i++)
        {
            // final int index = i;
            new Thread()
            {
                public void run()
                {
                    try
                    {
                        GenericRecord record = new GenericData.Record(controlReportSchema);
                        record.put("vin", "9527");
                        record.put("uuid", "same");
                        // record.put("uuid", UUID.randomUUID().toString());
                        record.put("time", System.currentTimeMillis());
                        record.put("result", "true");
                        
                        KafkaUtils.produce(controlReportSchema.getName(), producer,
                                AvroUtil.bytesWrite(controlReportSchema, record));
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
    
    public static void testSendControlReportPush(KafkaConfig kafkaConfig, Schema controlReportPushSchema,
            int threadCount) throws IOException
    {
        KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
        
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
                        
                        KafkaUtils.produce(controlReportPushSchema.getName(), producer,
                                AvroUtil.bytesWrite(controlReportPushSchema, record));
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
