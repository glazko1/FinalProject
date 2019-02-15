package dao.impl;

import dao.exception.DAOException;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pool.DatabaseConnectionPool;

import java.sql.Date;

import static org.testng.Assert.*;

public class UserSQLTest {

    private UserSQL userSQL = UserSQL.getInstance();

    @BeforeClass
    public void init() {
        DatabaseConnectionPool.getInstance().create("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/finalproject?useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root","relbud2k1");
    }

    @Test
    public void addNewUser_validInformation_correctValues() {
        User user = new User(241, "glazko1", "New", "User",
                "Password",
                4, "newuser@yahoo.com", false, Date.valueOf("2000-11-12"));
        try {
            userSQL.addNewUser(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}