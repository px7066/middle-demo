package com.github.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.github.elasticsearch.repositories")
public class ElasticsearchStarter {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchStarter.class, args);
    }
}
