package com.example.hibernatefilter.controller;

import com.example.hibernatefilter.dao.PersonRepository;
import com.example.hibernatefilter.dao.PersonService;
import com.example.hibernatefilter.dao.entity.Person;
import com.example.hibernatefilter.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@RestController(value = "/")
public class Controller {

    PersonService personService;
    PersonRepository personRepository;
    Utility utility;

    @Autowired
    public Controller(PersonService personService, PersonRepository personRepository, Utility utility) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.utility = utility;
    }

    @RequestMapping(value = "create-random-test", method = RequestMethod.GET)
    public List<Person> getFilter() {
        utility.createRandomTestData(100).stream().map(personRepository::saveAndFlush).forEach(System.out::println);
        return personRepository.findAll();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Person save(@RequestBody Person person) {
        return personRepository.saveAndFlush(person);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<Person> get(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam Integer age,
                            @RequestParam Double salary,
                            @RequestParam LocalTime time,
                            @RequestParam LocalDate dateOfBirth,
                            @RequestParam Integer pageSize,
                            @RequestParam Integer page) {

        return personRepository.findAll(Example.of(Person
                .builder()
                .name(name)
                .surname(surname)
                .age(age)
                .salary(salary)
                .time(time)
                .dateOfBirth(dateOfBirth)
                .build()), PageRequest.of(page, pageSize, Sort.by("name").and(Sort.by("surname"))))
                .toList();
    }

    @RequestMapping(value = "get-detail-wtih-filtering", method = RequestMethod.GET)
    public List<Person> getWithDetailedFilter(@RequestParam String name,
                                              @RequestParam String surname,
                                              @RequestParam Double salary,
                                              @RequestParam Integer ageS,
                                              @RequestParam LocalTime timeS,
                                              @RequestParam LocalDate dateOfBirthS,
                                              @RequestParam Integer pageSize,
                                              @RequestParam Integer page) {

        return personRepository.findAll(Example.of(Person
                        .builder()
                        .name(name)
                        .surname(surname)
                        .salary(salary)
                        .age(ageS)
                        .time(timeS)
                        .dateOfBirth(dateOfBirthS)
                        .build(),
                ExampleMatcher.matching().withMatcher("name", startsWith())),
                PageRequest.of(page, pageSize, Sort.by("name").and(Sort.by("surname"))))
                .toList();
    }

    @RequestMapping(value = "get-detail-with-filtering-enlarged", method = RequestMethod.GET)
    public List<Person> getWithDetailedFilterEnlarged(@RequestParam String name,
                                                      @RequestParam String surname,
                                                      @RequestParam Double salaryS, @RequestParam Double salaryB,
                                                      @RequestParam Integer ageS, @RequestParam Integer ageB,
                                                      @RequestParam LocalTime timeS, @RequestParam LocalTime timeB,
                                                      @RequestParam LocalDate dateOfBirthS, @RequestParam LocalDate dateOfBirthB,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam Integer page) {

        return personRepository.findAll(personService.getSpecs(
                dateOfBirthS, dateOfBirthB,
                ageS, ageB,
                timeS, timeB,
                salaryS, salaryB,
                Example.of(Person
                        .builder()
                        .name(name)
                        .surname(surname)
                        .build(), ExampleMatcher.matching().withMatcher("name", startsWith()))),PageRequest.of(page,pageSize,Sort.by("id")))
                .toList();
    }

}
