package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    /**
     * Creates and returns user according to some source of information (file,
     * database, etc.) and given parameter (user's ID).
     * @param userId ID of user to find.
     * @return object with information about user with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    User getUser(String userId) throws DAOException;

    /**
     * Creates and returns user according to some source of information (file,
     * database, etc.) and given parameters (username and password).
     * @param username login of user to find.
     * @param password password of user to find.
     * @return object with information about user with given ID.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    User getUser(String username, String password) throws DAOException;

    /**
     * Creates and returns list of users according to some source of information
     * (file, database, etc.)
     * @return list of users.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Adds new user to some source of information (file, database, etc.)
     * according to all fields in given object.
     * @param user object with information about new user.
     * @param encoded user's encoded password.
     * @throws DAOException if error occurred during the process of adding
     * information.
     */
    void addNewUser(User user, String encoded) throws DAOException;

    /**
     * Changes ban status of user (from banned to unbanned and vice versa).
     * @param userId ID of user to change ban status.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void changeBanStatus(String userId) throws DAOException;

    /**
     * Changes status of user (administrator, movie fan, alien specialist and
     * user).
     * @param userId ID of user to change ban status.
     * @param statusId ID of new user's status.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void changeUserStatus(String userId, int statusId) throws DAOException;

    /**
     * Updates information about user with given ID and writes new information
     * into some source (file, database, etc.)
     * @param userId ID of user to edit.
     * @param firstName new user's first name.
     * @param lastName new user's last name.
     * @param email new user's e-mail.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void editUser(String userId, String firstName, String lastName, String email) throws DAOException;

    /**
     * Changes password of user with given ID.
     * @param userId ID of user to change password.
     * @param newPassword new user's password.
     * @throws DAOException if error occurred during the process of changing
     * information.
     */
    void changePassword(String userId, String newPassword) throws DAOException;

    /**
     * Creates and returns user according to some source of information (file,
     * database, etc.) and given parameters (username, first name, last name,
     * and e-mail).
     * @param username user's login.
     * @param firstName user's first name.
     * @param lastName user's last name.
     * @param email user's email.
     * @return object with information about found user.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    User getUser(String username, String firstName, String lastName, String email) throws DAOException;

    /**
     * Creates and returns list of users according to some source of information
     * (file, database, etc.) sorted by given parameter ascending or descending.
     * @param sortedBy sorting parameter.
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of users.
     * @throws DAOException if error occurred during the process of getting
     * information.
     */
    List<User> getAllUsersSorted(String sortedBy, String sortType) throws DAOException;
}
