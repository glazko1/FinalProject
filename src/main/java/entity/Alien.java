package entity;

import java.util.Objects;

public class Alien {

    private long alienId;
    private String alienName;
    private Movie movie;
    private String planet;
    private String description;
    private double averageRating;
    private String imagePath;

    public Alien(long alienId, String alienName, Movie movie, String planet, String description, double averageRating, String imagePath) {
        this.alienId = alienId;
        this.alienName = alienName;
        this.movie = movie;
        this.planet = planet;
        this.description = description;
        this.averageRating = averageRating;
        this.imagePath = imagePath;
    }

    public long getAlienId() {
        return alienId;
    }

    public void setAlienId(long alienId) {
        this.alienId = alienId;
    }

    public String getAlienName() {
        return alienName;
    }

    public void setAlienName(String alienName) {
        this.alienName = alienName;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageRating() {
        return (double) Math.round(100 * averageRating) / 100;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alien alien = (Alien) o;
        return alienId == alien.alienId &&
                Double.compare(alien.averageRating, averageRating) == 0 &&
                Objects.equals(alienName, alien.alienName) &&
                Objects.equals(movie, alien.movie) &&
                Objects.equals(planet, alien.planet) &&
                Objects.equals(description, alien.description) &&
                Objects.equals(imagePath, alien.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alienId, alienName, movie, planet, description, averageRating, imagePath);
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", alienName='" + alienName + '\'' +
                ", movie=" + movie +
                ", planet='" + planet + '\'' +
                ", description='" + description + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
