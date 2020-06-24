package com.github.elasticsearch.controller;

import com.github.elasticsearch.entity.Person;
import com.github.elasticsearch.repositories.ReactivePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class TestController {

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ReactivePersonRepository reactivePersonRepository;

    public TestController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostMapping("/person")
    public String save(@RequestBody Person person) {
//        reactivePersonRepository.save(person);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(person.getId().toString())
                .withObject(person)
                .build();
        String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("person"));
        return documentId;
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id")  Long id) {
        Person person = elasticsearchOperations
                .queryForObject(GetQuery.getById(id.toString()), Person.class);
        return person;
    }
}