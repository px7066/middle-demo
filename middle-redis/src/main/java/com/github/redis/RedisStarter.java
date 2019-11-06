package com.github.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动器
 */
@SpringBootApplication(scanBasePackages = "com.github")
@EnableCaching
public class RedisStarter {
    public static void main(String[] args) {
        SpringApplication.run(RedisStarter.class, args);
    }
}
