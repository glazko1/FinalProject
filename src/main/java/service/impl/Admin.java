package service.impl;

import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserSQL;
import entity.User;
import service.AdminService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Admin implements AdminService {

    private static final Admin INSTANCE = new Admin();

    public static Admin getInstance() {
        return INSTANCE;
    }

    private Admin() {}

    private UserDAO userDAO = UserSQL.getInstance();

    @Override
    public void viewAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
