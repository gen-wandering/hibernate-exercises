package com.hibernateexercises.model.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ceo")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class CEO extends Manager {
    @Column(name = "amount_of_employees")
    private Integer amountOfEmployees;

    public CEO(String name, String surname, String project, Integer amountOfEmployees) {
        super(name, surname, project);
        this.amountOfEmployees = amountOfEmployees;
    }
}
