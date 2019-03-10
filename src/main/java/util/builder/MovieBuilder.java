package util.builder;

import entity.Movie;

import java.sql.Timestamp;

public class MovieBuilder {

    private long movieId;
    private String title;
    private int runningTime;
    private int budget;
    private Timestamp releaseDate;

    public MovieBuilder(long movieId) {
        this.movieId = movieId;
    }

    public MovieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder withRunningTime(int runningTime) {
        this.runningTime = runningTime;
        return this;
    }

    public MovieBuilder withBudget(int budget) {
        this.budget = budget;
        return this;
    }

    public MovieBuilder hasReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Movie build() {
        Movie movie = new Movie(movieId);
        movie.setTitle(title);
        movie.setRunningTime(runningTime);
        movie.setBudget(budget);
        movie.setReleaseDate(releaseDate);
        return movie;
    }
}
