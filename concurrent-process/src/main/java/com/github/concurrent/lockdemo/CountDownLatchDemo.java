package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.concurrent.*;

/**
 * <p>维护一个计数器，阻塞线程，等待计数器为0时，执行</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println("主线程执行阻塞");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),new WorkThreadFactory());
        executorService.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        });
        executorService.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子任务执行结束");
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程继续执行任务");

    }
}
