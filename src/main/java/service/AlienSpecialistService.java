package service;

import service.exception.ServiceException;

public interface AlienSpecialistService {

    void addAlien(String alienName, String planet, String description, String movieTitle) throws ServiceException;
    void editAlien(long alienId, String movieTitle, String planet, String description) throws ServiceException;
}
