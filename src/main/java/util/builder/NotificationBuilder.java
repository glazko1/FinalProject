package util.builder;

import entity.Notification;
import entity.User;

import java.sql.Timestamp;

public class NotificationBuilder {

    private long notificationId;
    private User user;
    private String notificationText;
    private Timestamp notificationDateTime;

    public NotificationBuilder(long notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationBuilder toUser(User user) {
        this.user = user;
        return this;
    }

    public NotificationBuilder withText(String notificationText) {
        this.notificationText = notificationText;
        return this;
    }

    public NotificationBuilder withNotificationDateTime(Timestamp notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
        return this;
    }

    public Notification build() {
        Notification notification = new Notification(notificationId);
        notification.setUser(user);
        notification.setNotificationText(notificationText);
        notification.setNotificationDateTime(notificationDateTime);
        return notification;
    }
}
