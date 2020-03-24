package com.top.producer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列配置文件
 */
@Configuration
public class DeadDirectExchangeConfig {

    @Bean
    public DirectExchange deadDirectExchange(){
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
