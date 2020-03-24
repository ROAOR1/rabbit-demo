package com.top.producer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DirectExchangeConfig {

    @Bean
    public DirectExchange defaultDirectExchange(){
        //直连型交换机，持久化消息，不自动删除
        return new DirectExchange(Constants.DEFAULT_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Queue defaultDirectQueue(){
        //给普通队列绑定死信交换机，设置路由键
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Constants.DEAD_DIRECT_EXCHANGE);
        map.put("x-dead-letter-routing-key", Constants.DEAD_ROUTING_KEY);
        //声明队列，持久化消息，非独有，不自动删除，消息属性
        return new Queue(Constants.DEFAULT_DIRECT_QUEUE,true,false,false,map);
    }

    @Bean
    public Binding directBiding(){
        //将队列和交换机绑定，并设置路由键
        return BindingBuilder.bind(defaultDirectQueue()).to(defaultDirectExchange()).with(Constants.DEFAULT_DIRECT_ROUTING_KEY);
    }
}
