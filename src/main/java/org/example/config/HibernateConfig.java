package org.example.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties props = new Properties();
                props.load(HibernateConfig.class.getClassLoader().getResourceAsStream("hibernate.cfg.xml"));
                configuration.setProperties(props);

                // Если используется HikariCP, настройка соединения должна быть в файле hibernate.properties
                // Но вы можете добавить любые другие настройки Hibernate в этот блок кода

                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
