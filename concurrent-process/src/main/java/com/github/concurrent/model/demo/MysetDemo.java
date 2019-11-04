package com.github.concurrent.model.demo;

import com.github.common.annotation.LocalThreadSafe;
import com.github.concurrent.model.vo.UserVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>演示实例封装</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@LocalThreadSafe
@Data
public class MysetDemo {
    // hashset本身不是线程安全的，但是将它封装到myset进行线程安全的访问(装饰器设计模式)
    private final Set<UserVo> myset = new HashSet<>();

    private synchronized void addUser(UserVo vo){
        if(null != vo){
            myset.add(vo);
        }
    }

    public synchronized UserVo get(String name){
        if(!StringUtils.isEmpty(name)){
            final  UserVo vo = new UserVo();
            myset.forEach((e) -> {
                if(name.equalsIgnoreCase(e.getName())){
                    BeanUtils.copyProperties(e, vo);
                }
            });
            return vo;
        }
        return null;
    }

    public synchronized boolean containsUser(UserVo vo){
        return myset.contains(vo);
    }

}
