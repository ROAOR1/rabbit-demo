package com.top.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            //成功发送到交换机会触发该回调
            if (ack){
                //表示消息发送成功，这里不做任何操作，这里只是为了写个注释（手动狗头）
            }else {
                //表示消息成功发送到服务器，但是没有找到交换器，这里可以记录日志，方便后续处理
                logger.warn("ConfirmCallback -> 消息发布到交换器失败，错误原因为：{}", cause);
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            //表示消息发送到交换机，但是没有找到队列，这里记录日志
            logger.warn("ReturnCallback -> 消息{}, {} 发送到队列失败，应答码：{}，原因：{}，交换器: {}，路由键：{}",
                    message,
                    message.getMessageProperties().getCorrelationId(),
                    replyCode,
                    replyText,
                    exchange,
                    routingKey);
        });

        return rabbitTemplate;
    }
}
