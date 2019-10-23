package com.github.kafka.product;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * <p>回调</p>
 *
 * @author <a href="mailto:panxi@zjport.gov.cn">panxi</a>
 * @version $Id$
 * @since 1.0
 */
public class KafkaProducerCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if(e != null){
            e.printStackTrace();
        }else{
            System.out.println("异步发送回调");
        }
    }
}
