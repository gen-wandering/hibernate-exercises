package com.hibernateexercises.runners.inheritance;

import com.hibernateapp.model.inheritance.joined.CEO;
import com.hibernateapp.model.inheritance.joined.Manager;
import com.hibernateapp.model.inheritance.joined.Person;
import com.hibernateapp.model.inheritance.joined.Programmer;
import com.hibernateapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class JoinedRunner {
    public static void main(String[] args) {
        secondLevelInheritancePolymorphicQueries();
    }

    private static void polymorphicQueries() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Manager manager = new Manager("M", "M", "UI");
                Programmer programmer = new Programmer("P", "P", "C++");

                Person personM = new Manager("personM", "personM", "UX");
                Person personP = new Programmer("PersonP", "PersonP", "Java");

                Person person = new Person("person", "person");

                session.persist(manager);
                session.persist(programmer);
                session.persist(personM);
                session.persist(personP);
                session.persist(person);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Person manager = session.get(Person.class, 1); // полиморфный запрос

                if (manager instanceof Manager) {
                    System.out.println("Manager: " + manager);
                }
                session.getTransaction().commit();
            }
        }
    }

    private static void polymorphicMerging() {

        Person person;

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // id -> 1
                Manager manager = new Manager("M", "M", "UI");
                session.persist(manager);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // Get manager by Person class
                person = session.get(Person.class, 1);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // Merge manager with Person ref
                person.setName("new manager");
                session.merge(person);

                session.getTransaction().commit();
            }
        }
    }

    private static void secondLevelInheritancePolymorphicQueries() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                CEO ceo = new CEO("CEO-name",
                        "CEO-surname",
                        "Managing company",
                        52
                );
                session.persist(ceo);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                // полиморфный запрос Person
                Person ceo = session.get(Person.class, 1);

                if (ceo instanceof Manager) {
                    if (ceo instanceof CEO) {
                        System.out.println("ceo: " + ceo);
                    }
                }
                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                // полиморфный запрос Manager
                Person ceo = session.get(Manager.class, 1);

                if (ceo instanceof CEO) {
                    System.out.println("ceo: " + ceo);
                }
                session.getTransaction().commit();
            }
        }
    }
}