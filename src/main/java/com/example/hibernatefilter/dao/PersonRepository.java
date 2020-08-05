package com.example.hibernatefilter.dao;

import com.example.hibernatefilter.dao.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {

    @Transactional(rollbackOn = {}, dontRollbackOn = {})
    @Query("SELECT p FROM Person p WHERE " +
            "(:name is null or p.name = :name) and " +
            "(:surname is null or p.surname = :surname) and " +
            "(:age is null or p.age = :age) and " +
            "(:dateOfBirth is null or p.dateOfBirth = :dateOfBirth) and " +
            "(:salary is null or p.salary = :salary)")
    List<Person> find(
            @Param(value = "name") String name,
            @Param(value = "surname") String surname,
            @Param(value = "age") Integer age,
            @Param(value = "dateOfBirth") LocalDate dateOfBirth,
            @Param(value = "salary") Double salary);

}
