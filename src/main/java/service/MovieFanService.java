package service;

import com.google.protobuf.ServiceException;

import java.sql.Date;

public interface MovieFanService {

    void addMovie(String title, int runningTime, int budget, Date releaseDate) throws ServiceException;
}
