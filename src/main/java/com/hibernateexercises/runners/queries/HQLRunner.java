package com.hibernateexercises.runners.queries;

import com.hibernateapp.model.base.User;
import com.hibernateapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Arrays;
import java.util.List;

public class HQLRunner {
    private static final String getUsers = "SELECT u FROM User u";
    private static final String getNames = "SELECT u.name FROM User u";
    private static final String getAllParams = """
                SELECT u.id, u.name, u.surname, u.role, u.birthday
                FROM User u
                WHERE u.id = 1
            """;
    private static final String getUserWithName = """
                SELECT u FROM User u
                WHERE u.name = ?1
            """;
    private static final String getUserWithSurname = """
                SELECT u FROM User u
                WHERE u.surname = :surname
            """;
    private static final String deleteBook = """
                DELETE FROM Book b
                WHERE b.id = :id
            """;
    private static final String amountOfBooks = """
            SELECT u.name, u.surname, count(*) AS amount_of_books
            FROM User u
                     JOIN u.books b
            GROUP BY u.name, u.surname
            ORDER BY amount_of_books DESC
            """;
    private static final String nativeGetUsers = """
            SELECT * FROM users u
                JOIN books b ON u.id = b.user_id
            """;
    private static final String join = """
            SELECT u, b
            FROM User u JOIN u.books b
            """;

    // Для решения проблемы "N+1"
    private static final String joinFetch = """
            SELECT u, b.title
            FROM User u JOIN FETCH u.books b
            """;

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                join(session);

                session.getTransaction().commit();
            }
        }
    }

    private static void execute(Session session) {
        session.createQuery(getUsers, User.class)
                .list()
                .forEach(System.out::println);
    }

    private static void getNames(Session session) {
        session.createQuery(getNames, String.class)
                .list()
                .forEach(System.out::println);
    }

    private static void nativeGetUsers(Session session) {
        session.createNativeQuery(nativeGetUsers, User.class)
                .list()
                .forEach(System.out::println);
    }

    /*
     * При выборе всех параметров, вместо объекта User добавления
     * в PersistenceContext не происходит.
     * */
    private static void getAllParametersButNotEntity(Session session) {
        session.createQuery(getAllParams, Object[].class)
                .list()
                .forEach(objects -> System.out.println(Arrays.toString(objects)));
    }

    private static void executeWithParam(Session session) {
        session.createQuery(getUserWithName, User.class)
                .setParameter(1, "Gustavo")
                .list()
                .forEach(System.out::println);
    }

    private static void executeWithNamedParam(Session session) {
        session.createQuery(getUserWithSurname, User.class)
                .setParameter("surname", "Leannon")
                .list()
                .forEach(System.out::println);
    }

    private static void executeNamedQuery() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            List<User> users;

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                users = session.createNamedQuery("getUsersOrderedByName", User.class).list();
                session.getTransaction().commit();
            }
            users.forEach(System.out::println); // fetch type eager
        }
    }

    private static void deleteBook(Session session) {
        session.createQuery(deleteBook)
                .setParameter("id", 1)
                .executeUpdate();
    }

    private static void join(Session session) {
        session.createQuery(join, Object[].class)
                .list()
                .forEach(row -> System.out.println(Arrays.toString(row)));
    }

    private static void joinFetch(Session session) {
        session.createQuery(joinFetch, Object[].class)
                .list()
                .forEach(row -> System.out.println(Arrays.toString(row)));
    }

    private static void getAmountOfBooksPerUser(Session session) {
        List<Object[]> list = session.createQuery(amountOfBooks, Object[].class).list();

        for (int i = 0; i < list.size(); ) {
            Object[] row = list.get(i);
            System.out.print((++i) + ") "
                             + row[0] + " "
                             + row[1] + " has "
            );
            if (row[2] instanceof Long val) {
                if (val == 1) System.out.println(val + " book.");
                else System.out.println(val + " books.");
            }
        }
    }
}