package com;

import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.avro.config.AvroMessageConfig;
import com.avro.utils.AvroUtil;
import com.consumer.ControlResultKafkaConsumerThread;
import com.consumer.ControlResultPushKafkaConsumerThread;
import com.kafka.config.KafkaBean;
import com.kafka.config.KafkaBean.TopicBean;
import com.kafka.config.KafkaConfig;
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
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        
        AvroMessageConfig avroMessageConfig = context.getBean(AvroMessageConfig.class);
        Schema controlReportSchema = avroMessageConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroMessageConfig.getControlResultReportPushSchema();
        
        List<TopicBean> topics = kafkaBean.getTopics();
        
        KafkaUtils.createTopics(topics, kafkaConfig.zkUtils());
        
        KafkaConsumer<String, byte[]> kafkaConsumer = kafkaConfig.kafkaConsumer();
        
        for (TopicBean topicBean : topics)
        {
            String topic = topicBean.getName();
            
            if (topic.equals(controlReportSchema.getName()))
            {
                List<PartitionInfo> partitions = kafkaConsumer.partitionsFor(topic);
                
                for (PartitionInfo partitionInfo : partitions)
                {
                    Runnable task = new ControlResultKafkaConsumerThread(topic, partitionInfo.partition(),
                            controlReportSchema, kafkaConfig.kafkaConsumer(), context);
                    ThreadUtils.execute(task);
                }
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                List<PartitionInfo> partitions = kafkaConsumer.partitionsFor(topic);
                
                for (PartitionInfo partitionInfo : partitions)
                {
                    Runnable task = new ControlResultPushKafkaConsumerThread(topic, partitionInfo.partition(),
                            controlReportPushSchema, kafkaConfig.kafkaConsumer(), context);
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
            final int index = i;
            new Thread()
            {
                public void run()
                {
                    try
                    {
                        GenericRecord record = new GenericData.Record(controlReportSchema);
                        record.put("vin", "vin" + index);
                        record.put("uuid", "飞哥哥" + index);
                        record.put("time", System.currentTimeMillis() + index);
                        record.put("result", "true" + index);
                        
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
