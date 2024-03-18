package org.example.config;


import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class HibernateConfig {
    @SneakyThrows
    public Connection getConnection() {
        // Получение настроек подключения из hibernate.cfg.xml
        Properties properties = new Properties();
        properties.load(HibernateConfig.class.getClassLoader().getResourceAsStream("hibernate.cfg.xml"));
        String url = properties.getProperty("hibernate.connection.url");
        String username = properties.getProperty("hibernate.connection.username");
        String password = properties.getProperty("hibernate.connection.password");

        // Установка соединения с базой данных
        return DriverManager.getConnection(url, username, password);
    }
}


