package service;

import entity.User;
import service.exception.ServiceException;

import java.util.List;

public interface AdminService {

    List<User> viewAllUsers() throws ServiceException;
    void changeBanStatus(long userId) throws ServiceException;
    void changeUserStatus(long userId, int statusId) throws ServiceException;
    List<User> viewAllUsersSorted(String sortedBy, String sortType) throws ServiceException;
}
