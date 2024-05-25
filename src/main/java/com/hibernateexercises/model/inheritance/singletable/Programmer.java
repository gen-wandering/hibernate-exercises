package com.hibernateexercises.model.inheritance.singletable;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PROG")
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
