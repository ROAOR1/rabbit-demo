package com.top.producer.config;

import com.top.base.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeConfig {

    @Bean
    public TopicExchange defaultTopicExchange(){
        //主题交换机
        return new TopicExchange(Constants.DEFAULT_TOPIC_EXCHANGE,true,false);
    }

    @Bean
    public Queue manTopicQueue(){
        //只会接受到user.man的数据
        return new Queue(Constants.MAN_TOPIC_QUEUE,true,false,false,null);
    }

    @Bean
    public Queue totalTopicQueue(){
        //会接收到路由键为user.#的数据
        return new Queue(Constants.TOTAL_TOPIC_QUEUE,true,false,false,null);
    }

    @Bean
    public Binding manTopicBinding(){
        return BindingBuilder.bind(manTopicQueue()).to(defaultTopicExchange()).with(Constants.MAN_TOPIC_ROUTING_KEY);
    }

    @Bean
    public Binding totalTopicBinding(){
        return BindingBuilder.bind(totalTopicQueue()).to(defaultTopicExchange()).with(Constants.TOTAL_TOPIC_ROUTING_KEY);
    }
}
