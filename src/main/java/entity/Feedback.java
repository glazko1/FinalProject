package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Feedback {

    private long feedbackId;
    private long alienId;
    private User user;
    private int rating;
    private String feedbackText;
    private Timestamp feedbackDateTime;

    public Feedback(long feedbackId, long alienId, User user, int rating, String feedbackText, Timestamp feedbackDateTime) {
        this.feedbackId = feedbackId;
        this.alienId = alienId;
        this.user = user;
        this.rating = rating;
        this.feedbackText = feedbackText;
        this.feedbackDateTime = feedbackDateTime;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public long getAlienId() {
        return alienId;
    }

    public void setAlienId(long alienId) {
        this.alienId = alienId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Timestamp getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(Timestamp feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }
}
