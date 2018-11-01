package com;

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

import com.config.avro.AvroMessageConfig;
import com.config.avro.AvroUtil;
import com.config.kafka.KafkaBean;
import com.config.kafka.KafkaBean.TopicBean;
import com.config.kafka.KafkaConfig;
import com.config.kafka.KafkaUtils;
import com.kafka.consumer.ControlResultKafkaConsumerThread;
import com.kafka.consumer.ControlResultPushKafkaConsumerThread;
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
        
        for (TopicBean topicBean : topics)
        {
            
            String topic = topicBean.getName();
            
            if (topic.equals(controlReportSchema.getName()))
            {
                Runnable task = new ControlResultKafkaConsumerThread(topic, controlReportSchema,
                        kafkaConfig.kafkaConsumer(), context);
                ThreadUtils.execute(task);
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                Runnable task = new ControlResultPushKafkaConsumerThread(topic, controlReportPushSchema,
                        kafkaConfig.kafkaConsumer(), context);
                ThreadUtils.execute(task);
            }
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
            
        }
        /***************************************************************/
        for (int i = 0; i < 8; i++)
        {
            KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
            
            GenericRecord record = new GenericData.Record(controlReportSchema);
            record.put("vin", "vin" + i);
            record.put("uuid", "uuid" + i);
            record.put("time", System.currentTimeMillis());
            record.put("result", "true" + i);
            
            KafkaUtils.produce(controlReportSchema.getName(), producer,
                    AvroUtil.bytesWrite(controlReportSchema, record));
            
        }
        
        System.out.println("----------controlReportSchema--------------------------------------------");
        //////////////////////////////////////////////////////////////////////////
        // record = new GenericData.Record(controlReportPushSchema);
        // record.put("vin", "vin1");
        // record.put("action", 6);
        // record.put("uuid", "uuid1");
        // record.put("result", "true");
        // record.put("time", System.currentTimeMillis());
        //
        // KafkaUtils.produce(controlReportPushSchema.getName(), producer,
        // AvroUtil.bytesWrite(controlReportPushSchema, record));
        //
        // System.out.println("----------controlReportPushSchema--------------------------------------------");
    }
    
}
