package dao;

import dao.exception.DAOException;
import entity.Movie;

import java.sql.Date;
import java.util.List;

public interface MovieDAO {

    Movie getMovieById(long movieId) throws DAOException;
    Movie getMovieByTitle(String title) throws DAOException;
    List<Movie> getAllMovies() throws DAOException;
    void addNewMovie(Movie movie) throws DAOException;
    List<Movie> getAllMoviesSorted(String sortedBy, String sortType) throws DAOException;
    void editMovie(long movieId, int runningTime, int budget, Date releaseDate) throws DAOException;
    void deleteMovie(long movieId) throws DAOException;
}
