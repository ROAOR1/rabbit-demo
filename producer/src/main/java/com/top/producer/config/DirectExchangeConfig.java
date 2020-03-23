package com.top.producer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Bean
    public DirectExchange defaultExchange(){
        //直连型交换机，持久化消息，不自动删除
        return new DirectExchange("test",true,false);
    }

    @Bean
    public DirectExchange defaultDirectExchange(){
        //直连型交换机，持久化消息，不自动删除
        return new DirectExchange(Constants.DEFAULT_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Queue defaultDirectQueue(){
        //声明队列，持久化消息，非独有，不自动删除
        return new Queue(Constants.DEFAULT_DIRECT_QUEUE,true,false,false,null);
    }

    @Bean
    public Binding directBiding(){
        //将队列和交换机绑定，并设置路由键
        return BindingBuilder.bind(defaultDirectQueue()).to(defaultDirectExchange()).with(Constants.DEFAULT_DIRECT_ROUTING_KEY);
    }
}
