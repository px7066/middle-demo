package com.github.kafka.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>消费监听器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class ConsumerListener {
    /**
     * 当主题不存在时会报错 Topic(s) test_send_message  is/are not present and missingTopicsFatal is true
     * 修改为false
     */
    @KafkaListener(topics = "test_send_message")
    public void onMessage(List<ConsumerRecord<?, ?>> records){
        System.out.println(JSON.toJSONString(records));
    }
}
