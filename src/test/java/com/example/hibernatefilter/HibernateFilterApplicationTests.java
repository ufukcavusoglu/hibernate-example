package com.example.hibernatefilter;

import antlr.collections.impl.IntRange;
import com.example.hibernatefilter.dao.entity.Person;
import com.example.hibernatefilter.dao.PersonRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
@DataJpaTest
/*@SpringBootTest(classes = {Person.class, PersonRepository.class})*/
class HibernateFilterApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(personRepository).isNotNull();
    }

    LocalDateTime randomDateTime() {
        Random r = new Random();
        long t1 = System.currentTimeMillis() + r.nextInt();
        long t2 = t1 + 2 * 60 * 1000 + r.nextInt(60 * 1000) + 1;
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(t1), ZoneId.systemDefault());
    }

    Person createRandomPerson() {
        Person person = new Person();
        person.setAge(new Random().nextInt());
        person.setDateOfBirth(randomDateTime().toLocalDate());
        person.setName(new RandomString().nextString());
        person.setSalary(new Random().nextDouble());
        person.setSurname(new RandomString().nextString());
        person.setTime(randomDateTime().toLocalTime());
        return person;
    }

    List<Person> createRandomTestData(Integer count) {
        return IntStream
                .range(0, count)
                .mapToObj(x -> createRandomPerson())
                .collect(Collectors.toList());

    }

    @Test
    void get() {
        List<Person> people = createRandomTestData(100).stream().peek(personRepository::save).collect(Collectors.toList());
        Person person = Person
                .builder()
                .age(null)
                .id(null)
                .name(people.stream().findAny().orElseThrow().getName())
                .salary(null)
                .time(null)
                .dateOfBirth(null)
                .surname(null)
                .build();

        List<Person> persons = personRepository.findAll(Example.of(person));
        persons.stream().forEach(System.out::println);
    }

    @Test
    void get1() {
        List<Person> people = createRandomTestData(100).stream().peek(personRepository::save).collect(Collectors.toList());
        Person person = Person
                .builder()
                .age(null)
                .id(null)
                .name(people.stream().findAny().orElseThrow().getName())
                .salary(null)
                .time(null)
                .dateOfBirth(null)
                .surname(null)
                .build();

        List<Person> persons = personRepository.findAll(Example.of(person));
        persons.stream().forEach(System.out::println);
    }

}
