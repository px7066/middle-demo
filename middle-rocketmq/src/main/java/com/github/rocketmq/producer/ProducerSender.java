package com.github.rocketmq.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>发送消息</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class ProducerSender {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void run(String... args) throws Exception {
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new com.github.rocketmq.model.OrderPaidEvent("T_001", new BigDecimal("88.00")));
        rocketMQTemplate.convertAndSend("test-topic-1", "message trace");

//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

    /**
     * 存在一个bug lombok和jackjson冲突
     * Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.github.rocketmq.producer.ProducerSender$OrderPaidEvent` (although at least one Creator exists): can only instantiate non-static inner class by using default, no-argument constructor
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderPaidEvent implements Serializable {
        private String orderId;
        private BigDecimal paidMoney;
    }
}
