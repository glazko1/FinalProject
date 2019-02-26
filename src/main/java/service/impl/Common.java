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
import util.generator.GeneratorId;
import util.hasher.PasswordHashKeeper;

import java.sql.Date;
import java.sql.Timestamp;
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
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @Override
    public User signIn(String username, String password) throws ServiceException {
        if (username.length() < 6 ||
                password.length() < 8) {
            throw new ServiceException("Information is not valid!");
        }
        try {
            PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
            String encoded = keeper.generateHash(username, password);
            User user = userDAO.getUser(username, encoded);
            if (user.isBanned()) {
                throw new ServiceException("User is banned!");
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(String username, String firstName, String lastName, String email,
                       String password, String confirmedPassword, Date birthDate) throws ServiceException {
        if (username.length() < 6 ||
                firstName.length() < 2 ||
                lastName.length() < 2 ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
                password.length() < 8 ||
                !password.equals(confirmedPassword)) {
            throw new ServiceException("Information is not valid!");
        }
        PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
        String encoded = keeper.generateHash(username, password);
        try {
            GeneratorId generatorId = GeneratorId.getInstance();
            long id = generatorId.generateId();
            User user = new User(id, username, firstName, lastName, encoded, 4,
                    email, false, new Timestamp(birthDate.getTime() + 11000 * 1000));
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
    public Alien viewAlien(long alienId) throws ServiceException {
        try {
            return alienDAO.getAlienById(alienId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Pair<Alien, List<Feedback>> viewAlienWithFeedbacks(long alienId) throws ServiceException {
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
            User user = userDAO.getUser(username);
            Timestamp feedbackDateTime = new Timestamp(System.currentTimeMillis());
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
            return userDAO.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editUser(long userId, String firstName, String lastName, String email) throws ServiceException {
        if (firstName.length() < 2 ||
                lastName.length() < 2 ||
                !email.matches(EMAIL_FORMAT_REGEX)) {
            throw new ServiceException("Information is not valid!");
        }
        try {
            userDAO.editUser(userId, firstName, lastName, email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(long userId, String currentPassword, String newPassword, String confirmedPassword) throws ServiceException {
        if (currentPassword.length() < 8 ||
                newPassword.length() < 8 ||
                confirmedPassword.length() < 8 ||
                !newPassword.equals(confirmedPassword)) {
            throw new ServiceException("Information is not valid!");
        }
        try {
            User user = userDAO.getUser(userId);
            String username = user.getUsername();
            String encodedCurrentPassword = keeper.generateHash(username, currentPassword);
            String encodedNewPassword = keeper.generateHash(username, newPassword);
            userDAO.getUser(username, encodedCurrentPassword);
            userDAO.changePassword(userId, encodedNewPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException {
        if (username.length() < 6 ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
                firstName.length() < 2 ||
                lastName.length() < 2 ||
                newPassword.length() < 8 ||
                !newPassword.equals(confirmedPassword)) {
            throw new ServiceException("Information is not valid!");
        }
        try {
            User user = userDAO.getUser(username, firstName, lastName, email);
            long userId = user.getUserId();
            String encoded = keeper.generateHash(username, newPassword);
            userDAO.changePassword(userId, encoded);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void recountAverageRating(long alienId) throws ServiceException {
        try {
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByAlienId(alienId);
            double averageRating = 0.0;
            if (!feedbacks.isEmpty()) {
                int ratingSum = 0;
                for (Feedback feedback : feedbacks) {
                    ratingSum += feedback.getRating();
                }
                averageRating = (double) ratingSum / feedbacks.size();
            }
            alienDAO.updateAverageRating(alienId, averageRating);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteFeedback(long feedbackId) throws ServiceException {
        try {
            feedbackDAO.deleteFeedback(feedbackId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Alien> viewAllAliensSorted(String sortedBy, String sortType) throws ServiceException {
        try {
            return alienDAO.getAllAliensSorted(sortedBy, sortType);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
