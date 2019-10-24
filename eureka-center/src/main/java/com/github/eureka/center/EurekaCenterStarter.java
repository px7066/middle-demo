package com.github.eureka.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaCenterStarter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCenterStarter.class, args);
    }
}
