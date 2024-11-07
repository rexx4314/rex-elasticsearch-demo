package com.rex.elasticsearch.demo.controller;

import com.rex.elasticsearch.demo.model.Person;
import com.rex.elasticsearch.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @GetMapping("/{id}")
    public Optional<Person> getPersonById(@PathVariable String id) {
        return personService.findPersonById(id);
    }

    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personService.findAllPersons();
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
    }

    @GetMapping("/query")
    public List<Person> getPersonsByAgeQuery(@RequestParam int age) {
        return personService.getPersonsByAgeQuery(age);
    }

    @GetMapping("/elasticsearch-client")
    public List<Person> getPersonsByAgeElasticsearchClient(@RequestParam int age) {
        return personService.getPersonsByAgeElasticsearchClient(age);
    }
}
