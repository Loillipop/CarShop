package org.example.listener;
import lombok.SneakyThrows;
import org.example.config.HibernateConfig;
import org.example.config.LiquidBaseConfig;
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
        HibernateConfig hibernateDataSource = new HibernateConfig();
        LiquidBaseConfig.setupLiquibase(hibernateDataSource.getConnection()).update();
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