package dao;

import dao.exception.DAOException;
import entity.Notification;

import java.util.List;

public interface NotificationDAO {

    /**
     * Creates and returns list of notifications sent to specified user according
     * to some source of information (file, database, etc.) and given parameter
     * (user's ID).
     * @param userId ID of user to find notifications.
     * @return list of notifications sent to user.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Notification> getNotificationsByUserId(String userId) throws DAOException;

    /**
     * Adds new notification to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param notification object with information about new notification.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewNotification(Notification notification) throws DAOException;

    /**
     * Deletes notification with given ID from some source of information
     * (file, database, etc.)
     * @param notificationId ID of notification to delete.
     * @throws DAOException if error occurred during the process of deleting
     * information.
     */
    void deleteNotification(String notificationId) throws DAOException;
}
