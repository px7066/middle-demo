package com.github.concurrent.failing.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>递归虚拟机栈溢出测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class JavaVMStackSOF {

    /**
     * 递归导致stack溢出
     */
    public static void recusive(){
        recusive();
    }


    /**
     * head 溢出
     */
    public static void recursive(int count){
        Thread thread = new Thread(() -> {
            List<String> list = new ArrayList<>();
            while (true){
                list.add("哈哈");
            }
        });
        if(count == 0){
            return;
        }
        final Integer[] num = {count--};
        new Thread(() -> {
            recursive(num[0]--);
        });
        thread.start();
    }

    public static void main(String[] args) {
        JavaVMStackSOF.recursive(10);
    }

}
