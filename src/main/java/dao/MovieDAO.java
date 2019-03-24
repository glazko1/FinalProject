package dao;

import dao.exception.DAOException;
import entity.Movie;

import java.sql.Date;
import java.util.List;

public interface MovieDAO {

    /**
     * Creates and returns movie according to some source of information (file,
     * database, etc.) and given parameter (movie ID).
     * @param movieId ID of movie to find.
     * @return object with information about movie with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    Movie getMovieById(String movieId) throws DAOException;

    /**
     * Creates and returns movie according to some source of information (file,
     * database, etc.) and given parameter (movie title).
     * @param title title of movie to find.
     * @return object with information about movie with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    Movie getMovieByTitle(String title) throws DAOException;

    /**
     * Creates and returns list of movies according to some source of information
     * (file, database, etc.)
     * @return list of movies.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Movie> getAllMovies() throws DAOException;

    /**
     * Adds new movie to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param movie object with information about new movie.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewMovie(Movie movie) throws DAOException;

    /**
     * Creates and returns list of movies according to some source of information
     * (file, database, etc.) sorted by given parameter ascending or descending.
     * @param sortedBy sorting parameter.
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of movies.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<Movie> getAllMoviesSorted(String sortedBy, String sortType) throws DAOException;

    /**
     * Updates information about movie with given ID and writes new information
     * into some source (file, database, etc.)
     * @param movieId ID of movie to edit.
     * @param runningTime new value of running time.
     * @param budget new value of budget.
     * @param releaseDate new release date.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void editMovie(String movieId, int runningTime, int budget, Date releaseDate) throws DAOException;

    /**
     * Deletes movie with given ID from some source of information (file,
     * database, etc.)
     * @param movieId ID of movie to delete.
     * @throws DAOException if error occurred during the process of deleting
     * information.
     */
    void deleteMovie(String movieId) throws DAOException;
}
