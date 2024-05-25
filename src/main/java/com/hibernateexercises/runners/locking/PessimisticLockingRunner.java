package com.hibernateexercises.runners.locking;

import com.hibernateapp.model.locking.pessimistic.Payment;
import com.hibernateapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * Пессимистические блокировки
 * 1) Блокировка сохраняется до выполнения commit() или rollback()для транзакции (т.е.
 *    до завершения транзакции).
 *
 * 2) Пессимистические блокировки распространяются только на те транзакции, которые
 *    используют данный вид блокировок.
 *    То есть, если во время того как транзакция с типом блокировки PESSIMISTIC_WRITE
 *    заблокирует строку с id=1, но в это же время транзакция без блокировки выполнит
 *    любую DML команду (SELECT, UPDATE...), то данная транзакция не будет заблокирована,
 *    а команда будет выполнена без ожидания.
 *
 * 3) Строки, на которые действует блокировка, остаются неизменными для блокирующей
 *    транзакции вплоть до ее завершения, даже при наличии изменений из других транзакций,
 *    не имеющих блокировок. Вызов метода refresh(entity) также вернет изначальную версию
 *    entity, полученную при первом блокирующем вызове find(), даже при наличии изменений
 *    соответствующей строки в базе данных.
 *
 * Типы пессимистических блокировок
 * 1) PESSIMISTIC_READ
 *    Данные могут только читаться. Ни одна транзакция с типом блокировки
 *    PESSIMISTIC_READ (включая блокирующую) не может удалять или изменять
 *    заблокированные строки.
 *
 * 2) PESSIMISTIC_WRITE
 *    Блокирующая транзакция может вносить любые изменения, все остальные
 *    транзакции с типом блокировки PESSIMISTIC_WRITE будут находиться в ожидании.
 *
 * 3) PESSIMISTIC_FORCE_INCREMENT
 *    Работает аналогично PESSIMISTIC_WRITE, но дополнительно увеличивает
 *    столбец @Version, если он определен для данной сущности.
 * */
public class PessimisticLockingRunner {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {
            var locker = new Lock(1, factory);
            var accessor = new TryAccess(1, factory);

            locker.start();
            Thread.sleep(100);
            accessor.start();

            locker.join();
            accessor.join();
        }
    }

    // Пример к (2) и (3)
    private static void TransactionWithAndWithoutLockingMode() throws InterruptedException {
        try (SessionFactory factory = HibernateUtil.buildSessionFactory()) {

            int targetId = 1;

            var locker = new Thread(new FindAndLock(targetId, factory));
            var changer = new Thread(new ChangeAmount(targetId, factory));

            locker.start();
            TimeUnit.SECONDS.sleep(1);
            changer.start();

            locker.join();
            changer.join();
        }
    }

    private record FindAndLock(int targetId, SessionFactory factory) implements Runnable {
        @Override
        public void run() {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                // (1)
                Payment payment = session.find(Payment.class, targetId, LockModeType.PESSIMISTIC_READ);

                System.out.println("FindAndLock acquire the lock on: " + payment + " | + " + LocalDateTime.now());
                TimeUnit.SECONDS.sleep(5);
                session.refresh(payment);
                // The same payment as in (1) due to PESSIMISTIC lock
                System.out.println("FindAndLock: refreshed payment: " + payment);
                System.out.println("FindAndLock release the lock | " + LocalDateTime.now());

                session.getTransaction().commit();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private record ChangeAmount(int targetId, SessionFactory factory) implements Runnable {
        @Override
        public void run() {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                Payment payment = session.find(Payment.class, targetId);
                System.out.println("Trying to change amount of " + payment + " | " + LocalDateTime.now());
                payment.setAmount(new Random().nextDouble());
                System.out.println("Payment after change " + payment + " | " + LocalDateTime.now());

                session.getTransaction().commit();
            }
        }
    }

    private static class Lock extends Thread {
        private final int targetId;
        private final SessionFactory factory;

        private Lock(int targetId, SessionFactory factory) {
            this.targetId = targetId;
            this.factory = factory;
        }

        private void doWork(Session session) throws InterruptedException {
            Payment payment = session.find(Payment.class, targetId, LockModeType.PESSIMISTIC_READ);
            System.out.println("Lock thread: acquired the lock on " + payment + " | " + LocalDateTime.now());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Lock thread: release the lock | " + LocalDateTime.now());
        }

        @Override
        public void run() {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                doWork(session);
                session.getTransaction().commit();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class TryAccess extends Thread {
        private final int targetId;
        private final SessionFactory factory;

        private TryAccess(int targetId, SessionFactory factory) {
            this.targetId = targetId;
            this.factory = factory;
        }

        private void doWork(Session session) {
            System.out.println("TryAccess thread: try to acquire the lock | " + LocalDateTime.now());
            Payment payment = session.find(Payment.class, targetId, LockModeType.PESSIMISTIC_READ);
            System.out.println("TryAccess thread: acquired the lock on " + payment + " | " + LocalDateTime.now());
            payment.setAmount(new Random().nextDouble());
        }

        @Override
        public void run() {
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                doWork(session);
                session.getTransaction().commit();
            }
        }
    }
}