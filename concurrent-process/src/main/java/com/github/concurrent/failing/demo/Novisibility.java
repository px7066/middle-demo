package com.github.concurrent.failing.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>可见性demo</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class Novisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            System.out.println(number);
            while (!ready){
                Thread.yield();
                System.out.println(number);
            }
        }
    }

    /**
     * 执行后发现每次最后一个数字都是不同的（0或42），因为主线程写入主存的数据，子线程没第一时间刷新栈内存
     */
    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 42;
        ready = true;
    }
}
