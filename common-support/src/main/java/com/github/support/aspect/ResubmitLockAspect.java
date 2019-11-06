package com.github.support.aspect;

import com.alibaba.fastjson.JSON;
import com.github.common.annotation.aop.ResubmitLock;
import com.github.common.exception.ResubmitException;
import com.github.common.util.Md5Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class ResubmitLockAspect {

    @Autowired(required = false)
    public ResubmitLockAspect(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate redisTemplate;



    @Around("execution(public * * (..)) && @annotation(com.github.common.annotation.aop.ResubmitLock)")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ResubmitLock resubmitLock = method.getAnnotation(ResubmitLock.class);
        long timeout = resubmitLock.timeout();
        String prefix = resubmitLock.prefix();
        Object[] args = point.getArgs();
        String key = Md5Util.encodeByMD5(JSON.toJSONString(args));
        final boolean locked = redisTemplate.opsForValue().setIfAbsent(prefix + key, System.nanoTime(), timeout, TimeUnit.MILLISECONDS);
        if(locked){
            return point.proceed();
        }else{
            throw new ResubmitException();
        }
    }



}
