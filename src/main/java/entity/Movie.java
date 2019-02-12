package entity;

import java.util.Date;

public class Movie {

    private long movieId;
    private String title;
    private int runningTime;
    private int budget;
    private Date releaseDate;

    public Movie(long movieId, String title, int runningTime, int budget, Date releaseDate) {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
