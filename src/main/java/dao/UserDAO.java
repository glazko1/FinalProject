package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    User getUser(long userId) throws DAOException;
    User getUser(String username, String password) throws DAOException;
    User getUser(String username) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    void addNewUser(User user) throws DAOException;
    void changeBanStatus(long userId) throws DAOException;
    void changeUserStatus(long userId, int statusId) throws DAOException;
    void editUser(long userId, String firstName, String lastName, String email) throws DAOException;
    void changePassword(long userId, String newPassword) throws DAOException;
    User getUser(String username, String firstName, String lastName, String email) throws DAOException;
}
