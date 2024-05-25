package com.hibernateexercises.runners;

import com.hibernateexercises.model.base.Birthday;
import com.hibernateexercises.model.base.Roles;
import com.hibernateexercises.model.base.User;
import com.hibernateexercises.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

import static com.hibernateexercises.util.HibernateLifecycleUtil.getManagedEntities;

/*
 * On call to transaction commit() or flush(),
 * the Session will find any dirty entities from
 * its PersistenceContext and synchronize the state
 * to the database.
 * */

public class LifeCycleMain {
    public static void main(String[] args) {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {

                User user = session.find(User.class, 1);
                System.out.println(user);
            }
        }
    }

    private static void removing() {
        // TRANSIENT to session
        User user = new User(
                "New User",
                "New User",
                new Birthday(LocalDate.now().minusYears(26)),
                Roles.USER
        );

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.persist(user);

                // Выполняет синхронизацию dirty entities (обновленные сущностей)
                // с базой в момент вызова (продувает PersistenceContext)
                // session.flush();

                getManagedEntities(session).forEach(entityEntry ->
                        System.out.println(entityEntry.getEntityKey()
                                           + ": " + entityEntry.getStatus())
                );

                //                   GET -> DELETE
                // PERSISTENT to session -> REMOVED to session
                session.delete(user);

                getManagedEntities(session).forEach(entityEntry ->
                        System.out.println(entityEntry.getEntityKey()
                                           + ": " + entityEntry.getStatus())
                );

                // User все еще находится в PersistenceContext сессии,
                // но так как имеет статус DELETED (REMOVED) метод
                // session.get() возвращает null
                var deletedUser = session.get(User.class, 23);
                if (deletedUser == null) System.out.println("deletedUser is null");

                getManagedEntities(session).forEach(entityEntry ->
                        System.out.println(entityEntry.getEntityKey()
                                           + ": " + entityEntry.getStatus())
                );

                session.getTransaction().commit();
            }
        }
    }

    private static void refreshing() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                var user = session.get(User.class, 1);
                user.setName("new Name");

                // Session refresh from the DB
                session.refresh(user);

                session.getTransaction().commit();
            }
        }
    }

    private static void merging() {
        User user = new User(
                1,
                "New User",
                "New User213",
                new Birthday(LocalDate.now().minusYears(26)),
                Roles.USER,
                null
        );

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                var firstUser = session.find(User.class, 1);
                session.merge(user);

                System.out.println("----------");
                // Проверяет, имеет ли сессия (persistent context сессии)
                // изменения, которые еще не попали в базу данных.
                System.out.println("session.isDirty: " + session.isDirty());

                System.out.println(user == firstUser);      // false
                System.out.println(user.equals(firstUser)); // true
                System.out.println("----------");

                session.getTransaction().commit();
            }
        }
    }

    private static void detaching() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {

                session.beginTransaction();
                var user = session.get(User.class, 1);
                System.out.println(getManagedEntities(session).get(0));

                session.evict(user);
                // exception: detached entity passed to persist
                session.persist(user);
            }
        }
    }
}