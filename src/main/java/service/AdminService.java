package service;

import entity.User;
import service.exception.ServiceException;

import java.util.List;

public interface AdminService {

    /**
     * Performs operation of getting information about all users.
     * @return list of users.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<User> viewAllUsers() throws ServiceException;

    /**
     * Performs operation of changing ban status of specified user (by ID).
     * @param userId ID of user to change ban status.
     * @throws ServiceException if error occurred during the process of chaning
     * information.
     */
    void changeBanStatus(String userId) throws ServiceException;

    /**
     * Performs operation of changing user's status according to given
     * parameters (user's ID and new status).
     * @param userId ID of user to change status.
     * @param statusId new status of user.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void changeUserStatus(String userId, int statusId) throws ServiceException;

    /**
     * Performs operation of getting information about all users sorted in
     * accordance with given parameters.
     * @param sortedBy sorting parameter (ID, username or e-mail).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of users.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<User> viewAllUsersSorted(String sortedBy, String sortType) throws ServiceException;

    /**
     * Performs operation of deleting specified alien (by ID).
     * @param alienId ID of alien to delete.
     * @throws ServiceException if error occurred during the process of deleting
     * information.
     */
    void deleteAlien(String alienId) throws ServiceException;

    /**
     * Performs operation of deleting specified movie (by ID).
     * @param movieId ID of movie to delete.
     * @throws ServiceException if error occurred during the process of deleting
     * information.
     */
    void deleteMovie(String movieId) throws ServiceException;
}
