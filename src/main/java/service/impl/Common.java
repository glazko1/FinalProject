package service.impl;

import dao.AlienDAO;
import dao.MovieDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.AlienSQL;
import dao.impl.MovieSQL;
import dao.impl.UserSQL;
import entity.Alien;
import entity.Movie;
import entity.User;
import service.CommonService;
import service.exception.ServiceException;
import util.hasher.PasswordHashKeeper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Random;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();

    @Override
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
            String encoded = keeper.generateHash(password);
            User user = userDAO.getUserByUsernameAndPassword(username, encoded);
            response.setHeader("username", user.getUsername());
            response.setIntHeader("status", user.getStatusId());
            response.setHeader("firstName", user.getFirstName());
            response.setHeader("lastName", user.getLastName());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String username = request.getParameter("newUsername");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("newPassword");
        String confirmedPassword = request.getParameter("confirmedPassword");
        if (username.length() < 6 ||
                firstName.length() < 2 ||
                lastName.length() < 2 ||
                password.length() < 8 ||
                !password.equals(confirmedPassword)) {
            throw new ServiceException("Information is not valid!");
        }
        PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
        String encoded = keeper.generateHash(password);
        try {
            Random random = new Random();
            long id = Math.abs(random.nextLong());
            User user = new User(id, username, firstName, lastName, encoded, 4,
                    request.getParameter("email"), false, Date.valueOf("2000-01-01"));
            userDAO.addNewUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewAliens(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            List<Alien> aliens = alienDAO.getAllAliens();
            request.setAttribute("aliens", aliens);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void viewMovies(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
