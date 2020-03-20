package com.top.consumer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueConfiguration {

    @Bean
    public DirectExchange defaultDirectExchange(){
        //业务交换机 ,持久化消息，不自动删除
        return new DirectExchange(Constants.DEFAULT_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Queue directQueue(){
        //为业务队列，绑定死信队列
        Map<String, Object> map = new HashMap<>();
        //声明队列绑定的死信交换机
        map.put("x-dead-letter-exchange", Constants.DEAD_DIRECT_EXCHANGE);
        //声明队列绑定的路由key
        map.put("x-dead-letter-routing-key", Constants.DEAD_ROUTING_KEY);
        return new Queue(Constants.DEFAULT_QUEUE,true,false,false,map);
    }

    @Bean
    public Binding directQueueBinding(){
        //将业务队列和交换机绑定，并设置路由键
        return BindingBuilder.bind(directQueue()).to(defaultDirectExchange()).with(Constants.DEFAULT_ROUTING_KEY);
    }

    @Bean
    public DirectExchange deadDirectExchange(){
        //死信交换机
        return new DirectExchange(Constants.DEAD_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Queue deadQueue(){
        return new Queue(Constants.DEAD_QUEUE,true,false,false);
    }

    @Bean
    public Binding deadQueueBiding(){
        //绑定死信队列和死信交换机、路由key
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with(Constants.DEAD_ROUTING_KEY);
    }

}