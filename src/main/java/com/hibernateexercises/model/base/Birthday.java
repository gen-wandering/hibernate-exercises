package com.hibernateexercises.model.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Birthday {
    private final LocalDate birthday;

    public Birthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthdate() {
        return birthday;
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Birthday birthday1 = (Birthday) o;
        return Objects.equals(birthday, birthday1.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(birthday);
    }

    @Override
    public String toString() {
        return "Birthday{" +
               "birthday=" + birthday +
               ", age=" + getAge() +
               '}';
    }
}