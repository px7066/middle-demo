package com.github.poi.annotation;


import java.lang.annotation.*;

/** 
 * <p>excel导入</p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/28
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelImportColumn {
    int colNum();

    int rowNum() default 0;

    boolean need() default true;

    ExcelDateFormat format() default @ExcelDateFormat;
}
