package util.builder;

import entity.Notification;
import entity.User;

import java.sql.Timestamp;

public class NotificationBuilder {

    private String notificationId;
    private User user;
    private String notificationText;
    private Timestamp notificationDateTime;

    public NotificationBuilder() {
        this("");
    }

    public NotificationBuilder(String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Sets user according to given parameter.
     * @param user user who'll receive notification.
     * @return current builder.
     */
    public NotificationBuilder toUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets text of notification according to given parameter.
     * @param notificationText text of notification.
     * @return current builder.
     */
    public NotificationBuilder withText(String notificationText) {
        this.notificationText = notificationText;
        return this;
    }

    /**
     * Sets date and time according to given parameter.
     * @param notificationDateTime date and time of notification.
     * @return current builder.
     */
    public NotificationBuilder withNotificationDateTime(Timestamp notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
        return this;
    }

    /**
     * Builds and returns {@link Notification} object in accordance with set
     * earlier parameters (user, notification text, date and time).
     * @return object with information about new notification.
     */
    public Notification build() {
        Notification notification = new Notification(notificationId);
        notification.setUser(user);
        notification.setNotificationText(notificationText);
        notification.setNotificationDateTime(notificationDateTime);
        return notification;
    }

    User getUser() {
        return user;
    }

    String getNotificationText() {
        return notificationText;
    }

    Timestamp getNotificationDateTime() {
        return notificationDateTime;
    }
}
