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
    private static final String ADD_NEW_USER_SQL = "INSERT INTO User (UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate) VALUES (UUID(), ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_EMAIL_SQL = "SELECT UserId FROM User WHERE Email = ?";
    private static final String CHANGE_BAN_STATUS_SQL = "UPDATE User SET Banned = ? WHERE UserId = ?";
    private static final String CHANGE_USER_STATUS_SQL = "UPDATE User SET StatusId = ? WHERE UserId = ?";
    private static final String EDIT_USER_SQL = "UPDATE User SET FirstName = ?, LastName = ?, Email = ? WHERE UserId = ?";
    private static final String CHANGE_PASSWORD_SQL = "UPDATE User SET Password = ? WHERE UserId = ?";
    private static final String GET_USER_BY_USERNAME_FIRST_NAME_LAST_NAME_EMAIL = "SELECT UserId, Username, FirstName, LastName, Password, StatusId, Email, Banned, BirthDate FROM User WHERE Username = ? AND FirstName = ? AND LastName = ? AND Email = ?";
    private static final String GET_USER_BY_EMAIL_EXCEPT_CURRENT_SQL = "SELECT UserId FROM User WHERE Email = ? AND UserId <> ?";
    private static final String GET_ALL_USERS_SORTED_BY_ID_ASC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY UserId ASC";
    private static final String GET_ALL_USERS_SORTED_BY_ID_DESC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY UserId DESC";
    private static final String GET_ALL_USERS_SORTED_BY_USERNAME_ASC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY Username ASC";
    private static final String GET_ALL_USERS_SORTED_BY_USERNAME_DESC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY Username DESC";
    private static final String GET_ALL_USERS_SORTED_BY_EMAIL_ASC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY Email ASC";
    private static final String GET_ALL_USERS_SORTED_BY_EMAIL_DESC_SQL = "SELECT UserId, Username, FirstName, LastName, StatusId, Email, Banned, BirthDate FROM User ORDER BY Email DESC";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    /**
     * Creates and returns user according to information in database and given
     * parameter (user's ID). Gets proxy connection from database pool, prepares
     * statement on it (by SQL-string) and gets result set with user.
     * @param userId ID of user to find.
     * @return user with given ID.
     * @throws DAOException if {@link SQLException} was caught or there is no
     * user with given ID in database.
     */
    @Override
    public User getUser(String userId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(GET_USER_BY_ID_SQL);
            statement.setString(1, userId);
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

    /**
     * Creates and returns user according to information in database and given
     * parameter (username and encoded password). Gets proxy connection from database
     * pool, prepares statement on it (by SQL-string) and gets result set with user.
     * @param username username of user to find.
     * @param password encoded password entered by user.
     * @return user with given username and encoded password.
     * @throws InvalidUsernameOrPasswordException if username or password is incorrect.
     * @throws DAOException if {@link SQLException} was caught.
     */
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

    /**
     * Creates and returns list of users existing in database. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string)
     * and gets result set with all aliens in database.
     * @return list of users existing in database.
     * @throws DAOException if {@link SQLException} was caught.
     */
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

    /**
     * Adds new user to database according to all fields in given object and encoded
     * password. User can't be added if username or e-mail is already in use. Gets
     * proxy connection from database pool, prepares statement on it (by SQL-string)
     * and executes.
     * @param user object with information about new user.
     * @param encoded new user's encoded password.
     * @throws UsedUsernameException if username is already in use.
     * @throws UsedEmailException if e-mail is already in use.
     * @throws DAOException if {@link SQLException} was caught.
     */
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
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, encoded);
            UserStatus status = user.getStatus();
            statement.setInt(5, status.getStatusId());
            statement.setString(6, user.getEmail());
            statement.setBoolean(7, user.isBanned());
            statement.setTimestamp(8, user.getBirthDateTimestamp());
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

    /**
     * Changes ban status of user (from banned to unbanned and vice versa).
     * @param userId ID of user to change ban status.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void changeBanStatus(String userId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_BAN_STATUS_SQL);
            User user = getUser(userId);
            statement.setBoolean(1, !user.isBanned());
            statement.setString(2, userId);
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

    /**
     * Changes status of user (administrator, movie fan, alien specialist and user).
     * @param userId ID of user to change status.
     * @param statusId ID of new user's status.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void changeUserStatus(String userId, int statusId) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_USER_STATUS_SQL);
            statement.setInt(1, statusId);
            statement.setString(2, userId);
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

    /**
     * Updates information (first name, last name and e-mail) of user with given ID
     * according to given parameters. Gets proxy connection from database pool,
     * prepares statement on it (by SQL-string) and executes.
     * @param userId ID of user to edit.
     * @param firstName new first name.
     * @param lastName new last name.
     * @param email new e-mail.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void editUser(String userId, String firstName, String lastName, String email) throws DAOException {
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
            statement.setString(4, userId);
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

    /**
     * Changes password of user with given ID.
     * @param userId ID of user to change password.
     * @param newPassword new encoded password.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void changePassword(String userId, String newPassword) throws DAOException {
        PreparedStatement statement = null;
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            statement = connection.prepareStatement(CHANGE_PASSWORD_SQL);
            statement.setString(1, newPassword);
            statement.setString(2, userId);
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

    /**
     * Creates and returns user according to information in database and given
     * parameters (username, first name, last name, e-mail). Used in process of
     * restoring password (all entered information should be correct). Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string) and
     * gets result set with user.
     * @param username username of user to find.
     * @param firstName first name of user to find.
     * @param lastName last name of user to find.
     * @param email e-mail of user to find.
     * @return user with given parameter.
     * @throws DAOException if {@link SQLException} was caught or there is no user
     * with given parameters in database.
     */
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

    /**
     * Creates and returns list of users existing in database sorted by givem
     * parameter (ID, username or e-mail) ascending or descending. Gets proxy
     * connection from database pool, prepares statement on it (by SQL-string)
     * and gets result set with all aliens in database.
     * @param sortedBy sorting parameter (ID, username or e-mail).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of users.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<User> getAllUsersSorted(String sortedBy, String sortType) throws DAOException {
        PreparedStatement statement = null;
        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            String sql = getSortingSql(sortedBy, sortType);
            statement = connection.prepareStatement(sql);
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

    private String getSortingSql(String sortedBy, String sortType) throws DAOException {
        switch (sortedBy) {
            case "userId":
                return "ASC".equals(sortType) ?
                        GET_ALL_USERS_SORTED_BY_ID_ASC_SQL :
                        GET_ALL_USERS_SORTED_BY_ID_DESC_SQL;
            case "username":
                return "ASC".equals(sortType) ?
                        GET_ALL_USERS_SORTED_BY_USERNAME_ASC_SQL :
                        GET_ALL_USERS_SORTED_BY_USERNAME_DESC_SQL;
            case "email":
                return "ASC".equals(sortType) ?
                        GET_ALL_USERS_SORTED_BY_EMAIL_ASC_SQL :
                        GET_ALL_USERS_SORTED_BY_EMAIL_DESC_SQL;
            default:
                break;
        }
        throw new DAOException("No sorting SQL for parameters " + sortedBy + " and " + sortType);
    }

    private User getNextUser(ResultSet set) throws SQLException {
        int statusId = set.getInt(5) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder builder = new UserBuilder(set.getString(1));
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

    private boolean emailIsUsed(Connection connection, String email, String userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_EXCEPT_CURRENT_SQL)) {
            statement.setString(1, email);
            statement.setString(2, userId);
            ResultSet set = statement.executeQuery();
            return set.first();
        }
    }
}
