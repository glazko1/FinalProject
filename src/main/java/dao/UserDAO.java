package dao;

import dao.exception.DAOException;
import entity.User;

import java.util.List;

public interface UserDAO {

    User getUserById(long id) throws DAOException;
    User getUserByUsername(String username) throws DAOException;
    List<User> getAllUsers();
    void addNewUser(User user) throws DAOException;
}
