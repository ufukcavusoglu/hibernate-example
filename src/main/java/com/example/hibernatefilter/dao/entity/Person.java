package com.example.hibernatefilter.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
@Setter
@ToString
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("Id")
    private UUID id;
    @Column
    @JsonProperty("Name")
    private String name;
    @Column
    @JsonProperty("Surname")
    private String surname;
    @Column
    @JsonProperty("Salary")
    private Double salary;
    @Column
    @JsonProperty("Age")
    private Integer age;
    @Column
    @JsonProperty("BirthDay")
    @JsonFormat(pattern = "dd-MM-YYYY")
    private LocalDate dateOfBirth;
    @Column
    @JsonProperty("Time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
}
