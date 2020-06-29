package com.github.concurrent.thread.demo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10, 然后是线程3打印11,12,13,14,15.
 *      接着再由线程1打印16,17,18,19,20….以此类推, 直到打印到75。</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/6/29 8:53
 */
public class Demo {

    public static final AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new DemoThreadFactory());
        threadPoolExecutor.execute(new PrintThread(0));
        threadPoolExecutor.execute(new PrintThread(1));
        threadPoolExecutor.execute(new PrintThread(2));
    }

    public static class PrintThread implements Runnable{

        public PrintThread(Integer threadNum) {
            this.threadNum = threadNum;
        }

        private Integer threadNum;

        @Override
        public void run() {
            try {
                synchronized (num){
                    while (num.get() <15){
                        int count = num.getAndIncrement();
                        if(count %3 == threadNum){
                            System.out.println(Thread.currentThread().getName());
                            for(int i=count*5+1; i<count*5+6; i++){
                                System.out.println(i);
                            }
                            num.notifyAll();
                        }else {
                            num.decrementAndGet();
                            num.wait();
                        }
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public static class DemoThreadFactory implements ThreadFactory {

        private AtomicInteger number = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int n = number.getAndIncrement();
            Thread thread = new Thread(r, "Demo-thread-" + n);
            return thread;
        }
    }
}
