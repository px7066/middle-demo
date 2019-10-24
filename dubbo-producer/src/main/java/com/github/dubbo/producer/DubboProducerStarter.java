package com.github.dubbo.producer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.github.dubbo.producer.dao")
@EnableDubbo
public class DubboProducerStarter {
    public static void main(String[] args) {
        SpringApplication.run(DubboProducerStarter.class, args);
    }

}
