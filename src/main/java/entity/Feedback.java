package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Feedback {

    private String feedbackId;
    private Alien alien;
    private User user;
    private int rating;
    private String feedbackText;
    private Timestamp feedbackDateTime;

    public Feedback(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return rating == feedback.rating &&
                Objects.equals(feedbackId, feedback.feedbackId) &&
                Objects.equals(alien, feedback.alien) &&
                Objects.equals(user, feedback.user) &&
                Objects.equals(feedbackText, feedback.feedbackText) &&
                Objects.equals(feedbackDateTime, feedback.feedbackDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, alien, user, rating, feedbackText, feedbackDateTime);
    }
}
