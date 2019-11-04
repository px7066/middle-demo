package com.github.concurrent.thread;

import com.github.common.annotation.definition.LocalThreadSafe;
import lombok.Data;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * <p>日志服务，使用阻塞队列</p>
 * <p>添加shutdown标志位和计数器，如果发生中断，只要当标志位为真且计数器为0线程才真正停止</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@LocalThreadSafe
@Data
public class LogService {
    private final BlockingQueue<String> queues;

    private final LoggerThread loggerThread;

    private final PrintWriter writer;

    private boolean isShutDown;

    private int reservations;

    public void stop(){
        synchronized (this){
            isShutDown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException{
        synchronized (this){
            if(isShutDown){
                throw new IllegalStateException();
            }
            ++reservations;
            queues.put(msg);
        }
    }



    private class LoggerThread extends Thread{
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
            try {
                while (true){
                    try {
                        synchronized (LogService.this){
                            if(isShutDown && reservations ==0){
                                break;
                            }
                            String msg = queues.take();
                            synchronized (LogService.this){
                                --reservations;
                            }
                            writer.println(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}

