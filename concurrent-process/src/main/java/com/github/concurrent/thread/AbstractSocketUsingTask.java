package com.github.concurrent.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * <p>Socket取消样例</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public abstract class AbstractSocketUsingTask<T> implements CancellableTask<T>{
    private ServerSocket serverSocket;

    private Socket socket;

    public synchronized void setServerSocket(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public synchronized ServerSocket getServerSocket(){
        return this.serverSocket;
    }

    public synchronized Socket getSocket(){
        if(null != serverSocket){
            try {
                socket = serverSocket.accept();
                return socket;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public synchronized void cancel(){
        try {
            if(null != socket){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning){
                try {
                    AbstractSocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
