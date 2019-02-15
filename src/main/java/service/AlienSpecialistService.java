package service;

import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AlienSpecialistService {

    void addAlien(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
