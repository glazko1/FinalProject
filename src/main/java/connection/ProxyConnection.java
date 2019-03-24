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

    /**
     * Returns connection to list of available connections in database
     * connection pool.
     */
    @Override
    public void close() {
        pool.releaseConnection(this);
    }

    /**
     * @return connection from this proxy-connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes connection from this proxy-connection.
     */
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
