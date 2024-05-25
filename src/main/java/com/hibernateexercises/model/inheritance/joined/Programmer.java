package com.hibernateexercises.model.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "programmers")
@Data
@NoArgsConstructor
public class Programmer extends Person {
    @Column(name = "prog_language")
    private String language;

    public Programmer(String name, String surname, String language) {
        super(name, surname);
        this.language = language;
    }
}
