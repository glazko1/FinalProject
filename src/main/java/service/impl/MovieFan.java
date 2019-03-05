package service.impl;

import dao.MovieDAO;
import dao.exception.DAOException;
import dao.impl.MovieSQL;
import entity.Movie;
import service.MovieFanService;
import service.exception.ServiceException;
import util.generator.GeneratorId;

import java.sql.Date;
import java.sql.Timestamp;

public class MovieFan implements MovieFanService {

    private static final MovieFan INSTANCE = new MovieFan();

    public static MovieFan getInstance() {
        return INSTANCE;
    }

    private MovieFan() {}

    private MovieDAO movieDAO = MovieSQL.getInstance();
    private GeneratorId generator = GeneratorId.getInstance();

    @Override
    public void addMovie(String title, int runningTime, int budget, Date releaseDate) throws ServiceException {
        long movieId = generator.generateId();
        Movie movie = new Movie(movieId, title, runningTime, budget, new Timestamp(releaseDate.getTime() + 11000 * 1000));
        try {
            movieDAO.addNewMovie(movie);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editMovie(long movieId, int runningTime, int budget, Date releaseDate) throws ServiceException {
        try {
            movieDAO.editMovie(movieId, runningTime, budget, releaseDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    void setGenerator(GeneratorId generator) {
        this.generator = generator;
    }
}
