package org.example.config;


import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class HibernateConfig {
    @SneakyThrows
    public Connection getConnection() {
        // Получение настроек подключения из hibernate.properties
        Properties properties = new Properties();
        properties.load(HibernateConfig.class.getClassLoader().getResourceAsStream("db/hibernate.properties"));
             var url = properties.getProperty("hibernate.connection.url");
             var username = properties.getProperty("hibernate.connection.username");
             var password = properties.getProperty("hibernate.connection.password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Установка соединения с базой данных
        return DriverManager.getConnection(url, username, password);

    }

}


