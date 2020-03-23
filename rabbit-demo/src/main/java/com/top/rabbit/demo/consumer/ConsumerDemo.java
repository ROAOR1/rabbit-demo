package com.top.rabbit.demo.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerDemo {
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

        String queueName = "defaultQueue";

        /**
         * 配置监听的队列
         * 第一个参数 队列名
         * 第二个参数 durable 是否持久化
         * 第三个参数 exclusive 是否独占的  相当于加了一把锁
         * 第四个参数 autoDelete 是否自动删除
         * 第五个参数 map类型，表示对队列中的消息进行详细设置，例如绑定死信队列
         */
        channel.queueDeclare(queueName,true,false,false,null);

        //创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(msg);
            }
        };

        //对消息作出应答，第二个参数表示自动ACK
        channel.basicConsume(queueName,true,consumer);
    }
}
