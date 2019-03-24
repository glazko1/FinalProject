package pool;

import connection.ProxyConnection;

public interface ConnectionPool {

    /**
     * @return available proxy-connection.
     */
    ProxyConnection getConnection();

    /**
     * Releases proxy-connection.
     * @param connection proxy-connection to release.
     */
    void releaseConnection(ProxyConnection connection);

    /**
     * Destroys connection pool.
     */
    void destroy();

    String getUrl();
    String getUser();
    String getPassword();
}
