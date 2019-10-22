package com.github.middlemongo.service;

import com.github.middlemongo.model.UserPO;

/**
 * service
 */
public interface IMongoTestService {

    void insert();

    Integer query();

    void insertUser(UserPO po);
}
