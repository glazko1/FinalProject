package service;

import service.exception.ServiceException;

public interface AlienSpecialistService {

    void addAlien(String alienName, String planet, String description, String title) throws ServiceException;
}
