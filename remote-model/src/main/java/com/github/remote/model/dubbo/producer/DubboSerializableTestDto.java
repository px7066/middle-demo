package com.github.remote.model.dubbo.producer;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>dubbo序列化过程查看</p>
 * <p>结果一如果生产者添加某个属性，消费者并不会报错，但是那个属性是不存在的</p>
 * <p>结果二如果生产者删除某个属性，消费者并不会报错，但是那个属性为空</p>
 * <p>结果三如果消费者删除某个属性，消费者并不会报错，但是那个属性是不存在的</p>
 * <p>结果四如果消费者添加某个属性，消费者并不会报错，但是那个属性为空</p>
 * <p>结论，添加或删除属性值不会报错（dubbo版本2.6.2）</p>
 * <p>结果五，如果删除Serializable实现，出现IllegalStateException的序列化错误</p>
 * <p>dubbo协议默认使用Hession2的Serializer来实现序列化，通过重写writeObject()将用反射过程交给其他方法调用</p>
 *
 * @author <a href="7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
public class DubboSerializableTestDto implements Serializable {
    private String name;

    private Integer age;

}
