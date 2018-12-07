package com.test;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.avro.config.AvroConfig;
import com.avro.utils.AvroUtil;
import com.consumer.vo.ControlResultRabbitMsg;
import com.rabbitmq.RabbitProducer;
import com.xiaolyuh.constants.RabbitConstants;
import com.xiaolyuh.mq.message.SendMessage;
import com.xiaolyuh.mq.sender.RabbitSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRabbitMQ
{
    
//    @Autowired
//    private RabbitSender rabbitSender;
    @Autowired
    private AvroConfig avroConfig;
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    private int threadCount = 10;
    
    @Test
    public void testDead() throws Exception
    {
        amqpTemplate.convertAndSend(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON,"dead..............");
        amqpTemplate.convertAndSend(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON,"dead..............");
        amqpTemplate.convertAndSend(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON,"dead..............");
        amqpTemplate.convertAndSend(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON,"dead..............");
        amqpTemplate.convertAndSend(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON,"dead..............");
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setId(1);
//        sendMessage.setAge(20);
//        sendMessage.setName("temp");

//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
//        rabbitSender.sendMessage(RabbitConstants.MQ_EXCHANGE_SEND_AWARD, RabbitConstants.MQ_ROUTING_KEY_SEND_COUPON, "sendMessage");
        
        Thread.sleep(100000);
    }
//    @Test
//    public void testDirectRabbit() throws Exception
//    {
//        Schema controlReportSchema = avroConfig.getControlResultReportSchema();
//        
//        for (int i = 0; i < threadCount; i++)
//        {
//            final int index = i;
//            new Thread()
//            {
//                public void run()
//                {
//                    try
//                    {
//                        GenericRecord record = new GenericData.Record(controlReportSchema);
//                        record.put("vin", "vin" + index);
//                        record.put("uuid", "飞哥" + index);
//                        record.put("time", System.currentTimeMillis() + index);
//                        record.put("result", "true" + index);
//                        
//                        // amqpTemplate.convertAndSend("directExchange",
//                        // "direct", new ControlResultRabbitMsg(
//                        // "vin" + index, "飞哥" + index, "true" + index,
//                        // System.currentTimeMillis() + index));
//                        amqpTemplate.convertAndSend("directExchange", "direct",
//                                AvroUtil.bytesWrite(controlReportSchema, record), new MessagePostProcessor()
//                                {
//                                    
//                                    @Override
//                                    public Message postProcessMessage(Message message) throws AmqpException
//                                    {
//                                        try
//                                        {
//                                            Schema schema = avroConfig.getControlResultReportSchema();
//                                            GenericRecord result = AvroUtil.bytesRead(message.getBody(), schema);
//                                            
//                                            ControlResultRabbitMsg controlResultRabbitMsg = JSONObject
//                                                    .parseObject(result.toString(), ControlResultRabbitMsg.class);
//                                            
//                                            MessageProperties messageProperties = new MessageProperties();
//                                            messageProperties
//                                                    .setContentType(MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT);
//                                            
//                                            return new Message(SerializationUtils.serialize(controlResultRabbitMsg),
//                                                    messageProperties);
//                                        }
//                                        catch (Exception e)
//                                        {
//                                            // TODO Auto-generated catch block
//                                            e.printStackTrace();
//                                        }
//                                        
//                                        return null;
//                                    }
//                                });
//                                
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                };
//                
//            }.start();
//        }
//        
//        Thread.sleep(100000);
//    }
    // @Test
    // public void testFanoutRabbit()
    // {
    // amqpTemplate.convertAndSend("fanoutExchange","fanout.a",
    // "testFanoutRabbit");
    // }
    // @Test
    // public void testTopicRabbit()
    // {
    // amqpTemplate.convertAndSend("topicExchange","topic.a",
    // "testTopicRabbit");
    // }
}