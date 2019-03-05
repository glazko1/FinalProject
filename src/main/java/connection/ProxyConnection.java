package connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.ConnectionPool;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class ProxyConnection implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger(ProxyConnection.class);
    private ConnectionPool pool = DatabaseConnectionPool.getInstance();
    private Connection connection;

    public ProxyConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        pool.releaseConnection(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
