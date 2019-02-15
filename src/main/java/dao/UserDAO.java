package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    User getUserById(long id) throws DAOException;
    User getUserByUsernameAndPassword(String username, String password) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    void addNewUser(User user) throws DAOException;
}
