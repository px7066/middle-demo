package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.concurrent.*;

/**
 * <p>阻塞线程，等待某一状态一起执行</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, ()->{
            System.out.println("到达集合点开始旅行");
        });

        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);

    }




}
