package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Notification {

    private long notificationId;
    private User user;
    private String notificationText;
    private Timestamp notificationDateTime;

    public Notification(long notificationId) {
        this.notificationId = notificationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification that = (Notification) o;
        return notificationId == that.notificationId &&
                Objects.equals(user, that.user) &&
                Objects.equals(notificationText, that.notificationText) &&
                Objects.equals(notificationDateTime, that.notificationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, user, notificationText, notificationDateTime);
    }
}
