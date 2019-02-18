package dao.impl;

import connection.ProxyConnection;
import dao.UserDAO;
import dao.exception.DAOException;
import entity.User;
import pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSQL implements UserDAO {

    private static final UserSQL INSTANCE = new UserSQL();

    public static UserSQL getInstance() {
        return INSTANCE;
    }

    private UserSQL() {}

    private static final String GET_USER_BY_ID_SQL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_AND_PASSWORD_SQL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND Password = ?";
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User";
    private static final String ADD_NEW_USER_SQL = "INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_USERNAME_OR_EMAIL_SQL = "SELECT UserId FROM User WHERE Username = ? OR Email = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public User getUserById(long id) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6),
                        set.getString(7),
                        set.getBoolean(8),
                        set.getDate(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DAOException("No user with ID " + id + " in DAO!");
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD_SQL);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6),
                        set.getString(7),
                        set.getBoolean(8),
                        set.getDate(9));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No user with username " + username + " in DAO!");
    }

    @Override
    public User getUserByUsername(String username) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6),
                        set.getString(7),
                        set.getBoolean(8),
                        set.getDate(9));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No user with username " + username + " in DAO!");
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                User user = new User(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6),
                        set.getString(7),
                        set.getBoolean(8),
                        set.getDate(9));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public void addNewUser(User user) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement findUserStatement = connection.prepareStatement(GET_USER_BY_USERNAME_OR_EMAIL_SQL);
            findUserStatement.setString(1, user.getUsername());
            findUserStatement.setString(2, user.getEmail());
            ResultSet foundUsers = findUserStatement.executeQuery();
            if (foundUsers.first()) {
                throw new DAOException("Username or e-mail is already booked!");
            }
            PreparedStatement addUserStatement = connection.prepareStatement(ADD_NEW_USER_SQL);
            addUserStatement.setLong(1, user.getUserId());
            addUserStatement.setString(2, user.getUsername());
            addUserStatement.setString(3, user.getFirstName());
            addUserStatement.setString(4, user.getLastName());
            addUserStatement.setString(5, user.getPassword());
            addUserStatement.setInt(6, user.getStatusId());
            addUserStatement.setString(7, user.getEmail());
            addUserStatement.setBoolean(8, user.isBanned());
            addUserStatement.setDate(9, user.getBirthDate());
            addUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
