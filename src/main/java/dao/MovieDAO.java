package dao;

import dao.exception.DAOException;
import entity.Movie;

import java.util.List;

public interface MovieDAO {

    Movie getMovieById(long movieId) throws DAOException;
    Movie getMovieByTitle(String title) throws DAOException;
    List<Movie> getAllMovies() throws DAOException;
    void addNewMovie(Movie movie) throws DAOException;
}
