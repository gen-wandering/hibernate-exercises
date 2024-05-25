package com.hibernateexercises.runners.locking;

import com.hibernateexercises.model.locking.optimistic.Purchase;
import com.hibernateexercises.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.LockModeType;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OptimisticLockingRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       firstCommitWins();
    }

    private static void refreshWithOptimisticLock() throws InterruptedException, ExecutionException {
        int targetId = 1;

        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Purchase purchase = session.find(Purchase.class, targetId);
                System.out.println("main: " + purchase);
                purchase.setHash("main_hash");
                System.out.println("main: " + purchase);

                concurrentChange(factory, targetId);

                // After refreshing it is possible to perform an update.
                session.refresh(purchase, LockModeType.OPTIMISTIC);

                System.out.println("main: " + purchase);

                session.getTransaction().commit();
            }
        }
    }

    private static void detachMergeWithOptimisticLock() throws ExecutionException, InterruptedException {
        int targetId = 1;

        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Purchase purchase = session.find(Purchase.class, targetId);
                System.out.println("main: " + purchase);

                session.detach(purchase);
                concurrentChange(factory, targetId);

                // OptimisticLockException
                session.merge(purchase);

                System.out.println("main: " + purchase);

                session.getTransaction().commit();
            }
        }
    }

    private static void firstCommitWins() throws ExecutionException, InterruptedException {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Purchase purchase = session.find(Purchase.class, 1);
                purchase.setHash("main_hash");

                concurrentChange(factory, 1);

                // OptimisticLockException
                session.getTransaction().commit();
            }
        }
    }

    private static void firstCommitWinsWithForceIncrement() throws ExecutionException, InterruptedException {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                session.find(
                        Purchase.class,
                        1,
                        LockModeType.OPTIMISTIC_FORCE_INCREMENT
                );
                concurrentChange(factory, 1);

                // OptimisticLockException due to OPTIMISTIC_FORCE_INCREMENT
                // even without changes in purchase
                session.getTransaction().commit();
            }
        }
    }

    private static void concurrentChange(SessionFactory factory, int id) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Purchase purchase = session.find(Purchase.class, id);
                purchase.setSum(new Random().nextDouble());

                session.getTransaction().commit();
            }
        }).get();
        executorService.shutdown();
    }
}