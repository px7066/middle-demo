package com.github.concurrent.model.vo;

import lombok.Data;

/**
 * <p>用户</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
public class UserVo implements Cloneable{
    private Integer id;

    private String name;

    private Integer age;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
