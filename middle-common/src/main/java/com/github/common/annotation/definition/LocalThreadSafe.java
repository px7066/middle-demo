package com.github.common.annotation.definition;

import java.lang.annotation.*;

/**
 * @description: 标识着类或方法是本地线程安全的（集群环境不安全）
 * @author: <a href="mailto:7066450@qq.com">panxi</a>
 * @version: 1.0.0
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface LocalThreadSafe {
}
