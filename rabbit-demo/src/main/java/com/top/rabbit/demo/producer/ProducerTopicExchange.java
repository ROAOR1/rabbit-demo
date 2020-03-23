package com.top.rabbit.demo.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic交换机
 */
public class ProducerTopicExchange {

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

        //使用exchangeName交换器
        String exchangeName = "topicExchange";
        String exchangeType = "topic";
        //指定routingKey
        String routingKey1 = "user.topicKey1";
        String routingKey2 = "user.topicKey2";
        String routingKey3 = "user.topicKey3";

        //声明一个topic类型的交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);

        //这里发送三条消息，指定三个routingKey
        channel.basicPublish(exchangeName,routingKey1,null,"hello1".getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,"hello2".getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,"hello3".getBytes());

        channel.close();
        connection.close();
    }
}
