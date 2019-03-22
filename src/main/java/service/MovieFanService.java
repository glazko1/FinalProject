package service;

import service.exception.ServiceException;

import java.sql.Date;

public interface MovieFanService {

    void addMovie(String title, String runningTime, String budget, Date releaseDate) throws ServiceException;
    void editMovie(String movieId, String runningTime, String budget, Date releaseDate) throws ServiceException;
    void deleteMovie(String movieId) throws ServiceException;
}
