package com.github.concurrent.thread.demo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.concurrent.*;

/**
 * <p>未捕获到的异常测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class UncaughtExceptionHandleDemo {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        executorService.execute(() -> {
            throw new RuntimeException("测试");
        });
        executorService.shutdown();
    }
}
