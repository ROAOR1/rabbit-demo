package com.top.rabbit.demo.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 不处理路由键，只需要简单的将队列绑定到交换机上
 * 发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
 * 所以Fanout交换机转发消息是最快的
 */
public class ConsumerFanoutExchange1 {
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

        String exchangeName = "fanoutExchange";
        String exchangeType = "fanout";
        String queueName = "defaultFanout1";
        //不需要指定路由key
        String routingKey = "";

        //所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上
        //topicExchange将RouteKey和某Topic进行模糊匹配

        //声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        //建立绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        //创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(msg);
            }
        };

        channel.basicConsume(queueName,true,consumer);
    }
}
