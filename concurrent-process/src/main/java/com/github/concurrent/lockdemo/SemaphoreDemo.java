package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.concurrent.*;

/**
 * <p>信号量Demo</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class SemaphoreDemo {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(10);
        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        executorService.execute(() -> {
            try {
                semaphore.acquire(5);
                System.out.println("线程开始执行");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release(4);
            }
        });
        executorService.execute(() -> {
            try {
                semaphore.acquire(5);
                System.out.println("线程二开始执行");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release(5);
            }
        });
        executorService.execute(() -> {
            try {
                semaphore.acquire(5);
                System.out.println("线程三开始执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release(5);
            }
        });
        executorService.shutdown();
    }
}
