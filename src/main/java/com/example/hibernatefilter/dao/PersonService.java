package com.example.hibernatefilter.dao;

import com.example.hibernatefilter.dao.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Specification<Person> getSpecs(LocalDate localDateS, LocalDate localDateB,
                                          Integer ageS, Integer ageB,
                                          LocalTime timeS, LocalTime timeB,
                                          Double salaryS, Double salaryB,
                                          Example<Person> example) {
        return (Specification<Person>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (localDateS != null) predicates.add(builder.greaterThan(root.get("dateOfBirth"), localDateS));
            if (timeS != null) predicates.add(builder.greaterThan(root.get("time"), timeS));
            if (ageS != null) predicates.add(builder.greaterThan(root.get("age"), ageS));
            if (salaryB != null) predicates.add(builder.greaterThan(root.get("salary"), salaryB));

            if (localDateB != null) predicates.add(builder.lessThan(root.get("dateOfBirth"), localDateB));
            if (timeB != null) predicates.add(builder.lessThan(root.get("time"), timeB));
            if (ageB != null) predicates.add(builder.lessThan(root.get("age"), ageB));
            if (salaryS != null) predicates.add(builder.lessThan(root.get("salary"), salaryS));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and((Predicate[]) predicates.toArray());
        };
    }

}
