package service;

import service.exception.ServiceException;

import java.sql.Date;

public interface MovieFanService {

    /**
     * Performs operation of adding information about new movie.
     * @param title title of new movie.
     * @param runningTime running time of new movie.
     * @param budget budget of new movie.
     * @param releaseDate release date of new movie.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void addMovie(String title, String runningTime, String budget, Date releaseDate) throws ServiceException;

    /**
     * Performs operation of editing information about specified movie (by ID).
     * @param movieId ID movie to edit information about.
     * @param runningTime new running time of movie.
     * @param budget new budget of movie.
     * @param releaseDate new release date of movie.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void editMovie(String movieId, String runningTime, String budget, Date releaseDate) throws ServiceException;
}
