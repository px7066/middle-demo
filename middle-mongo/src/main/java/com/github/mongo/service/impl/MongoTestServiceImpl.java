package com.github.mongo.service.impl;

import com.github.mongo.model.UserPO;
import com.github.mongo.model.dto.MongoTemplateGroupSubTestDTO;
import com.github.mongo.model.dto.MongoTemplateGroupTestDTO;
import com.github.mongo.service.IMongoTestService;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class MongoTestServiceImpl implements IMongoTestService {
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 新增
     */
    @Override
    public void insert() {
        MongoTemplateGroupTestDTO mongoTemplateGroupTestDTO = new MongoTemplateGroupTestDTO();
        List<MongoTemplateGroupSubTestDTO> subTestDTOS = new ArrayList<>();
        MongoTemplateGroupSubTestDTO mongoTemplateGroupSubTestDTO = new MongoTemplateGroupSubTestDTO();
        mongoTemplateGroupSubTestDTO.setEnd(1001);
        mongoTemplateGroupSubTestDTO.setStart(1020);
        subTestDTOS.add(mongoTemplateGroupSubTestDTO);
        MongoTemplateGroupSubTestDTO mongoTemplateGroupSubTestDTO1 = new MongoTemplateGroupSubTestDTO();
        mongoTemplateGroupSubTestDTO1.setEnd(2001);
        mongoTemplateGroupSubTestDTO1.setStart(2020);
        subTestDTOS.add(mongoTemplateGroupSubTestDTO1);
        MongoTemplateGroupSubTestDTO mongoTemplateGroupSubTestDTO2 = new MongoTemplateGroupSubTestDTO();
        mongoTemplateGroupSubTestDTO2.setEnd(2001);
        mongoTemplateGroupSubTestDTO2.setStart(2020);
        subTestDTOS.add(mongoTemplateGroupSubTestDTO1);
        mongoTemplateGroupTestDTO.setCode("RT-1002");
        mongoTemplateGroupTestDTO.setSubs(subTestDTOS);
        mongoTemplate.insert(mongoTemplateGroupTestDTO);
    }

    /**
     * 演示管道操作
     * db.getCollection('test').aggregate([
     *     {'$match':{'code':'RT-1002'}},
     *     {'$unwind':'$subs'},
     *     {'$group':{_id:'$subs.end','count':{'$sum':{'$subtract':['$subs.start','$subs.end']}}}}
     * ])
     * 结果累加
     */
    @Override
    public Integer query() {
        List<Document> dbObjects = new ArrayList<>();
        Document match = new Document();
        Document matchs = new Document();
        matchs.append("code","RT-1002");
        match.append("$match", matchs);
        Document unWind = new Document("$unwind", "$subs");
        Document group = new Document();
        Document groupVal = new Document();
        groupVal.append("_id", "subs.start");
        Document sub = new Document("$subtract", Arrays.asList("$subs.start", "$subs.end"));
        groupVal.append("sum", new Document("$sum", sub));
        group.append("$group", groupVal);
        dbObjects.add(match);
        dbObjects.add(unWind);
        dbObjects.add(group);
        AggregateIterable<Document> re = mongoTemplate.getCollection("test")
                .aggregate(dbObjects);
        BigDecimal sum = BigDecimal.ZERO;
        for (Iterator<Document> it = re.iterator(); it.hasNext();) {
            Document dbo = it.next();
            Integer sumTemp = dbo.getInteger("sum");
            sum = sum.add(BigDecimal.valueOf(sumTemp));
        }
        return sum.intValue();
    }

    @Override
    public void insertUser(UserPO po) {
        mongoTemplate.insert(po);
    }


}
