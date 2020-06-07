package com.github.poi.annotation;


import java.lang.annotation.*;

/**
 * <p>日期转换</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/29
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelDateFormat {
    String format() default "yyyy-MM-dd";


}
