package com.github.middlemongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * <p>测试用户持久化对象</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Data
@Document(collection = "middleware_mongo_user")
public class UserPO {
    @Id
    private String id;

    private String name;

    private String phone;

    private List<String> favorites;

}
