package com.hibernateexercises.runners;

import com.hibernateexercises.model.collectionmapping.Profile;
import com.hibernateexercises.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/*
 * Все из приведенных аннотаций используются для маппинга коллекций:
 * @ElementCollection, @OneToOne, @OneToMany, @ManyToOne, @ManyToMany.
 *
 * @ElementCollection - данные стандартных типов (Sting, Integer и тд).
 * Все остальные - данные пользовательских типов (см. SpringDataAc).
 * */
public class CollectionMappingRunner {
    public static void main(String[] args) {
        setImages();
        swapAndCheck();
    }

    public static void setImages() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.createNativeQuery("TRUNCATE TABLE images").executeUpdate();

                Profile profile = session.get(Profile.class, 1);

                profile.addImage("landscape.jpg");
                profile.addImage("background.jpg");
                profile.addImage("apple.jpg");
                profile.addImage("cat.jpg");

                session.getTransaction().commit();
            }
        }
    }

    public static void sortImagesAndCheck() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);
                profile.sortImages();

                session.evict(profile);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);

                session.getTransaction().commit();
            }
        }
    }

    public static void sortImagesAndCheckWithFlushing() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile beforeSortingBeforeFlushing = session.get(Profile.class, 1);
                beforeSortingBeforeFlushing.sortImages();
                Profile afterSortingBeforeFlushing = session.get(Profile.class, 1);
                session.flush();
                Profile afterSortingAfterFlushing = session.get(Profile.class, 1);

                // afterSortingBeforeFlushing == afterSortingAfterFlushing

                session.getTransaction().commit();
            }
        }
    }

    public static void deleteImagesAndCheck() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);
                profile.getImages().remove(2);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);

                session.getTransaction().commit();
            }
        }
    }

    public static void insertImagesAndCheck() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);
                profile.getImages().add(2, "newImage.PNG");

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);

                session.getTransaction().commit();
            }
        }
    }

    public static void swapAndCheck() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // sort

                Profile profile = session.get(Profile.class, 1);
                profile.sortImages();

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Profile profile = session.get(Profile.class, 1);
                profile.sortImages();
                List<String> images = profile.getImages();

                // swap

                String s = images.get(0);
                images.set(0, images.get(3));
                images.set(3, s);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                // check

                Profile profile = session.get(Profile.class, 1);

                session.getTransaction().commit();
            }
        }
    }
}
