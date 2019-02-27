package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Feedback {

    private long feedbackId;
    private Alien alien;
    private User user;
    private int rating;
    private String feedbackText;
    private Timestamp feedbackDateTime;

    public Feedback(long feedbackId, Alien alien, User user, int rating, String feedbackText, Timestamp feedbackDateTime) {
        this.feedbackId = feedbackId;
        this.alien = alien;
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

    public Alien getAlien() {
        return alien;
    }

    public void setAlien(Alien alien) {
        this.alien = alien;
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

    public String getFeedbackDateTime() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(feedbackDateTime);
    }

    public Timestamp getFeedbackTimestamp() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(Timestamp feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }
}
