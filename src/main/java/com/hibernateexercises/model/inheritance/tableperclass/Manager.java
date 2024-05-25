package com.hibernateexercises.model.inheritance.tableperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "managers")
@Data
@NoArgsConstructor
public class Manager extends Person {
    private String project;

    public Manager(String name, String surname, String project) {
        super(name, surname);
        this.project = project;
    }
}
