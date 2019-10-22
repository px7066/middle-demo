package com.github.middlemongo.controller;

import com.github.middlemongo.service.IMongoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoTestController {

    @Autowired
    private IMongoTestService mongoTestService;

    @RequestMapping("test")
    public String test(){
        System.out.println("MongoTest");
        mongoTestService.insert();
        return "Hello";
    }

    @RequestMapping("query")
    public Integer query(){
        return mongoTestService.query();
    }
}
