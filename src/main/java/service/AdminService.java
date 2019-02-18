package service;

import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminService {

    void viewAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
