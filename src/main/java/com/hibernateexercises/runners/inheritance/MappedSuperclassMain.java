package com.hibernateexercises.runners.inheritance;

import com.hibernateapp.model.inheritance.mappedsuperclass.Car;
import com.hibernateapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MappedSuperclassMain {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Car car = new Car(1, "New car brand");

                session.merge(car);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                Car car = session.get(Car.class, 1);
                System.out.println(car);
                session.getTransaction().commit();
            }
        }
    }
}
