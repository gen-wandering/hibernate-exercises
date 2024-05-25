package com.hibernateexercises.runners.queries;

import com.hibernateexercises.model.base.User;
import com.hibernateexercises.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                findAllByName(session, "Jacqulyn").forEach(System.out::println);

                session.getTransaction().commit();
            }
        }
    }

    private static List<User> findAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);

        query.select(user);
        return session.createQuery(query).list();
    }

    private static List<User> findAllByName(Session session, String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);

        query.select(user).where(criteriaBuilder.equal(user.get("name"), name));

        return session.createQuery(query).list();
    }
}
