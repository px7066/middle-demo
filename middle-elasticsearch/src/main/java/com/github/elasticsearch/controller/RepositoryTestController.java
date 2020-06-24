package com.github.elasticsearch.controller;


import com.github.elasticsearch.entity.Person;
import com.github.elasticsearch.repositories.ReactivePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepositoryTestController {
    @Autowired
    private ReactivePersonRepository repository;


    @GetMapping("findByFirstname")
    public Person findByFirstname(@RequestParam String firstName){
        return repository.findByFirstName(firstName).blockFirst();
    }

}
