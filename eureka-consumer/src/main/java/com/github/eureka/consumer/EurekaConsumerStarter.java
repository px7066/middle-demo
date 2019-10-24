package com.github.eureka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaConsumerStarter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerStarter.class, args);
    }
}
