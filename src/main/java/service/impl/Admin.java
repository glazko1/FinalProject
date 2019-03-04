package service.impl;

import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserSQL;
import entity.User;
import service.AdminService;
import service.exception.ServiceException;

import java.util.List;

public class Admin implements AdminService {

    private static final Admin INSTANCE = new Admin();

    public static Admin getInstance() {
        return INSTANCE;
    }

    private Admin() {}

    private UserDAO userDAO = UserSQL.getInstance();

    @Override
    public List<User> viewAllUsers() throws ServiceException {
        try {
            return userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeBanStatus(long userId) throws ServiceException {
        try {
            userDAO.changeBanStatus(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeUserStatus(long userId, int statusId) throws ServiceException {
        try {
            userDAO.changeUserStatus(userId, statusId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
