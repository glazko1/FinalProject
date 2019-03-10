package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Movie {

    private long movieId;
    private String title;
    private int runningTime;
    private int budget;
    private Timestamp releaseDate;

    public Movie(long movieId) {
        this.movieId = movieId;
    }

    public Movie(long movieId, String title, int runningTime, int budget, Timestamp releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.runningTime = runningTime;
        this.budget = budget;
        this.releaseDate = releaseDate;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getReleaseDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(releaseDate);
    }

    public Timestamp getReleaseDateTimestamp() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return movieId == movie.movieId &&
                runningTime == movie.runningTime &&
                budget == movie.budget &&
                Objects.equals(title, movie.title) &&
                Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, title, runningTime, budget, releaseDate);
    }
}
