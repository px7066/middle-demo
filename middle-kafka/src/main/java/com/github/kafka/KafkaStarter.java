package com.github.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@SpringBootApplication
@EnableKafka
public class KafkaStarter {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStarter.class, args);
    }
}
