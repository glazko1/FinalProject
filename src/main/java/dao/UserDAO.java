package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    User getUser(String userId) throws DAOException;
    User getUser(String username, String password) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    void addNewUser(User user, String encoded) throws DAOException;
    void changeBanStatus(String userId) throws DAOException;
    void changeUserStatus(String userId, int statusId) throws DAOException;
    void editUser(String userId, String firstName, String lastName, String email) throws DAOException;
    void changePassword(String userId, String newPassword) throws DAOException;
    User getUser(String username, String firstName, String lastName, String email) throws DAOException;
    List<User> getAllUsersSorted(String sortedBy, String sortType) throws DAOException;
}
