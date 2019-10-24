package com.github.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.remote.api.dubbo.producer.IUserRemoteService;
import com.github.remote.model.dubbo.producer.UserRmoteDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>用户Controller</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@RestController
public class UserController {
    @Reference
    private IUserRemoteService userRemoteService;

    @GetMapping("queryUserById")
    public UserRmoteDto queryUserById(Integer id){
        return userRemoteService.queryUserById(id);
    }
}
