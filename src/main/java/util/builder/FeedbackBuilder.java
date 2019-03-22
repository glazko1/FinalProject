package util.builder;

import entity.Alien;
import entity.Feedback;
import entity.User;

import java.sql.Timestamp;

public class FeedbackBuilder {

    private String feedbackId;
    private Alien alien;
    private User user;
    private int rating;
    private String feedbackText;
    private Timestamp feedbackDateTime;

    public FeedbackBuilder() {
        this("");
    }

    public FeedbackBuilder(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public FeedbackBuilder aboutAlien(Alien alien) {
        this.alien = alien;
        return this;
    }

    public FeedbackBuilder leftByUser(User user) {
        this.user = user;
        return this;
    }

    public FeedbackBuilder withRating(int rating) {
        this.rating = rating;
        return this;
    }

    public FeedbackBuilder withText(String feedbackText) {
        this.feedbackText = feedbackText;
        return this;
    }

    public FeedbackBuilder withFeedbackDateTime(Timestamp feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
        return this;
    }

    public Feedback build() {
        Feedback feedback = new Feedback(feedbackId);
        feedback.setAlien(alien);
        feedback.setUser(user);
        feedback.setRating(rating);
        feedback.setFeedbackText(feedbackText);
        feedback.setFeedbackDateTime(feedbackDateTime);
        return feedback;
    }
}
