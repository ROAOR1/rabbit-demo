package com.top.consumer.config;

import com.rabbitmq.client.Channel;
import com.top.base.common.Constants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class Consumer {

    @RabbitListener(queues = Constants.DEFAULT_QUEUE)
    public void consumer(String msg,Channel channel,Message message) throws IOException {

        boolean flag = true;
        if (flag){
            //抛出运行时异常会进行重试，抛出检查期异常时不会进行重试，消息手动拒绝之后不会进行重试，抛出异常消息不会进入死信队列

            //如果这里抛出的异常过多，会导致mq无法得到回复，消息会一直积累在unacked中，直到mq崩溃
            //方案一：返回NACK，并重新放入队列，这种情况可能会导致死循环
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            //抛弃此条消息
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            //方案二：这里直接返回ACK，后续根据业务逻辑在这里处理
            System.out.println("此消息报错");
            throw new RuntimeException("出错了");
        }
        System.out.println("消费消息成功："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = Constants.DEAD_QUEUE)
    public void deadConsumer(String msg,Channel channel,Message message) throws IOException {
        System.out.println("死信队列处理："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
