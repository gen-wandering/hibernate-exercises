package com.hibernateexercises.model.inheritance.tableperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    // IDENTITY strategy is not available due to the InheritanceType
    @GeneratedValue(generator = "people_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "people_id_gen", sequenceName = "people_id_seq",
            allocationSize = 1)
    private Integer id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}