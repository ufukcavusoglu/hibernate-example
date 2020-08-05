package com.example.hibernatefilter.utility;

import com.example.hibernatefilter.dao.entity.Person;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Utility {

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

    public List<Person> createRandomTestData(Integer count) {
        return IntStream
                .range(0, count)
                .mapToObj(x -> createRandomPerson())
                .collect(Collectors.toList());

    }

}
