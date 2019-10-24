package com.github.remote.model.dubbo.producer;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>样例用户</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
public class UserRmoteDto implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * age
     */
    private Integer age;


}
