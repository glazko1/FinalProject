package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Notification {

    private long notificationId;
    private User user;
    private String notificationText;
    private Timestamp notificationDateTime;

    public Notification(long notificationId, User user, String notificationText, Timestamp notificationDateTime) {
        this.notificationId = notificationId;
        this.user = user;
        this.notificationText = notificationText;
        this.notificationDateTime = notificationDateTime;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationDateTime() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(notificationDateTime);
    }

    public Timestamp getNotificationTimestamp() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(Timestamp notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }
}
