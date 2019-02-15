package pool;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionPoolTest {

    @Test
    public void test() throws ClassNotFoundException, IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("./src/main/resources/db.properties"));
        Class.forName(properties.getProperty("db.driver"));
//        DatabaseConnectionPool pool = DatabaseConnectionPool.create(
//                properties.getProperty("db.url"),
//                properties.getProperty("db.user"),
//                properties.getProperty("db.password"));
        // url -> to properties
        // промежуточный файл для связывания проперти и пула соединений
        // private static final String DRIVER = "db.driver"; - значения берем из проперти в методах
//        Connection connection = pool.getConnection();
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproject" +
//                        "?useLegacyDatetimeCode=false" +
//                        "&serverTimezone=UTC", "root", "relbud2k1");
//        Statement statement = connection.createStatement();
        // use PreparedStatement
        // use setters in it
        // firstly registers then executes (calls DB twice)
        // concatenation is BAD
        // PreparedStatement saves from SQL injection
        // use try-with-resources
        // close statements
//        ResultSet set = statement.executeQuery("SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM user WHERE FirstName = 'Yegor';");
//        while (set.next()) {
//            System.out.println(set.getString(1) + " "
//                    + set.getString(2) + " "
//                    + set.getString(3) + " "
//                    + set.getString(4) + " "
//                    + set.getString(5) + " "
//                    + set.getString(6) + " "
//                    + set.getString(7) + " "
//                    + set.getString(8) + " ");
//        }
    }
}