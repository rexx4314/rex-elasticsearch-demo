package com.rex.elasticsearch.demo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.rex.elasticsearch.demo.model.Person;
import com.rex.elasticsearch.demo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ElasticsearchClient elasticsearchClient;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> findPersonById(String id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }

    public List<Person> getPersonsByAgeQuery(int age) {
        return personRepository.findByAge(age);
    }

    public List<Person> getPersonsByAgeElasticsearchClient(int age) {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("person")
                .query(q -> q.match(m -> m.field("age").query(age)))
                .build();

        SearchResponse<Person> response;
        try {
            response = elasticsearchClient.search(searchRequest, Person.class);
        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch 검색 오류", e);
        }

        return response.hits().hits().stream()
                .map(hit -> {
                    Person person = hit.source();
                    if (person != null) {
                        person.setId(hit.id());
                    }
                    return person;
                })
                .collect(Collectors.toList());
    }
}
