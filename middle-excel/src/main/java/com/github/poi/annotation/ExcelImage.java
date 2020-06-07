package com.github.poi.annotation;


import com.github.poi.enums.ImageType;

import java.lang.annotation.*;

/**
 * <p>excel图片导出</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/14 8:40
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelImage {
    ImageType type();

    float height() default 40L;
}
