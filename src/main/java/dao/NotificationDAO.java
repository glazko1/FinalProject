package dao;

import dao.exception.DAOException;
import entity.Notification;

import java.util.List;

public interface NotificationDAO {

    List<Notification> getNotificationsByUserId(long userId) throws DAOException;
    void addNewNotification(Notification notification) throws DAOException;
}
