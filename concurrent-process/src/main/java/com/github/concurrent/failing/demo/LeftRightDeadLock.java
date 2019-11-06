package com.github.concurrent.failing.demo;

/**
 * <p>简单顺序死锁</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight(){
        synchronized (left){
            synchronized (right){
                System.out.println("正常执行");
            }
        }
    }

    public void rightLeft(){
        synchronized (right){
            synchronized (left){
                System.out.println("正常执行");
            }
        }
    }

    public static void main(String[] args) {
        final LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        while (true){
            Thread thread1 = new Thread(() -> {
                leftRightDeadLock.leftRight();
            });
            Thread thread2 = new Thread(() -> {
                leftRightDeadLock.rightLeft();
            });
            thread1.start();
            thread2.start();
        }
    }


}
