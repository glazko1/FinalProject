package util.builder;

import entity.Alien;
import entity.Movie;

public class AlienBuilder {

    private long alienId;
    private String alienName;
    private Movie movie;
    private String planet;
    private String description;
    private double averageRating;
    private String imagePath;

    public AlienBuilder(long alienId) {
        this.alienId = alienId;
    }

    public AlienBuilder withName(String alienName) {
        this.alienName = alienName;
        return this;
    }

    public AlienBuilder fromMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public AlienBuilder fromPlanet(String planet) {
        this.planet = planet;
        return this;
    }

    public AlienBuilder hasDescription(String description) {
        this.description = description;
        return this;
    }

    public AlienBuilder withAverageRating(double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public AlienBuilder withPathToImage(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Alien build() {
        Alien alien = new Alien(alienId);
        alien.setAlienName(alienName);
        alien.setMovie(movie);
        alien.setPlanet(planet);
        alien.setDescription(description);
        alien.setAverageRating(averageRating);
        alien.setImagePath(imagePath);
        return alien;
    }
}
