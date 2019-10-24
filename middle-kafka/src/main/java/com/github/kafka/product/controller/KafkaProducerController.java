package com.github.kafka.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>生产者产生消息</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@RestController
public class KafkaProducerController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("/message/send")
    public boolean send(String message){
        kafkaTemplate.send("test_send_message","message" ,message);
//        kafkaTemplate.send("test_send_message",1,"messagess" ,message +"as");
        return true;
    }
}
