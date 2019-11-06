package com.github.redis.service.impl;

import com.github.common.annotation.aop.ResubmitLock;
import com.github.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void add() {
        stringRedisTemplate.opsForValue().set("redis-test", "Hello");
    }

    @Override
    @ResubmitLock(prefix = "query", timeout = 5000)
    public String query() {
        return stringRedisTemplate.opsForValue().get("redis-test");
    }
}
