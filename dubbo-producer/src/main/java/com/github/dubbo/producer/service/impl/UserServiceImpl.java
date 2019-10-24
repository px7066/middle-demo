package com.github.dubbo.producer.service.impl;

import com.github.dubbo.producer.dao.IUserDao;
import com.github.dubbo.producer.domain.UserPo;
import com.github.dubbo.producer.model.UserVo;
import com.github.dubbo.producer.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>UserServiceImpl</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao iUserDao;

    @Override
    public UserVo queryUserById(Integer id) {
        UserPo userPo = iUserDao.selectUserById(id);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(userPo, vo);
        return vo;
    }
}
