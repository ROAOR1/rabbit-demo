package com.top.rabbit.demo.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //通过工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //通过连接创建通道
        Channel channel = connection.createChannel();
        channel.queueDeclare("test",true,false,false,null);

        //创建消费者
    }
}
