package service.impl;

import dao.AlienDAO;
import dao.FeedbackDAO;
import dao.MovieDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.AlienSQL;
import dao.impl.FeedbackSQL;
import dao.impl.MovieSQL;
import dao.impl.UserSQL;
import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.User;
import javafx.util.Pair;
import service.CommonService;
import service.exception.ServiceException;
import util.hasher.PasswordHashKeeper;

import java.sql.Date;
import java.util.List;
import java.util.Random;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    private static final String EMAIL_FORMAT_REGEX = "[a-z][[a-z][0-9][-][_]]{3,15}[@][a-z]{2,10}[.][a-z]{2,4}";
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private FeedbackDAO feedbackDAO = FeedbackSQL.getInstance();

    @Override
    public User signIn(String username, String password) throws ServiceException {
        try {
            PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
            String encoded = keeper.generateHash(password);
            return userDAO.getUserByUsernameAndPassword(username, encoded);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(String username, String firstName, String lastName, String email,
                       String password, String confirmedPassword, String birthDate) throws ServiceException {
        if (username.length() < 6 ||
                firstName.length() < 2 ||
                lastName.length() < 2 ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
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
                    email, false, Date.valueOf(birthDate));
            userDAO.addNewUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Alien> viewAllAliens() throws ServiceException {
        try {
            return alienDAO.getAllAliens();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Movie> viewAllMovies() throws ServiceException {
        try {
            return movieDAO.getAllMovies();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Alien, List<Feedback>> viewAlien(long alienId) throws ServiceException {
        try {
            Alien alien = alienDAO.getAlienById(alienId);
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByAlienId(alienId);
            return new Pair<>(alien, feedbacks);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addFeedback(long alienId, String username, int rating, String feedbackText) throws ServiceException {
        try {
            Random random = new Random();
            long id = Math.abs(random.nextLong());
            User user = userDAO.getUserByUsername(username);
            Date feedbackDateTime = new Date(System.currentTimeMillis());
            Feedback feedback = new Feedback(id, alienId, user, rating, feedbackText, feedbackDateTime);
            feedbackDAO.addNewFeedback(feedback);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Movie viewMovie(long movieId) throws ServiceException {
        try {
            return movieDAO.getMovieById(movieId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User viewUser(long userId) throws ServiceException {
        try {
            return userDAO.getUserById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
