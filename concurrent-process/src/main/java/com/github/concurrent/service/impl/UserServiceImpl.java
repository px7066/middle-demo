package com.github.concurrent.service.impl;

import com.github.common.annotation.definition.LocalThreadSafe;
import com.github.common.util.Md5Util;
import com.github.concurrent.dao.generator.IndexMapper;
import com.github.concurrent.dao.generator.UserMapper;
import com.github.concurrent.model.generator.Index;
import com.github.concurrent.model.generator.User;
import com.github.concurrent.model.generator.UserExample;
import com.github.concurrent.model.vo.UserVo;
import com.github.concurrent.service.ITransationdDemoService;
import com.github.concurrent.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;
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
    private UserMapper userMapper;

    @Autowired
    private IndexMapper indexMapper;

    private AtomicLong visitNum = new AtomicLong(0);

    @Autowired
    private ITransationdDemoService transationdDemoService;


    @Override
    public UserVo queryUserById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public UserVo queryUserByName(String name) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
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
        UserVo selectUser = this.queryUserByName(vo.getUserName());
        if(null != selectUser){
            return false;
        }
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        int id = userMapper.insert(user);
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

    @Override
    public void testTransactionRollback() throws Exception {
        User user = new User();
        user.setCreateTime(new Date());
        user.setDeleteFlag(false);
        user.setUserAge((byte) 20);
        user.setUserName("李四" + System.currentTimeMillis());
        user.setVersion(1);
        userMapper.insert(user);
        transationdDemoService.testTransactionRollbackC();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void testTransactionRollbackA() throws Exception {
        Index index = new Index();
        index.setIndexA(Md5Util.encodeByMD5("indexA" + System.currentTimeMillis()));
        index.setCreateTime(new Date());
        index.setIndexB(Md5Util.encodeByMD5("indexB" + System.currentTimeMillis()));
        Random random = new Random();
        index.setIndexC((byte) (random.nextInt() % 4));
        indexMapper.insert(index);
        throw new Exception();
    }

    @Override
    public void testTransactionRollbackB() throws Exception {
        throw new Exception();
    }


}
