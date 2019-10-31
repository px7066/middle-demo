package com.github.concurrent;

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
@MapperScan(basePackages = "com.github.concurrent.dao")
public class ConcurrentStarter {
    public static void main(String[] args) {
        SpringApplication.run(ConcurrentStarter.class, args);
    }
}
