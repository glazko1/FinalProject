package util.builder;

import entity.Movie;

import java.sql.Timestamp;

public class MovieBuilder {

    private String movieId;
    private String title;
    private int runningTime;
    private int budget;
    private Timestamp releaseDate;

    public MovieBuilder() {
        this("");
    }

    public MovieBuilder(String movieId) {
        this.movieId = movieId;
    }

    /**
     * Sets title according to given parameter.
     * @param title title of movie.
     * @return current builder.
     */
    public MovieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets running time according to given parameter.
     * @param runningTime running time of movie.
     * @return current builder.
     */
    public MovieBuilder withRunningTime(int runningTime) {
        this.runningTime = runningTime;
        return this;
    }

    /**
     * Sets budget according to given parameter.
     * @param budget budget of movie.
     * @return current builder.
     */
    public MovieBuilder withBudget(int budget) {
        this.budget = budget;
        return this;
    }

    /**
     * Sets release date according to given parameter.
     * @param releaseDate date on which movie was released.
     * @return current builder.
     */
    public MovieBuilder hasReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * Builds and returns {@link Movie} object in accordance with set earlier
     * parameters (title, running time, budget and release date).
     * @return object with information about new movie.
     */
    public Movie build() {
        Movie movie = new Movie(movieId);
        movie.setTitle(title);
        movie.setRunningTime(runningTime);
        movie.setBudget(budget);
        movie.setReleaseDate(releaseDate);
        return movie;
    }

    String getTitle() {
        return title;
    }

    int getRunningTime() {
        return runningTime;
    }

    int getBudget() {
        return budget;
    }

    Timestamp getReleaseDate() {
        return releaseDate;
    }
}
