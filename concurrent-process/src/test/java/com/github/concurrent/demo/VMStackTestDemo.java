package com.github.concurrent.demo;

/**
 * <p>java栈测试类</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class VMStackTestDemo {

    public static void test(){
        byte[] bytes = new byte[1024*1024*1024];
        for (int i=0; i< bytes.length; i++){
            bytes[i] = (byte) i;
        }
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }

    public static void main(String[] args) {
        new Thread(VMStackTestDemo::test).start();
    }
}
