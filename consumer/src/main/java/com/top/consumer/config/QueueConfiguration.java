package com.top.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.text.bidi.BidiLine;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class QueueConfiguration {

    //业务队列 :demo
    @Bean
    public Queue directQueue(){
        return new Queue("demo",true,false,false);
    }

    //业务交换机 ：defaultDirectExchange
    @Bean
    public DirectExchange defaultDirectExchange(){
        return new DirectExchange("defaultDirectExchange",true,false);
    }

    //将队列和交换机绑定，并设置匹配件 routingKey
    @Bean
    public Binding directQueueBinding(){
        return BindingBuilder.bind(directQueue()).to(defaultDirectExchange()).with("demo");
    }
}