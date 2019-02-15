package service;

import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommonService {

    void signIn(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void signUp(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewAliens(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewMovies(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
