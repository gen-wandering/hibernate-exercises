package com.hibernateexercises.runners.queries;

import com.hibernateapp.model.base.QUser;
import com.hibernateapp.model.base.User;
import com.hibernateapp.util.HibernateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class QueryDSLRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                getAmountOfBooksPerUser(session).forEach(System.out::println);

                session.getTransaction().commit();
            }
        }
    }

    private static List<String> findAll(Session session) {
        return new JPAQuery<User>(session)
                .select(QUser.user.name)
                .from(QUser.user)
                .fetch();
    }

    private static List<User> findAllByName(Session session, String name) {
        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.name.eq(name))
                .fetch();
    }

    private static List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .orderBy(new OrderSpecifier(Order.DESC, QUser.user.birthday))
                .limit(limit)
                .fetch();
    }

    private static List<Tuple> getAmountOfBooksPerUser(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(QUser.user.name,
                        QUser.user.surname,
                        QUser.user.books.size().count())
                .from(QUser.user)
                .join(QUser.user.books)
                .groupBy(QUser.user.name, QUser.user.surname)
                .orderBy(QUser.user.books.size().count().asc())
                .fetch();
    }
}
