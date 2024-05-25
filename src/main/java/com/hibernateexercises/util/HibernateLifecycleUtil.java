package com.hibernateexercises.util;

import org.hibernate.Session;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HibernateLifecycleUtil {
    public static List<EntityEntry> getManagedEntities(Session session) {
        Map.Entry<Object, EntityEntry>[] entries = ((SessionImplementor) session)
                .getPersistenceContext()
                .reentrantSafeEntityEntries();

        return Arrays.stream(entries)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
