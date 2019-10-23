package com.github.redis.controller;

import com.github.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private IRedisService redisService;



    @PostMapping("add")
    public void add(){
        redisService.add();
    }

    @GetMapping("query")
    public String query(){
        return redisService.query();
    }

}
