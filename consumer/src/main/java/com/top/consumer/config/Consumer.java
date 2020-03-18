package com.top.consumer.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "demo")
    public void consumer(String msg){
        System.out.println(msg);
    }
}
