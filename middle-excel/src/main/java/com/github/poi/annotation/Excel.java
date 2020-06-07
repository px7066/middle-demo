package com.github.poi.annotation;


import com.github.poi.enums.ExcelType;

import java.lang.annotation.*;

/**
 * <p>excel定义</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/12 14:14
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Excel {
    String name() default "";

    ExcelType type() default ExcelType.HSSF;
}
