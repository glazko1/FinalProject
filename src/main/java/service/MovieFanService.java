package service;

import service.exception.ServiceException;

import java.sql.Date;

public interface MovieFanService {

    void addMovie(String title, int runningTime, int budget, Date releaseDate) throws ServiceException;
    void editMovie(long movieId, int runningTime, int budget, Date releaseDate) throws ServiceException;
    void deleteMovie(long movieId) throws ServiceException;
}
