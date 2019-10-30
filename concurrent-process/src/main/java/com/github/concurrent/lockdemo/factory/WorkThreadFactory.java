package com.github.concurrent.lockdemo.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>工作线程工厂</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class WorkThreadFactory implements ThreadFactory {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        int c = atomicInteger.incrementAndGet();
        System.out.println("create work-thread-" + c);
        return new Thread(r, "work-thread-" + c);
    }
}
