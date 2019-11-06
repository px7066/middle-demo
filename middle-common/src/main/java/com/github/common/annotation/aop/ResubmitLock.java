package com.github.common.annotation.aop;

import java.lang.annotation.*;

/**
 * <p>防止重复提交</p>
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResubmitLock {
    long timeout() default 10L;

    String prefix();
}
