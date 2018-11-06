package com.kafka.consumer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.avro.config.AvroConfig;
import com.avro.utils.AvroUtil;

@Component
public class KafkaConsumerListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);
    
    @Autowired
    private AvroConfig avroConfig;
    
    //
    // @KafkaListener(topicPartitions =
    // { @TopicPartition(topic = "psgcar_control_result_report", partitions =
    // { "0" ,"1" ,"2" ,"3" ,"4" ,"5" ,"6" ,"7" }) })
    @KafkaListener(topics =
    { "psgcar_control_result_report" })
    public void controlResultKafkaConsumer(ConsumerRecord<String, byte[]> record, Acknowledgment ack)
    {
        try
        {
            Schema schema = avroConfig.getControlResultReportSchema();
            GenericRecord message = AvroUtil.bytesRead(record.value(), schema);
            
            LOGGER.info("【controlResultKafkaConsumer-----offset={}, partition={}, key={}, value={}】", record.offset(),
                    record.partition(), record.key(), message);
            
            // JSONObject messageJson =
            // JSONObject.parseObject(message.toString());
            // consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
    }
    
    @KafkaListener(topics =
    { "psgcar_control_result_report_push" })
    public void getControlResultReportPushSchema(ConsumerRecord<String, byte[]> record)
    {
        try
        {
            Schema schema = avroConfig.getControlResultReportPushSchema();
            GenericRecord message = AvroUtil.bytesRead(record.value(), schema);
            
            LOGGER.info("【getControlResultReportPushSchema-----offset={}, partition={}, key={}, value={}】",
                    record.offset(), record.partition(), record.key(), message);
            
            // JSONObject messageJson =
            // JSONObject.parseObject(message.toString());
            // consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
    }
}
