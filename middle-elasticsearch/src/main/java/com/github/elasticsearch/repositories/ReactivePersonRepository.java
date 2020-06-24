package com.github.elasticsearch.repositories;

import com.github.elasticsearch.entity.Person;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactivePersonRepository extends ReactiveSortingRepository<Person, String> {
    Flux<Person> findByFirstName(String firstname);

    Flux<Person> findByFirstName(Publisher<String> firstname);

    Flux<Person> findByFirstNameOrderByLastName(String firstname);

    Flux<Person> findByFirstName(String firstname, Sort sort);

    Flux<Person> findByFirstName(String firstname, Pageable page);

    Mono<Person> findByFirstNameAndLastName(String firstname, String lastname);

    Mono<Person> findFirstByLastName(String lastname);

    @Query("{ \"bool\" : { \"must\" : { \"term\" : { \"lastname\" : \"?0\" } } } }")
    Flux<Person> findByLastName(String lastname);

    Mono<Long> countByFirstName(String firstname);

    Mono<Boolean> existsByFirstName(String firstname);

    Mono<Long> deleteByFirstName(String firstname);
}
