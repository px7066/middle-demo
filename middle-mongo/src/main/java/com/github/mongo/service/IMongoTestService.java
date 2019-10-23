package com.github.mongo.service;

import com.github.mongo.model.UserPO;

/**
 * service
 */
public interface IMongoTestService {

    void insert();

    Integer query();

    void insertUser(UserPO po);
}
