package com.rex.elasticsearch.demo.repository;

import com.rex.elasticsearch.demo.model.Person;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"age\": \"?0\"}}]}}")
    List<Person> findByAge(int age);
}
