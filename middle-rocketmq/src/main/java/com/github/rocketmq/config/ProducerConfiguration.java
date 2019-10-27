package com.github.rocketmq.config;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.github.common.exception.RocketConfigException;
import com.github.rocketmq.listen.ConsumerMsgListenerProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * <p>生产者配置</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
@Data
@Slf4j
public class ProducerConfiguration {

    private String groupName;

    private String namesrvAddr;

    private Integer maxMessageSize;

    private Integer sendMsgTimeout;

    private Integer retryTimesWhenSendFailed;

    @Bean
    @ConditionalOnClass(ConsumerMsgListenerProcessor.class)
    public DefaultMQProducer getRocketMQProducer() throws RocketConfigException {
        if(StringUtils.isEmpty(groupName)){
            throw new RocketConfigException("groupName is empty");
        }
        if(StringUtils.isEmpty(namesrvAddr)){
            throw new RocketConfigException("namesrvAddr is empty");
        }
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setCreateTopicKey("TOPIC_KEY_TEST");
        if(this.maxMessageSize != null){
            producer.setMaxMessageSize(maxMessageSize);
        }
        if(this.sendMsgTimeout != null){
            producer.setSendMsgTimeout(sendMsgTimeout);
        }
        if(this.retryTimesWhenSendFailed != null){
            producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        }
        try {
            producer.start();
            log.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , groupName, namesrvAddr));
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}"
                    , e.getMessage(),e));
            throw new RocketConfigException(e);
        }
        return producer;
    }

}
