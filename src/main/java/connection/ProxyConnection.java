package connection;

import pool.ConnectionPool;
import pool.DatabaseConnectionPool;

import java.sql.Connection;

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
}
