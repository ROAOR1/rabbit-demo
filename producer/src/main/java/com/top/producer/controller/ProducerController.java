package com.top.producer.controller;

import com.top.base.common.Constants;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/direct/sendMsg")
    public void directSendMsg(){
        String msg = "direct-msg";
        MessagePostProcessor processor = (message)->{
            //设置消息过期时间为3s
            message.getMessageProperties().setExpiration("3000");
            return message;
        };
        //发送消息到交换机，并携带路由键
        rabbitTemplate.convertAndSend(Constants.DEFAULT_DIRECT_EXCHANGE,Constants.DEFAULT_DIRECT_ROUTING_KEY,msg,processor);
    }

    @RequestMapping("/topic/man/sendMsg")
    public void topicManSendMsg(){
        String msg = "error";
//        String msg = "topic-man";
        rabbitTemplate.convertAndSend(Constants.DEFAULT_TOPIC_EXCHANGE,Constants.MAN_TOPIC_ROUTING_KEY,msg);
    }

    @RequestMapping("/topic/total/sendMsg")
    public void topicTotalSendMsg(){
        String msg = "topic-total";
        rabbitTemplate.convertAndSend(Constants.DEFAULT_TOPIC_EXCHANGE,Constants.TOTAL_TOPIC_ROUTING_KEY,msg);
    }

    @RequestMapping("/fanout/sendMsg")
    public void fanoutSendMsg(){
        String msg = "fanout-msg";
        //不需要发送路由键
        rabbitTemplate.convertAndSend(Constants.DEFAULT_FANOUT_EXCHANGE,"",msg);
    }

    @RequestMapping("/dead/sendMsg")
    public void sendDeadQueueMsg(){
        //设置过期时间
        MessagePostProcessor processor = (message) -> {
            message.getMessageProperties().setExpiration("1000");
            return message;
        };

        String msg = "dead-msg";
        //发送消息到业务队列，
        rabbitTemplate.convertAndSend(Constants.DEFAULT_DIRECT_EXCHANGE,Constants.DEFAULT_DIRECT_ROUTING_KEY,msg);
    }
}
