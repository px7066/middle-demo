package com.github.concurrent.demo;

/**
 * <p>-XX:AutoBoxCacheMax=7777</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class IntegerCacheTest {
    public static void main(String[] args) {
        Integer a = 1001;
        Integer b = 1001;
        System.out.println(a == b);
    }
}
