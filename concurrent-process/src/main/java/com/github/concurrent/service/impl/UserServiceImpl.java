package com.github.concurrent.service.impl;

import com.github.common.annotation.definition.LocalThreadSafe;
import com.github.concurrent.dao.user.DubboUserMapper;
import com.github.concurrent.model.user.DubboUser;
import com.github.concurrent.model.user.DubboUserExample;
import com.github.concurrent.model.vo.UserVo;
import com.github.concurrent.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    private AtomicLong visitNum = new AtomicLong(0);


    @Override
    public UserVo queryUserById(Integer userId) {
        DubboUser dubboUser = dubboUserMapper.selectByPrimaryKey(userId);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(dubboUser, vo);
        return vo;
    }

    @Override
    public UserVo queryUserByName(String name) {
        DubboUserExample example = new DubboUserExample();
        DubboUserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<DubboUser> users = dubboUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(users.get(0), vo);
        return vo;
    }

    /**
     * 线程安全的方法，但是多线程跑成了单线程
     * Check-Then-Act
     * @param vo 用户
     */
    @Override
    @LocalThreadSafe
    public synchronized boolean saveUser(UserVo vo) {
        UserVo selectUser = this.queryUserByName(vo.getName());
        if(null != selectUser){
            return false;
        }
        DubboUser user = new DubboUser();
        BeanUtils.copyProperties(vo, user);
        int id =dubboUserMapper.insert(user);
        return id > 0;
    }


    /**
     * long var6;
     * do {
     *      var6 = this.getLongVolatile(var1, var2);
     * } while(!this.compareAndSwapLong(var1, var2, var6, var6 + var4));
     * CAS自旋新增
     */
    @Override
    @LocalThreadSafe
    public Long queryVisitNum() {
        return visitNum.incrementAndGet();
    }


}
