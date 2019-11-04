package com.github.concurrent.thread.demo;

/**
 * <p>给出CPU的个数</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
public class CpuNumberDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
