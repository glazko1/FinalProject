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

    /**
     * Sets alien according to given parameter.
     * @param alien alien about which feedback is.
     * @return current builder.
     */
    public FeedbackBuilder aboutAlien(Alien alien) {
        this.alien = alien;
        return this;
    }

    /**
     * Sets user according to given parameter.
     * @param user user who left this feedback.
     * @return current builder.
     */
    public FeedbackBuilder leftByUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets rating according to given parameter.
     * @param rating rating set by user.
     * @return current builder.
     */
    public FeedbackBuilder withRating(int rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Sets text of feedback according to given parameter.
     * @param feedbackText text of feedback.
     * @return current builder.
     */
    public FeedbackBuilder withText(String feedbackText) {
        this.feedbackText = feedbackText;
        return this;
    }

    /**
     * Sets date and time according to given parameter.
     * @param feedbackDateTime date and time of feedback.
     * @return current builder.
     */
    public FeedbackBuilder withFeedbackDateTime(Timestamp feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
        return this;
    }

    /**
     * Builds and returns {@link Feedback} object in accordance with set earlier
     * parameters (alien, user, rating, feedback text, date and time).
     * @return object with information about new feedback.
     */
    public Feedback build() {
        Feedback feedback = new Feedback(feedbackId);
        feedback.setAlien(alien);
        feedback.setUser(user);
        feedback.setRating(rating);
        feedback.setFeedbackText(feedbackText);
        feedback.setFeedbackDateTime(feedbackDateTime);
        return feedback;
    }

    Alien getAlien() {
        return alien;
    }

    User getUser() {
        return user;
    }

    int getRating() {
        return rating;
    }

    String getFeedbackText() {
        return feedbackText;
    }

    Timestamp getFeedbackDateTime() {
        return feedbackDateTime;
    }
}
