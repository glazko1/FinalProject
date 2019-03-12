package service;

import service.exception.ServiceException;

import java.sql.Date;

public interface MovieFanService {

    void addMovie(String title, String runningTime, String budget, Date releaseDate) throws ServiceException;
    void editMovie(long movieId, String runningTime, String budget, Date releaseDate) throws ServiceException;
    void deleteMovie(long movieId) throws ServiceException;
}
