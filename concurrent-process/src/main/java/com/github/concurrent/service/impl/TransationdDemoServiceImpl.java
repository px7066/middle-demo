package com.github.concurrent.service.impl;

import com.github.common.util.Md5Util;
import com.github.concurrent.dao.generator.IndexMapper;
import com.github.concurrent.model.generator.Index;
import com.github.concurrent.service.ITransationdDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
public class TransationdDemoServiceImpl implements ITransationdDemoService {

    @Autowired
    private IndexMapper indexMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransactionRollbackC() throws Exception {
        Index index = new Index();
        index.setIndexA(Md5Util.encodeByMD5("indexA" + System.currentTimeMillis()));
        index.setCreateTime(new Date());
        index.setIndexB(Md5Util.encodeByMD5("indexB" + System.currentTimeMillis()));
        Random random = new Random();
        index.setIndexC((byte) (random.nextInt() % 4));
        indexMapper.insert(index);
        throw new Exception();
    }
}
