package org.example.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LiquibaseConfig {

    @SneakyThrows
    public Connection getConnection() {
        // Получение настроек подключения из liquibase.properties
        Properties properties = new Properties();
        properties.load(LiquibaseConfig.class.getClassLoader().getResourceAsStream("db/liquidbase.yml"));
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Создание объекта подключения к базе данных
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }

}