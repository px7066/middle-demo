package com.github.middlemongo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.middlemongo.model.UserPO;
import com.github.middlemongo.model.dto.MongoTemplateGroupSubTestDTO;
import com.github.middlemongo.model.dto.MongoTemplateGroupTestDTO;
import com.github.middlemongo.service.IMongoTestService;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.BsonArray;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        mongoTemplateGroupTestDTO.setCode("RT-1001");
        mongoTemplateGroupTestDTO.setSubs(subTestDTOS);
        mongoTemplate.insert(mongoTemplateGroupTestDTO);
    }

    /**
     * 演示管道操作
     */
    @Override
    public Integer query() {
        List<Document> dbObjects = new ArrayList<>();
        Document match = new Document();
        Document matchs = new Document();
        matchs.append("code","RT-1001");
        match.append("$match", matchs);
        Document group = new Document();
        Document groupVal = new Document();
        groupVal.append("_id", "subs.start");
//        Document sub = new Document("$substract", )

        group.append("$group", groupVal);
        dbObjects.add(match);
        AggregateIterable<Document> re = mongoTemplate.getCollection("test")
                .aggregate(dbObjects);
        List<Document> listDocument = new ArrayList<>();
        for (Iterator<Document> it = re.iterator(); it.hasNext();) {
            Document dbo = it.next();
            listDocument.add(dbo);
        }
        Document document = listDocument.get(0);
        List<Document> str = document.get("subs", List.class);
//        JSONArray jsonArray = JSONArray.parseArray(str);
//        List<MongoTemplateGroupSubTestDTO> list = new ArrayList<>();
//        for(int i=0; i<jsonArray.size(); i++){
//            MongoTemplateGroupSubTestDTO mongoTemplateGroupSubTestDTO = jsonArray.getObject(i, MongoTemplateGroupSubTestDTO.class);
//            list.add(mongoTemplateGroupSubTestDTO);
//        }
//
//        System.out.println(JSON.toJSON(document));

        return str.get(0).getInteger("start");
    }

    @Override
    public void insertUser(UserPO po) {
        mongoTemplate.insert(po);
    }


}
