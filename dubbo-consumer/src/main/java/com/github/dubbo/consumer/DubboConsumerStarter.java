package com.github.dubbo.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@SpringBootApplication
@EnableDubbo
@DubboComponentScan(basePackages = "com.github.dubbo")
public class DubboConsumerStarter {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerStarter.class, args);
    }

}
