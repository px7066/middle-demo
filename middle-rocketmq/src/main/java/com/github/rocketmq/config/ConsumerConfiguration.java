package com.github.rocketmq.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.github.common.exception.RocketConfigException;
import com.github.rocketmq.listen.ConsumerMsgListenerProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>消费者配置</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Slf4j
@Data
public class ConsumerConfiguration {
    private String namesrvAddr;
    private String groupName;
    private Integer consumeThreadMin;
    private Integer consumeThreadMax;
    private String topics;
    private Integer consumeMessageBatchMaxSize;

    @Autowired
    private ConsumerMsgListenerProcessor consumerMsgListenerProcessor;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketConfigException{
        if(StringUtils.isEmpty(groupName)){
            throw new RocketConfigException("groupName is not empty");
        }
        if(StringUtils.isEmpty(namesrvAddr)){
            throw new RocketConfigException("namesrvAddr is not empty");
        }
        if(StringUtils.isEmpty(topics)){
            throw new RocketConfigException("topics is not empty");
        }
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(groupName);
        pushConsumer.setNamesrvAddr(namesrvAddr);
        pushConsumer.setMessageListener(consumerMsgListenerProcessor);
        if(consumeThreadMin != null){
            pushConsumer.setConsumeThreadMin(consumeThreadMin);
        }
        if(consumeThreadMax != null){
            pushConsumer.setConsumeThreadMax(consumeThreadMax);
        }
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置消费模型，集群还是广播，默认为集群
        pushConsumer.setMessageModel(MessageModel.BROADCASTING);

        //设置一次消费消息的条数，默认为1条
        if(consumeMessageBatchMaxSize != null){
            pushConsumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        }


        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag,则tag使用*，
             */
            pushConsumer.subscribe(topics, "*");
            pushConsumer.start();
            log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr);
        } catch (Exception e) {
            log.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr,e);
            throw new RocketConfigException(e);
        }

        return pushConsumer;
    }
}
