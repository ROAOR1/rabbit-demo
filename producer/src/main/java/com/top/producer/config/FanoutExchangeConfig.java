package com.top.producer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeConfig {

    @Bean
    public FanoutExchange defaultFanoutExchange(){
        //扇形交换机
        return new FanoutExchange(Constants.DEFAULT_FANOUT_EXCHANGE,true,false);
    }

    @Bean
    public Queue defaultFanoutQueue1(){
        return new Queue(Constants.DEFAULT_FANOUT_QUEUE_1,true,false,false,null);
    }

    @Bean
    public Queue defaultFanoutQueue2(){
        return new Queue(Constants.DEFAULT_FANOUT_QUEUE_2,true,false,false,null);
    }

    @Bean
    public Queue defaultFanoutQueue3(){
        return new Queue(Constants.DEFAULT_FANOUT_QUEUE_3,true,false,false,null);
    }

    @Bean
    public Binding fanoutBinding1(){
        //不需要配置路由键
        return BindingBuilder.bind(defaultFanoutQueue1()).to(defaultFanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        //不需要配置路由键
        return BindingBuilder.bind(defaultFanoutQueue2()).to(defaultFanoutExchange());
    }

    @Bean
    public Binding fanoutBinding3(){
        //不需要配置路由键
        return BindingBuilder.bind(defaultFanoutQueue3()).to(defaultFanoutExchange());
    }
}
