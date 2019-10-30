package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

/**
 * <p>读写并发锁解决写饥饿问题</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class StampLockDemo {
    private StampedLock stampedLock = new StampedLock();

    static Map<String, String> map = new HashMap();

    public void read(){
        long s = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName()+"读开始...");
        System.out.println(Thread.currentThread().getName()+"读数据为："+map.get("data"));
        System.out.println(Thread.currentThread().getName()+"读结束...");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            stampedLock.unlock(s);
//        }
        stampedLock.unlock(s);
    }

    public void write(){
        long s = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName()+"写开始...");
        System.out.println(Thread.currentThread().getName()+"写数据为："+map.put("data", "stamplock-write"));
        System.out.println(Thread.currentThread().getName()+"写结束...");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            stampedLock.unlock(s);
//        }
        stampedLock.unlock(s);
    }

    public void write1(){
        long s = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName()+"写开始...");
        System.out.println(Thread.currentThread().getName()+"写数据为："+map.put("data", "stamplock-write1"));
        System.out.println(Thread.currentThread().getName()+"写结束...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlock(s);
        }
//        stampedLock.unlock(s);
    }


    public static void main(String[] args) throws InterruptedException {
        map.put("data", "stamplock-read");
        StampLockDemo demo = new StampLockDemo();

        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        executorService.execute(demo::read);
        executorService.execute(demo::write);
        executorService.execute(demo::write1);
        executorService.execute(demo::read);
        executorService.shutdown();
    }

}
