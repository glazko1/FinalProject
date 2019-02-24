package pool;

import connection.ProxyConnection;

public interface ConnectionPool {

    ProxyConnection getConnection();
    void releaseConnection(ProxyConnection connection);
    void destroy();
    String getUrl();
    String getUser();
    String getPassword();
}
