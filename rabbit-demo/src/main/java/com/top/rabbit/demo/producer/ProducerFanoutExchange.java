package com.top.rabbit.demo.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * fanout交换机
 */
public class ProducerFanoutExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        String queueName = "test";
        //通过工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //通过连接创建通道
        Channel channel = connection.createChannel();

        //使用exchangeName交换器
        String exchangeName = "fanoutExchange";
        //不需要指定路由key
        String routingKey = "";
        channel.basicPublish(exchangeName,routingKey,null,"hello".getBytes());

        channel.close();
        connection.close();
    }
}
