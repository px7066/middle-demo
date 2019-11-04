package com.github.concurrent.lockdemo;

import com.github.concurrent.lockdemo.factory.WorkThreadFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>Futrue demo</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class FutureDemo {
    final static FutureTask<Map<String,String>> future = new FutureTask<>(() ->{
        System.out.println("开始初始化");
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap();
        map.put("init", "初始化完成");
        Thread.sleep(1000);
        return map;
    });


    public static void main(String[] args) {
        ThreadFactory threadFactory = new WorkThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
        executorService.submit(future);
        executorService.execute(() ->{
            boolean flag = true;
            while(flag){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(future.isDone() && !future.isCancelled()){
                    Map<String,String> futureResult = null;
                    try {
                        futureResult = future.get();

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.println("===初始化完成了："+futureResult.get("init"));
                    flag = false;
                }else{
                    System.out.println("====还在初始化");
                }
            }

        });

    }


}
