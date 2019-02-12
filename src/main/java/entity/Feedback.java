package entity;

import java.util.Date;

public class Feedback {

    private long feedbackId;
    private long alienId;
    private long userId;
    private int rating;
    private String feedbackText;
    private Date feedbackDateTime;

    public Feedback(long feedbackId, long alienId, long userId, int rating, String feedbackText, Date feedbackDateTime) {
        this.feedbackId = feedbackId;
        this.alienId = alienId;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public Date getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(Date feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }
}
