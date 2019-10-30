package com.github.rocketmq.controller;

import com.github.rocketmq.producer.ProducerSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>控制器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@RestController
@RequestMapping("rocketmq")
public class RocketmqController {
    @Autowired
    private ProducerSender producerSender;
    @GetMapping("send")
    public void send(){
        try {
            producerSender.run(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
