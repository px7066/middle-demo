package com.github.rocketmq.listen;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>监听器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
@ConfigurationProperties("listen")
@Data
@Slf4j
public class ConsumerMsgListenerProcessor implements MessageListenerConcurrently {
    private String topic;

    private String tags;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if(CollectionUtils.isEmpty(list)){
            log.info("接收到的消息为空，不做任何处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        log.info("接收到的消息是："+msg);
        if(messageExt.getTopic().equals(topic)){
            if(messageExt.getTags().equals(tags)){
                int reconsumeTimes = messageExt.getReconsumeTimes();
                if(reconsumeTimes == 3){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                log.info("监听回调方法调用");
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
