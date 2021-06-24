package com.github.kafka.wheel;

import com.github.kafka.http.HttpClientHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestWheelTimer {
    public static void main(String[] args) throws InterruptedException {
        WheelTimer timer = new WheelTimer();
        List<Thread> threads = new ArrayList<>();
        for(int i =0; i< 1000; i++){
            Thread thread = new Thread(() -> {
                for(int j =0;j<100;j++){
                    timer.newTimeout(() -> {
                        try {
                            HttpClientHelper.sendGet("https://crm-dev.bigvisiontech.com/api/v1/version/getNowVersion");
                            System.out.println(Thread.currentThread().getId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, 2*j, TimeUnit.SECONDS);
                }
            });
            threads.add(thread);
        }

        timer.start();
        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }
        System.out.println("任务开始");
    }
}
