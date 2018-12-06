// package com.test;
//
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import com.rabbitmq.RabbitProducer;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class TestRabbitMQ
// {
//
// @Autowired
// private RabbitProducer rabbitProducer;
//
// @Test
// public void testRabbit()
// {
// rabbitProducer.send("queue", "hello world!!!哈哈");
//
// try
// {
// Thread.sleep(1000 * 60);
// }
// catch (InterruptedException e)
// {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
// }