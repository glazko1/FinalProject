package pool;

import connection.ProxyConnection;

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

    // proxy-connection implements connection and instead of closing returns connection to pool

    private String url;
    private String user;
    private String password;
    private BlockingQueue<ProxyConnection> availableConnections; // ArrayBlockingQueue
    private BlockingQueue<ProxyConnection> usedConnections;
    private static final int INITIAL_SIZE = 10; // set in properties

    public void create(String driver, String url, String user, String password) {
//        Properties properties = new Properties();
        try {
            Class.forName(driver);
            this.url = url;
            this.user = user;
            this.password = password;
            availableConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
            usedConnections = new ArrayBlockingQueue<>(INITIAL_SIZE);
            // classForName here
            // fatal error if exception here
            // first release reserved connections, then available
            for (int i = 0; i < INITIAL_SIZE; i++) {
                Connection connection = createConnection(url, user, password);
                availableConnections.add(new ProxyConnection(connection));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void releaseConnection(ProxyConnection connection) {
        availableConnections.add(connection);
        usedConnections.remove(connection);
    }

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
