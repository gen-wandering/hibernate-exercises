package com.hibernateexercises.util;

import com.hibernateexercises.converter.BirthdayConverter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAttributeConverter(new BirthdayConverter(), true);
        return cfg.buildSessionFactory();
    }
}
