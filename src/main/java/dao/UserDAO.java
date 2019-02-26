package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    User getUser(long id) throws DAOException;
    User getUser(String username, String password) throws DAOException;
    User getUser(String username) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    void addNewUser(User user) throws DAOException;
    void changeBanStatus(long id) throws DAOException;
    void changeUserStatus(long id, int statusId) throws DAOException;
    void editUser(long id, String firstName, String lastName, String email) throws DAOException;
    void changePassword(long id, String newPassword) throws DAOException;
    User getUser(String username, String firstName, String lastName, String email) throws DAOException;
}
