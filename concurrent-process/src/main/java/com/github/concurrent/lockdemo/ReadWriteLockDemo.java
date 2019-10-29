package com.github.concurrent.lockdemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    final ReadWriteLock  rwLock = new ReentrantReadWriteLock();

    Map map = new HashMap();

    public void read(){
        map.put("data", "readlock");
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"读开始...");
        System.out.println(Thread.currentThread().getName()+"读数据为："+map.get("data"));
        System.out.println(Thread.currentThread().getName()+"读结束...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Thread thread1 = new Thread(demo::read);
        Thread thread2 = new Thread(demo::read);
        thread1.start();
        thread2.start();
    }


}
