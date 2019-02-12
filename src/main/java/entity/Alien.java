package entity;

public class Alien {

    private long alienId;
    private String alienName;
    private int movieId;
    private String planet;
    private String description;
    private double averageRating;

    public Alien(long alienId, String alienName, int movieId, String planet, String description, double averageRating) {
        this.alienId = alienId;
        this.alienName = alienName;
        this.movieId = movieId;
        this.planet = planet;
        this.description = description;
        this.averageRating = averageRating;
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", alienName='" + alienName + '\'' +
                ", movieId=" + movieId +
                ", planet='" + planet + '\'' +
                ", description='" + description + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
