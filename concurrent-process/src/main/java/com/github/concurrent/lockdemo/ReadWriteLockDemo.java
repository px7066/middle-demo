package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    final ReadWriteLock  rwLock = new ReentrantReadWriteLock();

    static Map<String, String> map = new HashMap();

    public void read(){
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"读开始...");
        System.out.println(Thread.currentThread().getName()+"读数据为："+map.get("data"));
        System.out.println(Thread.currentThread().getName()+"读结束...");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            rwLock.readLock().unlock();
//        }
        rwLock.readLock().unlock();
    }

    public void write(){
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"写开始...");
        System.out.println(Thread.currentThread().getName()+"写数据为："+map.put("data", "writelock"));
        System.out.println(Thread.currentThread().getName()+"写结束...");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            rwLock.writeLock().unlock();
//        }
        rwLock.writeLock().unlock();
    }

    public void write1(){
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"写开始...");
        System.out.println(Thread.currentThread().getName()+"写数据为："+map.put("data", "writelock1"));
        System.out.println(Thread.currentThread().getName()+"写结束...");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            rwLock.writeLock().unlock();
//        }
        rwLock.writeLock().unlock();
    }

    /**
     * 如果只有读锁，两个线程同时执行
     * 如果先有线程获取读锁，再有线程就无法获取到写锁，要等读锁释放
     * 对于线程本身第二次写与读顺序是无序的
     * 对于线程池线程的执行时调用的顺序也是不保障的
     */
    public static void main(String[] args) throws InterruptedException {
        map.put("data", "readlock");
        ReadWriteLockDemo demo = new ReadWriteLockDemo();
//        Thread readThread1 = new Thread(safe::read);
//        Thread readThread2 = new Thread(safe::read);
//        Thread writeThread1 = new Thread(safe::write);
//        Thread writeThread2 = new Thread(safe::write1);
//
//        readThread1.start();
//        readThread1.join();
//        writeThread2.start();
//        writeThread2.join();
//        readThread2.start();
//        readThread2.join();
//        writeThread1.start();
//        writeThread1.join();

        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(4, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        executorService.execute(demo::read);
        executorService.execute(demo::write);
        executorService.execute(demo::write1);
        executorService.execute(demo::read);
        executorService.shutdown();
    }


}
