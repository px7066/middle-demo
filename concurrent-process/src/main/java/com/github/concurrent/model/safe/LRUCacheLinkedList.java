package com.github.concurrent.model.safe;

import com.github.common.annotation.definition.LocalThreadSafe;
import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * <p>LRU缓存实现</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@LocalThreadSafe
public class LRUCacheLinkedList {
    private final LinkedList<Node> cache;

    private final long capacity;

    public LRUCacheLinkedList() {
        this.cache = new LinkedList<>();
        this.capacity = 1000;
    }

    public LRUCacheLinkedList(LinkedList<Node> cache) {
        this.cache = cache;
        this.capacity = 1000;
    }

    public LRUCacheLinkedList(Long capacity) {
        this.capacity = capacity;
        this.cache = new LinkedList<>();
    }

    public LRUCacheLinkedList(LinkedList<Node> cache, Long capacity) {
        this.cache = cache;
        this.capacity = capacity;
    }

    public Object getCache(String k){
        if(k == null){
            return null;
        }
        synchronized (cache){
            Iterator<Node> iterator = cache.iterator();
            while (iterator.hasNext()){
                Node node = iterator.next();
                if(k.equals(node.getKey())){
                    putCache(node);
                    return node.getObj();
                }
            }
        }
        return null;
    }

    public Node putCache(String key, Object obj) {
        if(key == null){
            return null;
        }
        Node node = new Node(key, obj);
        synchronized (cache){
            Iterator<Node> iterator = cache.iterator();
            while (iterator.hasNext()){
                Node next = iterator.next();
                if(key.equals(next.getKey())){
                    iterator.remove();
                    break;
                }
            }
            if(cache.size() == capacity){
                cache.removeLast();
            }
            cache.addFirst(node);
        }
        return node;

    }

    public Node putCache(Node node) {
        if(node == null || node.getKey() == null){
            return null;
        }
        synchronized (cache){
            Iterator<Node> iterator = cache.iterator();
            while (iterator.hasNext()){
                Node next = iterator.next();
                if(node.getKey().equals(next.getKey())){
                    iterator.remove();
                    break;
                }
            }
            if(cache.size() == capacity){
                cache.removeLast();
            }
            cache.addFirst(node);
        }
        return node;

    }




    @Data
    final class Node{
        private final String key;
        private final Object obj;

        public Node(String key, Object obj) {
            this.key = key;
            this.obj = obj;
        }
    }
}
