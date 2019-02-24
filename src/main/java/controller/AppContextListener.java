package controller;

import pool.DatabaseConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String driver = context.getInitParameter("db.driver");
        String url = context.getInitParameter("db.url");
        String user = context.getInitParameter("db.user");
        String password = context.getInitParameter("db.password");
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        pool.create(driver, url, user, password);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        pool.destroy();
    }
}
