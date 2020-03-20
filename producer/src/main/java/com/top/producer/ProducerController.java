package com.top.producer;

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

    @RequestMapping("/sendMsg")
    public void sendMsg(){
        String msg = "hello";
        //发送消息到路由key
        rabbitTemplate.convertAndSend(Constants.DEFAULT_DIRECT_EXCHANGE,Constants.DEFAULT_ROUTING_KEY,msg);
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
        rabbitTemplate.convertAndSend(Constants.DEFAULT_DIRECT_EXCHANGE,Constants.DEFAULT_ROUTING_KEY,msg);
    }
}
