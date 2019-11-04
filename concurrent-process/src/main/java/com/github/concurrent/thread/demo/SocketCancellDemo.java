package com.github.concurrent.thread.demo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;
import com.github.concurrent.thread.SocketCancellTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * <p>Socket取消样例（为什么写这个样例，因为I/O是不可中断阻塞，Thread.interrupt()方法无法中断，需要调用Socket.close）</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
public class SocketCancellDemo {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        SocketCancellTask socketCancellTask = new SocketCancellTask();
        socketCancellTask.setServerSocket(ss);
        executorService.submit(socketCancellTask);
        Thread.sleep(5000);
        socketCancellTask.cancel();
        Thread.sleep(5000);
        executorService.shutdown();
    }
}
