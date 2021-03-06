package com.github.dubbo.producer.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>用户返回</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
public class UserVo implements Serializable {

    private Integer id;

    private String name;

    private Integer age;
}
