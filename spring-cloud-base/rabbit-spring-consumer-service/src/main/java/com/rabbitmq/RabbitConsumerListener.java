package com.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avro.config.AvroConfig;

@Component
public class RabbitConsumerListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitConsumerListener.class);
    
    @Autowired
    private AvroConfig avroConfig;
    
    @RabbitListener(queues = "queue") // 监听器监听指定的Queue
    // public void controlResultConsumer(ConsumerRecord<String, byte[]> record,
    // Acknowledgment acknowledgment)
    public void controlResultConsumer(Object msg)
    {
        try
        {
            
            LOGGER.info("【----------------controlResultKafkaConsumer-----】--receive:{}", msg);
            
            // Schema schema = avroConfig.getControlResultReportSchema();
            // GenericRecord message = AvroUtil.bytesRead(record.value(),
            // schema);
            //
            // LOGGER.info("【controlResultKafkaConsumer-----offset={},
            // partition={}, key={}, value={}】", record.offset(),
            // record.partition(), record.key(), message);
            
            // JSONObject messageJson =
            // JSONObject.parseObject(message.toString());
            // consumerService(messageJson);
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
        }
    }
    
    //
}
