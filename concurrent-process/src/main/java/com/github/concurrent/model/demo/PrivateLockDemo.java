package com.github.concurrent.model.demo;

import com.github.common.annotation.definition.LocalThreadSafe;
import lombok.Data;

/**
 * <p>私有锁保护状态样例</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
@LocalThreadSafe
public class PrivateLockDemo {
    private final Object myLock = new Object();

    private Integer state;

    void updateState(){
        synchronized (myLock){
            state = 1;
        }
    }


}
