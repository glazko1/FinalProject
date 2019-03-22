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

    /**
     * Performs operation of getting information about all users. Calls
     * appropriate method of user-specified DAO and returns list of users.
     * @return list of users existing in database.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<User> viewAllUsers() throws ServiceException {
        try {
            return userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of changing ban status of specified user (by user's
     * ID). Calls appropriate method of user-specified DAO.
     * @param userId ID of user to change ban status.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void changeBanStatus(String userId) throws ServiceException {
        try {
            userDAO.changeBanStatus(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of changing status of specified user (by user's ID and
     * his new status). Calls appropriate method of user-specified DAO.
     * @param userId ID of user to change status.
     * @param statusId new status of user.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void changeUserStatus(String userId, int statusId) throws ServiceException {
        try {
            userDAO.changeUserStatus(userId, statusId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about all users in specified by
     * parameters (sorted by and type of sorting) order. Calls appropriate method
     * of user-specified DAO.
     * @param sortedBy sorting parameter (ID, username or e-mail).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of users existing in database.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<User> viewAllUsersSorted(String sortedBy, String sortType) throws ServiceException {
        try {
            return userDAO.getAllUsersSorted(sortedBy, sortType);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
