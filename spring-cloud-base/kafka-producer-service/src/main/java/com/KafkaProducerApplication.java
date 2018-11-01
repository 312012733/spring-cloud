package com;

import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.avro.AvroMessageConfig;
import com.config.avro.AvroUtil;
import com.config.kafka.KafkaBean;
import com.config.kafka.KafkaConfig;
import com.config.kafka.KafkaUtils;
import com.kafka.consumer.ControlResultKafkaConsumerThread;
import com.kafka.consumer.ControlResultPushKafkaConsumerThread;
import com.utils.ThreadUtils;

@EnableEurekaClient
@SpringBootApplication
public class KafkaProducerApplication
{
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaProducerApplication.class);
    
    public static void main(String[] args) throws Exception
    {
        
        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
        
        KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
        KafkaBean kafkaBean = context.getBean(KafkaBean.class);
        KafkaConsumer<String, byte[]> kafkaConsumer = kafkaConfig.kafkaConsumer();
        
        AvroMessageConfig avroMessageConfig = context.getBean(AvroMessageConfig.class);
        Schema controlReportSchema = avroMessageConfig.getControlResultReportSchema();
        Schema controlReportPushSchema = avroMessageConfig.getControlResultReportPushSchema();
        
        List<String> topics = kafkaBean.getTopics();
        
        for (String topic : topics)
        {
            
            if (topic.equals(controlReportSchema.getName()))
            {
                Runnable task = new ControlResultKafkaConsumerThread(topic, controlReportSchema, kafkaConsumer,
                        context);
                ThreadUtils.execute(task);
            }
            else if (topic.equals(controlReportPushSchema.getName()))
            {
                Runnable task = new ControlResultPushKafkaConsumerThread(topic, controlReportPushSchema, kafkaConsumer,
                        context);
                ThreadUtils.execute(task);
            }
            else
            {
                throw new IllegalArgumentException("topicName and schemaName did not match up.");
            }
        }
        
        /***************************************************************/
        KafkaProducer<String, byte[]> producer = kafkaConfig.kafkaProducer();
        
        GenericRecord record = new GenericData.Record(controlReportSchema);
        record.put("vin", "vin");
        record.put("uuid", "uuid");
        record.put("time", System.currentTimeMillis());
        record.put("result", "true");
        
        KafkaUtils.produce(controlReportSchema.getName(), producer, AvroUtil.bytesWrite(controlReportSchema, record));
        
        //////////////////////////////////////////////////////////////////////////
        record = new GenericData.Record(controlReportPushSchema);
        record.put("vin", "vin");
        record.put("uuid", "uuid");
        record.put("time", System.currentTimeMillis());
        record.put("action", 6);
        record.put("result", "true");
        
        KafkaUtils.produce(controlReportPushSchema.getName(), producer,
                AvroUtil.bytesWrite(controlReportSchema, record));
    }
    
    public static void send(KafkaProducer<String, byte[]> producer, String topic, byte[] value)
    {
        try
        {
            producer.send(new ProducerRecord<String, byte[]>(topic, "hello", value));
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
            
        }
        finally
        {
            // producer.close();
        }
        
    }
    
}
