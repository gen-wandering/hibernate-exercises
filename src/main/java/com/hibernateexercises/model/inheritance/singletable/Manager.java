package com.hibernateexercises.model.inheritance.singletable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MGR")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Manager extends Person {
    private String project;

    public Manager(String name, String surname, String project) {
        super(name, surname);
        this.project = project;
    }
}
