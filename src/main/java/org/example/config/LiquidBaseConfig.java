package org.example.config;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import java.sql.Connection;
import java.util.Properties;

public class LiquidBaseConfig {

    @SneakyThrows
    public static Liquibase setupLiquibase(Connection connection) {
        Properties props = new Properties();
        props.load(LiquidBaseConfig.class.getClassLoader().getResourceAsStream("liquidbase.yml"));

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        return new Liquibase(props.getProperty("db.dbchangelog-update.xml"), new ClassLoaderResourceAccessor(), database);
    }
}