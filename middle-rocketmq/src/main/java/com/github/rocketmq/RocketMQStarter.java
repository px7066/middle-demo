package com.github.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@SpringBootApplication
public class RocketMQStarter {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQStarter.class, args);
    }
}
