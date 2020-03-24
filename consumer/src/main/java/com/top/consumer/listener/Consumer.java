package com.top.consumer.listener;

import com.rabbitmq.client.Channel;
import com.top.base.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = Constants.DEFAULT_DIRECT_QUEUE)
    public void directConsumer(String msg, Channel channel,Message message) throws IOException {
//        //死信队列测试使用
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);

        try{
            //这里表示消息消费成功
            System.out.println("直连型交换机收到消息："+msg);
            //确认消息，false表示不批量处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            //这里表示消息消费失败，当消息消费失败时，如果没有进行处理，会导致MQ中Unacked的消息越来越多，最终占用内存越来越大

            //方案一：返回NACK，并重新放入队列，这种情况可能会导致死循环
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);

            //方案二，这里也返回ACK，记录报错日志，后续根据日志进行恢复处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.error("消息消费失败：{}", e.getMessage());
        }
    }

    @RabbitListener(queues = Constants.MAN_TOPIC_QUEUE)
    public void topicManConsumer(String msg, Channel channel,Message message) throws IOException {
//        if ("error".equals(msg)){
//            //这里抛出检查其期异常不会进行重试，抛出检查期异常时重试多少次还是会抛出异常
//            //消息被拒绝之后不会进行重试
//            //抛出运行时异常会进行重试
//            System.out.println("消息消费失败");
//            throw new RuntimeException("业务出错");
//        }
        System.out.println("主题交换机收到消息-man："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = Constants.TOTAL_TOPIC_QUEUE)
    public void topicTotalConsumer(String msg, Channel channel,Message message) throws IOException {
        System.out.println("主题交换机收到消息-total："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = Constants.DEFAULT_FANOUT_QUEUE_1)
    public void fanoutConsumer1(String msg, Channel channel,Message message) throws IOException {
        System.out.println("扇形交换机收到消息-1："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = Constants.DEFAULT_FANOUT_QUEUE_2)
    public void fanoutConsumer2(String msg, Channel channel,Message message) throws IOException {
        System.out.println("扇形交换机收到消息-2："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = Constants.DEFAULT_FANOUT_QUEUE_3)
    public void fanoutConsumer3(String msg, Channel channel,Message message) throws IOException {
        System.out.println("扇形交换机收到消息-3："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    //监听死信队列
    @RabbitListener(queues = Constants.DEAD_QUEUE)
    public void deadConsumer(String msg,Channel channel,Message message) throws IOException {
        System.out.println("死信队列处理："+msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
