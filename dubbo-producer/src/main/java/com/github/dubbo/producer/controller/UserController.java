package com.github.dubbo.producer.controller;

import com.github.dubbo.producer.model.UserVo;
import com.github.dubbo.producer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>UserController</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version $Id$
 * @since 1.0
 */
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("queryUserById")
    public UserVo queryUserById(Integer id, HttpServletRequest request){
        System.out.println(request.getSession().getId());
        return userService.queryUserById(id);
    }
}
