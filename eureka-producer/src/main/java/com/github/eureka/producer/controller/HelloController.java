package com.github.eureka.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>启动器</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "Hello";
    }

}
