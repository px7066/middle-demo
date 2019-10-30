package com.github.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * <p>message trace 测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Service
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "test1", enableMsgTrace = true, customizedTraceTopic = "my-trace-topic")
@Slf4j
public class ConsumerListenerTraceTest implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info(s);
    }
}
