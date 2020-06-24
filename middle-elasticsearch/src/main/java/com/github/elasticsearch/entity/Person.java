package com.github.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "person")
@Data
public class Person {
    @Id
    private String id;

    private String firstName;

    private String lastName;
}
