package com.github.concurrent.service;

import com.github.concurrent.model.vo.UserVo;

/**
 * <p>用户服务</p>
 *
 * @author <a href="mailto:706450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public interface IUserService {

    UserVo queryUser(Integer userId);
}
