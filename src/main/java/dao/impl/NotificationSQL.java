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

    private static final String GET_NOTIFICATIONS_BY_USER_ID_SQL = "SELECT n.NotificationId, u.UserId, u.Username, u.FirstName, u.LastName, u.StatusId, u.Email, u.Banned, u.BirthDate, n.NotificationText, n.NotificationDateTime FROM Notification n JOIN User u ON n.UserId = u.UserId WHERE n.UserId = ?";
    private static final String ADD_NEW_NOTIFICATION_SQL = "INSERT INTO Notification (NotificationId, UserId, NotificationText, NotificationDateTime) VALUES (?, ?, ?, ?)";
    private DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public List<Notification> getNotificationsByUserId(long userId) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NOTIFICATIONS_BY_USER_ID_SQL);
            statement.setLong(1, userId);
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

    @Override
    public void addNewNotification(Notification notification) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection()) {
            Connection connection = proxyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_NOTIFICATION_SQL);
            statement.setLong(1, notification.getNotificationId());
            User user = notification.getUser();
            statement.setLong(2, user.getUserId());
            statement.setString(3, notification.getNotificationText());
            statement.setTimestamp(4, notification.getNotificationTimestamp());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Notification getNextNotification(ResultSet set) throws SQLException {
        int statusId = set.getInt(6) - 1;
        UserStatus status = UserStatus.values()[statusId];
        UserBuilder userBuilder = new UserBuilder(set.getLong(2));
        User user = userBuilder.withUsername(set.getString(3))
                .withFirstName(set.getString(4))
                .withLastName(set.getString(5))
                .withStatus(status)
                .hasEmail(set.getString(7))
                .isBanned(set.getBoolean(8))
                .hasBirthDate(set.getTimestamp(9))
                .build();
        NotificationBuilder notificationBuilder = new NotificationBuilder(set.getLong(1));
        return notificationBuilder.toUser(user)
                .withText(set.getString(10))
                .withNotificationDateTime(set.getTimestamp(11))
                .build();
    }
}
