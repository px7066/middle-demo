package com.github.dubbo.producer.dao;

import com.github.dubbo.producer.domain.UserPo;
import org.springframework.stereotype.Repository;

/**
 * USER
 * @author: <a href="mailto:7066450qq.com">panxi</a>
 * @version: 1.0.0
 * @since 1.0
 */
@Repository
public interface IUserDao {
    UserPo selectUserById(Integer id);
}
