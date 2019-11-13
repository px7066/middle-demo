package com.github.concurrent.demo;


import com.github.concurrent.model.safe.LRUCacheLinkedList;

/**
 * <p>LRU测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class LRUCacheDemo {
    public static void main(String[] args) {
        LRUCacheLinkedList lruCacheLinkedList = new LRUCacheLinkedList(2L);
        lruCacheLinkedList.putCache("test1", "as");
        lruCacheLinkedList.putCache("test2", "as");
        lruCacheLinkedList.putCache("test3", "as");
        System.out.println(lruCacheLinkedList.getCache("test1"));
        System.out.println(lruCacheLinkedList.getCache("test2"));
        System.out.println(lruCacheLinkedList.getCache("test3"));
    }
}
