package connection;

import pool.ConnectionPool;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class ProxyConnection implements AutoCloseable {

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
            e.printStackTrace();
        }
    }
}
