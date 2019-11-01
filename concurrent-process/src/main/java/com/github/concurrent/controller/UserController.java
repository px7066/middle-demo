package com.github.concurrent.controller;

import com.github.concurrent.model.vo.UserVo;
import com.github.concurrent.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>用户并发测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("queryUserById")
    public UserVo queryUserById(Integer id){
        return userService.queryUserById(id);
    }

    @PostMapping("saveUser")
    public Boolean saveUser(UserVo vo){
        if(null == vo){
            return false;
        }
        return userService.saveUser(vo);
    }

    @GetMapping("queryVisitNum")
    public Long queryVisitNum(){
        return userService.queryVisitNum();
    }
}
