package com.github.dubbo.producer.service;

import com.github.dubbo.producer.model.UserVo;

/**
 * <p>IUserService</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public interface IUserService {
    UserVo queryUserById(Integer id);
}
