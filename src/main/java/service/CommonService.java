package service;

import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommonService {

    void signIn(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void signUp(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewAllAliens(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewAllMovies(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewAlien(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void addFeedback(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
    void viewMovie(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
