package pool;

import connection.ProxyConnection;

public interface ConnectionPool {

    ProxyConnection getConnection();
    void releaseConnection(ProxyConnection connection);
    String getUrl();
    String getUser();
    String getPassword();
}
