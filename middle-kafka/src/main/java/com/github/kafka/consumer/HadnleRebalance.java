package com.github.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>再均衡监听器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class HadnleRebalance implements ConsumerRebalanceListener {
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        System.out.println("onPartitionsRevoked");
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        System.out.println("onPartitionsAssigned");
    }
}
