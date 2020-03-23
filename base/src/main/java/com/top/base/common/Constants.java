package com.top.base.common;

public class Constants {
    //直连型交换机
    public static final String DEFAULT_DIRECT_EXCHANGE = "defaultDirectExchange";
    public static final String DEFAULT_DIRECT_QUEUE = "defaultDirectQueue";
    public static final String DEFAULT_DIRECT_ROUTING_KEY = "defaultDirectRoutingKey";

    //主题交换机
    public static final String DEFAULT_TOPIC_EXCHANGE = "defaultTopicExchange";
    public static final String MAN_TOPIC_QUEUE = "manTopicQueue";
    public static final String TOTAL_TOPIC_QUEUE = "totalTopicQueue";
    public static final String MAN_TOPIC_ROUTING_KEY = "user.man";
    public static final String TOTAL_TOPIC_ROUTING_KEY = "user.#";

    //扇形交换机
    public static final String DEFAULT_FANOUT_EXCHANGE = "defaultFanoutExchange";
    public static final String DEFAULT_FANOUT_QUEUE_1 = "defaultFanoutQueue1";
    public static final String DEFAULT_FANOUT_QUEUE_2 = "defaultFanoutQueue2";
    public static final String DEFAULT_FANOUT_QUEUE_3 = "defaultFanoutQueue3";


    //死信交换机
    public static final String DEAD_DIRECT_EXCHANGE = "deadDirectExchange";
    //死信队列
    public static final String DEAD_QUEUE = "deadQueue";
    //死信路由key
    public static final String DEAD_ROUTING_KEY = "deadRoutingKey";

}
