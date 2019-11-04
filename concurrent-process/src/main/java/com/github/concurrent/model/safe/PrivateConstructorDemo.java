package com.github.concurrent.model.safe;

import com.github.common.annotation.definition.LocalThreadSafe;
import lombok.Data;

/**
 * <p>私有构造函数捕获模式（Private Constructor Capture Bloch and Gafter）</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
@LocalThreadSafe
public class PrivateConstructorDemo {
    private int x,y;

    private PrivateConstructorDemo(int[] a){
        this(a[0], a[2]);
    }

    public PrivateConstructorDemo(PrivateConstructorDemo privateConstructorDemo){
        this(privateConstructorDemo.get());
    }

    public PrivateConstructorDemo(int x, int y){
        this.x= x;
        this.y = y;
    }

    public synchronized int[] get(){
        return new int[] {x, y};
    }

    public synchronized void set(int x, int y){
        this.x = x;
        this.y = y;
    }
}
