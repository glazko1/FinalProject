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
import util.generator.IdGenerator;
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
    private IdGenerator generator = IdGenerator.getInstance();

    @Override
    public void addMovie(String title, String runningTime, String budget, Date releaseDate) throws ServiceException {
        if (!validator.validate(title, runningTime, budget)) {
            throw new InvalidMovieInformationException("Information is not valid!");
        }
        long movieId = generator.generateId();
        MovieBuilder builder = new MovieBuilder(movieId);
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

    @Override
    public void editMovie(long movieId, String runningTime, String budget, Date releaseDate) throws ServiceException {
        if (!validator.validate(runningTime, budget)) {
            throw new InvalidMovieInformationException("Information is not valid!");
        }
        try {
            movieDAO.editMovie(movieId, Integer.parseInt(runningTime), Integer.parseInt(budget), releaseDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteMovie(long movieId) throws ServiceException {
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

    void setGenerator(IdGenerator generator) {
        this.generator = generator;
    }
}
