package com.github.common.annotation;

import com.github.common.annotation.definition.LocalThreadSafe;

import java.lang.annotation.*;

/**
 * <p>标识类为不变对象</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@LocalThreadSafe
public @interface Immutable {

}
