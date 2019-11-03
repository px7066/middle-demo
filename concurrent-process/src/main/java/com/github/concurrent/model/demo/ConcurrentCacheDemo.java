package com.github.concurrent.model.demo;

import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>高效缓存样例(Future首先判断某个key的计算是否开始了，没开始则等待)</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class ConcurrentCacheDemo {
    private final Map<String, Future<String>> cache = new ConcurrentHashMap<>();

    public String compute(String key) throws InterruptedException{
        while (true){
            Future<String> future = cache.get(key);
            if(null == future){
                Callable<String> eval = new Callable<String>() {
                    @Override
                    public String call() throws InterruptedException{
                        //休眠一段时间为了让结果更直观,输出一个结果看看是否只执行一次
                        System.out.println("计算开始执行");
                        Thread.sleep(3000);
                        return "高效缓存";
                    }
                };
                FutureTask<String> futureTask = new FutureTask<>(eval);
                future = futureTask;
                future = cache.putIfAbsent(key, future);
                if(null == future){
                    future = futureTask;
                    futureTask.run();
                };
            }
            try {
                return future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw launderThrowable(e.getCause());
            }
        }
    }

    /**
     * 强制将throwable转换成Exception
      * @param e Throwable
     * @return java.lang.RuntimeException
     * @see com.github.concurrent.model.demo.ConcurrentCacheDemo#launderThrowable(Throwable)
     */
    public static RuntimeException launderThrowable(Throwable e){
        if(e instanceof RuntimeException){
            return (RuntimeException) e;
        }else if(e instanceof Error){
            throw (Error) e;
        }else{
            throw new IllegalStateException();
        }
    }


    public static void main(String[] args) {
        ConcurrentCacheDemo concurrentCacheDemo = new ConcurrentCacheDemo();
        final Runnable runnable = () -> {
            System.out.println("计算开始" + Thread.currentThread().getId());
            try {
                String key = concurrentCacheDemo.compute("test");
                System.out.println(key);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

}
