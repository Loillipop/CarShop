package org.example.listener;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.example.config.HibernateConfig;
import org.example.config.LiquibaseConfig;
import org.example.dao.CarDAO;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        Liquibase liquibase = new Liquibase(
                "db/db.changelog/db.changelog-master.xml", // Путь к файлу changelog.xml
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(liquibaseConfig.getConnection())
        );
        liquibase.update();
        HibernateConfig hibernateDataSource = new HibernateConfig();
        CarDAO carDAO = new CarDAO(hibernateDataSource);

        ctx.setAttribute("hibernateDataSource", hibernateDataSource);
        ctx.setAttribute("carDAO", carDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("hibernateSessionFactory");
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}