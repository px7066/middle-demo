package com.github.concurrent.service.impl;

import com.github.concurrent.dao.user.DubboUserMapper;
import com.github.concurrent.model.user.DubboUser;
import com.github.concurrent.model.vo.UserVo;
import com.github.concurrent.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>用户用于并发测试</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private DubboUserMapper dubboUserMapper;


    @Override
    public UserVo queryUser(Integer userId) {
        DubboUser dubboUser = dubboUserMapper.selectByPrimaryKey(userId);
        UserVo vo = new UserVo();
        if(null == dubboUser){
            return vo;
        }
        BeanUtils.copyProperties(dubboUser, vo);
        return vo;
    }
}
