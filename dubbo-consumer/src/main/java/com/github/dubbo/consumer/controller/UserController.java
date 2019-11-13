package com.github.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.remote.api.dubbo.producer.IUserRemoteService;
import com.github.remote.model.dubbo.producer.DubboSerializableTestDto;
import com.github.remote.model.dubbo.producer.UserRmoteDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>用户Controller</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@RestController
public class UserController {
    @Reference
    private IUserRemoteService userRemoteService;

    @GetMapping("queryUserById")
    public UserRmoteDto queryUserById(Integer id, HttpServletRequest request){
        System.out.println(request.getSession().getId());
        return userRemoteService.queryUserById(id);
    }

    @GetMapping("serializatableTestMethod")
    public DubboSerializableTestDto serializatableTestMethod(){
        return userRemoteService.serializatableTestMethod();
    }
}
