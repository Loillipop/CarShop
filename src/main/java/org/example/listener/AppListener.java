package org.example.listener;
import lombok.SneakyThrows;
import org.example.config.HibernateConfig;
import org.example.config.LiquidBaseConfig;
import org.example.dao.CarDAO;

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

        LiquidBaseConfig.setupLiquibase().migrate();

        HibernateConfig hibernateDataSource = new HibernateConfig();
        CarDAO carDAO = new CarDAO();

        ctx.setAttribute("hikariDataSource", hibernateDataSource);
        ctx.setAttribute("bouquetDAO", carDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateConfig ds = (HibernateConfig) servletContextEvent
                .getServletContext()
                .getAttribute("hikariDataSource");
        ds.close();
    }
}