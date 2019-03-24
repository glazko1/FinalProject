package pool;

import connection.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DatabaseConnectionPool implements ConnectionPool {

    private static final DatabaseConnectionPool INSTANCE = new DatabaseConnectionPool();

    public static DatabaseConnectionPool getInstance() {
        return INSTANCE;
    }

    private DatabaseConnectionPool() {}

    private static final Logger LOGGER = LogManager.getLogger(DatabaseConnectionPool.class);
    private String url;
    private String user;
    private String password;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> usedConnections;
    private static final int INITIAL_SIZE = 10;

    /**
     * Initializes connection pool with ten proxy-connections.
     * @param driver database driver.
     * @param url URL to database.
     * @param user username to connect to database.
     * @param password password to connect to database.
     * @see ProxyConnection
     */
    public void init(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
            this.url = url;
            this.user = user;
            this.password = password;
            availableConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
            for (int i = 0; i < INITIAL_SIZE; i++) {
                Connection connection = createConnection(url, user, password);
                availableConnections.add(new ProxyConnection(connection));
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.fatal(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates and returns connection to database.
     * @param url URL to database.
     * @param user username to connect to database.
     * @param password password to connect to database.
     * @return connection to database.
     * @throws SQLException if error occurred while getting connection.
     */
    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Returns proxy-connection from list of available connections and moves it
     * to the list of connections in use.
     * @return proxy-connection from list of available connections.
     */
    @Override
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        return connection;
    }

    /**
     * Removes proxy-connection from list of connections in use and puts it
     * to the list of available connections.
     * @param connection proxy-connection to move from list of connections in
     * use to list of available connections.
     */
    @Override
    public void releaseConnection(ProxyConnection connection) {
        availableConnections.add(connection);
        usedConnections.remove(connection);
    }

    /**
     * Destroys database connection pool: removes proxy-connections from list
     * of connections in use and list of available connections.
     */
    @Override
    public void destroy() {
        usedConnections.forEach(ProxyConnection::destroy);
        availableConnections.forEach(ProxyConnection::destroy);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
