package com.github.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @description: 取消接口
 * @author: <a href="mailto:7066450@qq.com">panxi</a>
 * @version: 1.0.0
 * @since 1.0
 */
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
