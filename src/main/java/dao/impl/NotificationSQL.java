package dao.impl;

import connection.ProxyConnection;
import dao.NotificationDAO;
import dao.exception.DAOException;
import entity.Notification;
import entity.User;
import entity.UserStatus;
import pool.DatabaseConnectionPool;
import util.builder.NotificationBuilder;
import util.builder.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationSQL implements NotificationDAO {

    private static final NotificationSQL INSTANCE = new NotificationSQL();

    public static NotificationSQL getInstance() {
        return INSTANCE;
    }

    private NotificationSQL() {}

    private static final String GET_NOTIFICATIONS_BY_USER_ID_SQL = "SELECT n.NotificationId, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, n.NotificationText, n.NotificationDateTime FROM Notification n JOIN User u ON n.UserId = u.UserId WHERE n.UserId = ? ORDER BY n.NotificationDateTime DESC";
    private static final String ADD_NEW_NOTIFICATION_SQL = "INSERT INTO Notification (NotificationId, UserId, NotificationText, NotificationDateTime) VALUES (UUID(), ?, ?, ?)";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    /**
     * Creates and returns list of notifications sent to specified user (user's ID is
     * given as parameter). Gets proxy connection from database pool, prepares statement
     * on it (by SQL-string) and gets result set with all notifications to this user.
     * @param userId ID of user to find notifications.
     * @return list of notifications sent to specified user.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public List<Notification> getNotificationsByUserId(String userId) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NOTIFICATIONS_BY_USER_ID_SQL);
            statement.setString(1, userId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Notification notification = getNextNotification(set);
                notifications.add(notification);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return notifications;
    }

    /**
     * Adds new notification to database according to all fields in given object. Gets
     * proxy connection from database pool, prepares statement on it (by SQL-string)
     * and executes.
     * @param notification object with information about new notification.
     * @throws DAOException if {@link SQLException} was caught.
     */
    @Override
    public void addNewNotification(Notification notification) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_NOTIFICATION_SQL);
            User user = notification.getUser();
            statement.setString(1, user.getUserId());
            statement.setString(2, notification.getNotificationText());
            statement.setTimestamp(3, notification.getNotificationTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Notification getNextNotification(ResultSet set) throws SQLException {
        int statusId = set.getInt(6) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder userBuilder = new UserBuilder(set.getString(2));
        User user = userBuilder.withUsername(set.getString(3))
                .withFirstName(set.getString(4))
                .withLastName(set.getString(5))
                .withStatus(status)
                .hasEmail(set.getString(7))
                .isBanned(set.getBoolean(8))
                .hasBirthDate(set.getTimestamp(9))
                .build();
        NotificationBuilder notificationBuilder = new NotificationBuilder(set.getString(1));
        return notificationBuilder.toUser(user)
                .withText(set.getString(10))
                .withNotificationDateTime(set.getTimestamp(11))
                .build();
    }
}
