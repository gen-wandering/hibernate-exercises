package com.hibernateexercises.model.inheritance.mappedsuperclass;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

// Отмечается суперкласс от которого наследуются поля
@MappedSuperclass
public abstract class AbstractCar<T extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected T id;
}