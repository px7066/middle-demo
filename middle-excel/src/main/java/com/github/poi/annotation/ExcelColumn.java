package com.github.poi.annotation;


import com.github.poi.enums.ImageType;

import java.lang.annotation.*;

/**
 * <p>excel栏</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/12 14:16
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelColumn {
    String name();

    ExcelImage image() default @ExcelImage(type = ImageType.NONE);
}
