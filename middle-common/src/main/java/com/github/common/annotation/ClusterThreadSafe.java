package com.github.common.annotation;

import java.lang.annotation.*;

/**
 * <p>标识类或方法集群环境安全</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@LocalThreadSafe
public @interface ClusterThreadSafe {

}
