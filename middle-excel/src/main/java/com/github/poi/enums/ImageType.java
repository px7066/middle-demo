package com.github.poi.enums;

import lombok.Getter;

/**
 * <p></p>
 *
 * @author <a href="mailto:xipan@bigvisiontech.com">panxi</a>
 * @version 1.0.0
 * @date 2020/5/14 8:58
 * @since 1.0
 */
@Getter
public enum ImageType {
    URL(String.class), BYTE(byte[].class), PATH(String.class), NONE(Void.class);


    private Class aClass;

    ImageType(Class aClass) {
        this.aClass = aClass;
    }
}
