package util.builder;

import entity.Alien;
import entity.Movie;

public class AlienBuilder {

    private String alienId;
    private String alienName;
    private Movie movie;
    private String planet;
    private String description;
    private double averageRating;
    private String imagePath;

    public AlienBuilder() {
        this("");
    }

    public AlienBuilder(String alienId) {
        this.alienId = alienId;
    }

    /**
     * Sets alien's name according to given parameter.
     * @param alienName alien's name.
     * @return current builder.
     */
    public AlienBuilder withName(String alienName) {
        this.alienName = alienName;
        return this;
    }

    /**
     * Sets movie according to given parameter.
     * @param movie movie where alien was.
     * @return current builder.
     */
    public AlienBuilder fromMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    /**
     * Sets planet according to given parameter.
     * @param planet home planet of alien.
     * @return current builder.
     */
    public AlienBuilder fromPlanet(String planet) {
        this.planet = planet;
        return this;
    }

    /**
     * Sets description of alien according to given parameter.
     * @param description description of alien.
     * @return current builder.
     */
    public AlienBuilder hasDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets alien's average rating according to given parameter.
     * @param averageRating alien's average rating.
     * @return current builder.
     */
    public AlienBuilder withAverageRating(double averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    /**
     * Sets path to alien's image according to given parameter.
     * @param imagePath path to alien's image
     * @return current builder.
     */
    public AlienBuilder withPathToImage(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    /**
     * Builds and returns {@link Alien} object in accordance with set earlier
     * parameters (name, movie, planet, description, average rating and path to
     * image).
     * @return object with information about new alien.
     */
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

    String getAlienName() {
        return alienName;
    }

    Movie getMovie() {
        return movie;
    }

    String getPlanet() {
        return planet;
    }

    String getDescription() {
        return description;
    }

    double getAverageRating() {
        return averageRating;
    }

    String getImagePath() {
        return imagePath;
    }
}
