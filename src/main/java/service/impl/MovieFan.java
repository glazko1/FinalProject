package service.impl;

import dao.MovieDAO;
import dao.exception.DAOException;
import dao.exception.movie.UsedMovieTitleException;
import dao.impl.MovieSQL;
import entity.Movie;
import service.MovieFanService;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.exception.movie.InvalidMovieTitleException;
import util.builder.MovieBuilder;
import util.validator.MovieInformationValidator;

import java.sql.Date;
import java.sql.Timestamp;

public class MovieFan implements MovieFanService {

    private static final MovieFan INSTANCE = new MovieFan();

    public static MovieFan getInstance() {
        return INSTANCE;
    }

    private MovieFan() {}

    private MovieInformationValidator validator = MovieInformationValidator.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();

    /**
     * Performs operation of adding information about new movie. Checks correctness
     * of information by validator and calls appropriate method of movie-specified
     * DAO to put information to database.
     * @param title title of new movie.
     * @param runningTime running time of new movie.
     * @param budget budget of new movie.
     * @param releaseDate release date of new movie.
     * @throws InvalidMovieInformationException if information about new movie is
     * not correct.
     * @throws InvalidMovieTitleException if title of new movie is already in use.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void addMovie(String title, String runningTime, String budget, Date releaseDate) throws ServiceException {
        if (!validator.validate(title, runningTime, budget)) {
            throw new InvalidMovieInformationException("Information is not valid!");
        }
        MovieBuilder builder = new MovieBuilder();
        Movie movie = builder.withTitle(title)
                .withRunningTime(Integer.parseInt(runningTime))
                .withBudget(Integer.parseInt(budget))
                .hasReleaseDate(new Timestamp(releaseDate.getTime() + 11000 * 1000))
                .build();
        try {
            movieDAO.addNewMovie(movie);
        } catch (UsedMovieTitleException e) {
            throw new InvalidMovieTitleException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of editing information about specified movie (by ID).
     * Checks correctness of information by validator and calls appropriate method
     * of movie-specified DAO to edit information in database.
     * @param movieId ID movie to edit information about.
     * @param runningTime new running time of movie.
     * @param budget new budget of movie.
     * @param releaseDate new release date of movie.
     * @throws InvalidMovieInformationException if information about new movie is
     * not correct.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void editMovie(String movieId, String runningTime, String budget, Date releaseDate) throws ServiceException {
        if (!validator.validate(runningTime, budget)) {
            throw new InvalidMovieInformationException("Information is not valid!");
        }
        try {
            movieDAO.editMovie(movieId, Integer.parseInt(runningTime), Integer.parseInt(budget), releaseDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of deleting information about specified movie (by ID).
     * Calls appropriate method of movie-specified DAO to delete information from
     * database.
     * @param movieId ID of movie to delete.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void deleteMovie(String movieId) throws ServiceException {
        try {
            movieDAO.deleteMovie(movieId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void setValidator(MovieInformationValidator validator) {
        this.validator = validator;
    }

    void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }
}
