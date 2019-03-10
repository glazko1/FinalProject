package dao.impl;

import connection.ProxyConnection;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.user.InvalidUsernameOrPasswordException;
import dao.exception.user.UsedEmailException;
import dao.exception.user.UsedUsernameException;
import entity.User;
import entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pool.DatabaseConnectionPool;
import util.builder.UserBuilder;

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

    private static final Logger LOGGER = LogManager.getLogger(UserSQL.class);
    private static final String GET_USER_BY_ID_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_AND_PASSWORD_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND Password = ?";
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User";
    private static final String ADD_NEW_USER_SQL = "INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_EMAIL_SQL = "SELECT UserId FROM User WHERE Email = ?";
    private static final String CHANGE_BAN_STATUS_SQL = "UPDATE User SET Banned = ? WHERE UserId = ?";
    private static final String CHANGE_USER_STATUS_SQL = "UPDATE User SET StatusId = ? WHERE UserId = ?";
    private static final String EDIT_USER_SQL = "UPDATE User SET FirstName = ?, LastName = ?, Email = ? WHERE UserId = ?";
    private static final String CHANGE_PASSWORD_SQL = "UPDATE User SET Password = ? WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_FIRST_NAME_LAST_NAME_EMAIL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND FirstName = ? AND LastName = ? AND Email = ?";
    private static final String GET_USER_BY_EMAIL_EXCEPT_CURRENT_SQL = "SELECT UserId FROM User WHERE Email = ? AND UserId <> ?";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public User getUser(long userId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_ID_SQL);
            statement.setLong(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        throw new DAOException("No user with ID " + userId + " in DAO!");
    }

    @Override
    public User getUser(String username, String password) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD_SQL);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        throw new InvalidUsernameOrPasswordException("Login or password is incorrect!");
    }

    @Override
    public User getUser(String username) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getNextUser(set);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        throw new DAOException("No user with username " + username + " in DAO!");
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        PreparedStatement statement = null;
        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL_USERS_SQL);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                User user = getNextUser(set);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        return users;
    }

    @Override
    public void addNewUser(User user, String encoded) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(ADD_NEW_USER_SQL);
            if (usernameIsUsed(connection, user.getUsername())) {
                throw new UsedUsernameException("Username is already in use!");
            }
            if (emailIsUsed(connection, user.getEmail())) {
                throw new UsedEmailException("E-mail is already in use!");
            }
            statement.setLong(1, user.getUserId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, encoded);
            UserStatus status = user.getStatus();
            statement.setInt(6, status.getStatusId());
            statement.setString(7, user.getEmail());
            statement.setBoolean(8, user.isBanned());
            statement.setTimestamp(9, user.getBirthDateTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public void changeBanStatus(long userId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_BAN_STATUS_SQL);
            User user = getUser(userId);
            statement.setBoolean(1, !user.isBanned());
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public void changeUserStatus(long userId, int statusId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_USER_STATUS_SQL);
            statement.setInt(1, statusId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public void editUser(long userId, String firstName, String lastName, String email) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(EDIT_USER_SQL);
            if (emailIsUsed(connection, email, userId)) {
                throw new UsedEmailException("E-mail is already in use!");
            }
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setLong(4, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public void changePassword(long userId, String newPassword) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_PASSWORD_SQL);
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    @Override
    public User getUser(String username, String firstName, String lastName, String email) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_USERNAME_FIRST_NAME_LAST_NAME_EMAIL);
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
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
        throw new DAOException("No user with username " + username +
                ", first name " + firstName +
                ", last name" + lastName +
                "e-mail" + email + "in DAO!");
    }

    private User getNextUser(ResultSet set) throws SQLException {
        int statusId = set.getInt(5) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder builder = new UserBuilder(set.getLong(1));
        return builder.withUsername(set.getString(2))
                .withFirstName(set.getString(3))
                .withLastName(set.getString(4))
                .withStatus(status)
                .hasEmail(set.getString(6))
                .isBanned(set.getBoolean(7))
                .hasBirthDate(set.getTimestamp(8))
                .build();
    }

    private boolean usernameIsUsed(Connection connection, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_SQL)) {
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            return set.first();
        }
    }

    private boolean emailIsUsed(Connection connection, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            return set.first();
        }
    }

    private boolean emailIsUsed(Connection connection, String email, long userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_EXCEPT_CURRENT_SQL)) {
            statement.setString(1, email);
            statement.setLong(2, userId);
            ResultSet set = statement.executeQuery();
            return set.first();
        }
    }
}
