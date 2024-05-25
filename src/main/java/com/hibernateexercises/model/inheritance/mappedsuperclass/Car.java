package com.hibernateexercises.model.inheritance.mappedsuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car extends AbstractCar<Integer> {
    private String brand;

    public Car(int id, String brand) {
        this.id = id;
        this.brand = brand;
    }
}