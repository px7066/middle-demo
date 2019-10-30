package com.github.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.github.rocketmq.model.OrderPaidEvent;
import com.github.rocketmq.producer.ProducerSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * <p>消费监听测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "test2")
public class ConsumerListenerTest2 implements RocketMQListener<OrderPaidEvent> {
    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        log.info(JSON.toJSONString(orderPaidEvent));
    }
}
