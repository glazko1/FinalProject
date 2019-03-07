package dao.impl;

import connection.ProxyConnection;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.InvalidUsernameOrPasswordException;
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

    private static final String GET_USER_BY_ID_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_AND_PASSWORD_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND Password = ?";
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User";
    private static final String ADD_NEW_USER_SQL = "INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_USERNAME_OR_EMAIL_SQL = "SELECT UserId FROM User WHERE Username = ? OR Email = ?";
    private static final String CHANGE_BAN_STATUS_SQL = "UPDATE User SET Banned = ? WHERE UserId = ?";
    private static final String CHANGE_USER_STATUS_SQL = "UPDATE User SET StatusId = ? WHERE UserId = ?";
    private static final String EDIT_USER_SQL = "UPDATE User SET FirstName = ?, LastName = ?, Email = ? WHERE UserId = ?";
    private static final String CHANGE_PASSWORD_SQL = "UPDATE User SET Password = ? WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_FIRST_NAME_LAST_NAME_EMAIL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND FirstName = ? AND LastName = ? AND Email = ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public User getUser(long userId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_SQL);
            statement.setLong(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5),
                        set.getString(6),
                        set.getBoolean(7),
                        set.getTimestamp(8));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No user with ID " + userId + " in DAO!");
    }

    @Override
    public User getUser(String username, String password) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD_SQL);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new InvalidUsernameOrPasswordException("Login or password is incorrect!");
    }

    @Override
    public User getUser(String username) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
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
                User user = getNextUser(set);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public void addNewUser(User user, String encoded) throws DAOException {
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
            addUserStatement.setString(5, encoded);
            addUserStatement.setInt(6, user.getStatusId());
            addUserStatement.setString(7, user.getEmail());
            addUserStatement.setBoolean(8, user.isBanned());
            addUserStatement.setTimestamp(9, user.getBirthDateTimestamp());
            addUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeBanStatus(long userId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_BAN_STATUS_SQL);
            User user = getUser(userId);
            statement.setBoolean(1, !user.isBanned());
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeUserStatus(long userId, int statusId) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_USER_STATUS_SQL);
            statement.setInt(1, statusId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void editUser(long userId, String firstName, String lastName, String email) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(EDIT_USER_SQL);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setLong(4, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changePassword(long userId, String newPassword) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_PASSWORD_SQL);
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User getUser(String username, String firstName, String lastName, String email) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_FIRST_NAME_LAST_NAME_EMAIL);
            statement.setString(1, username);
            statement.setString(4, email);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        throw new DAOException("No user with username " + username +
                ", first name " + firstName +
                ", last name" + lastName +
                "e-mail" + email + "in DAO!");
    }

    private User getNextUser(ResultSet set) throws SQLException {
        return new User(set.getLong(1),
                set.getString(2),
                set.getString(3),
                set.getString(4),
                set.getInt(5),
                set.getString(6),
                set.getBoolean(7),
                set.getTimestamp(8));
    }
}
