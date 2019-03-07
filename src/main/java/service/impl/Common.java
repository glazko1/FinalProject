package service.impl;

import dao.AlienDAO;
import dao.EditDAO;
import dao.FeedbackDAO;
import dao.MovieDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.InvalidUsernameOrPasswordException;
import dao.impl.AlienSQL;
import dao.impl.EditSQL;
import dao.impl.FeedbackSQL;
import dao.impl.MovieSQL;
import dao.impl.NotificationSQL;
import dao.impl.UserSQL;
import entity.Alien;
import entity.Edit;
import entity.Feedback;
import entity.Movie;
import entity.Notification;
import entity.User;
import javafx.util.Pair;
import service.CommonService;
import service.exception.BannedUserException;
import service.exception.InvalidPasswordException;
import service.exception.InvalidSignInInformationException;
import service.exception.InvalidSignUpInformationException;
import service.exception.ServiceException;
import util.generator.IdGenerator;
import util.hasher.PasswordHashKeeper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    private static final String USERNAME_FORMAT_REGEX = ".{6,15}";
    private static final String NAME_FORMAT_REGEX = ".{2,30}";
    private static final String EMAIL_FORMAT_REGEX = "[a-z][[a-z][0-9][-][_]]{3,15}[@][a-z]{2,10}[.][a-z]{2,4}";
    private static final String PASSWORD_FORMAT_REGEX = ".{8,30}";
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private FeedbackDAO feedbackDAO = FeedbackSQL.getInstance();
    private EditDAO editDAO = EditSQL.getInstance();
    private NotificationDAO notificationDAO = NotificationSQL.getInstance();
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();
    private IdGenerator generator = IdGenerator.getInstance();

    @Override
    public User signIn(String username, String password) throws ServiceException {
        if (!username.matches(USERNAME_FORMAT_REGEX) ||
                !password.matches(PASSWORD_FORMAT_REGEX)) {
            throw new InvalidSignInInformationException("Information is not valid!");
        }
        try {
            String encoded = keeper.generateHash(username, password);
            User user = userDAO.getUser(username, encoded);
            if (user.isBanned()) {
                throw new BannedUserException("User is banned!");
            }
            return user;
        } catch (InvalidUsernameOrPasswordException e) {
            throw new InvalidSignInInformationException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(String username, String firstName, String lastName, String email,
                       String password, String confirmedPassword, Date birthDate) throws ServiceException {
        if (!username.matches(USERNAME_FORMAT_REGEX) ||
                !firstName.matches(NAME_FORMAT_REGEX) ||
                !lastName.matches(NAME_FORMAT_REGEX) ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
                !password.matches(PASSWORD_FORMAT_REGEX) ||
                !password.equals(confirmedPassword)) {
            throw new InvalidSignUpInformationException("Information is not valid!");
        }
        String encoded = keeper.generateHash(username, password);
        try {
            long id = generator.generateId();
            User user = new User(id, username, firstName, lastName, 4,
                    email, false, new Timestamp(birthDate.getTime() + 11000 * 1000));
            userDAO.addNewUser(user, encoded);
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
            long id = generator.generateId();
            User user = userDAO.getUser(username);
            Alien alien = alienDAO.getAlienById(alienId);
            Timestamp feedbackDateTime = new Timestamp(System.currentTimeMillis());
            Feedback feedback = new Feedback(id, alien, user, rating, feedbackText, feedbackDateTime);
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
        if (!firstName.matches(NAME_FORMAT_REGEX) ||
                !lastName.matches(NAME_FORMAT_REGEX) ||
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
    public void changePassword(long userId, String currentPassword, String newPassword,
                               String confirmedPassword) throws ServiceException {
        if (!currentPassword.matches(PASSWORD_FORMAT_REGEX) ||
                !newPassword.matches(PASSWORD_FORMAT_REGEX) ||
                !confirmedPassword.matches(PASSWORD_FORMAT_REGEX) ||
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
        } catch (InvalidUsernameOrPasswordException e) {
            throw new InvalidPasswordException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException {
        if (!username.matches(USERNAME_FORMAT_REGEX) ||
                !firstName.matches(NAME_FORMAT_REGEX) ||
                !lastName.matches(NAME_FORMAT_REGEX) ||
                !email.matches(EMAIL_FORMAT_REGEX) ||
                !newPassword.matches(PASSWORD_FORMAT_REGEX) ||
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

    @Override
    public void suggestEdit(long userId, long alienId, String description) throws ServiceException {
        long editId = generator.generateId();
        try {
            Alien alien = alienDAO.getAlienById(alienId);
            User user = userDAO.getUser(userId);
            Timestamp editDateTime = new Timestamp(System.currentTimeMillis());
            Edit edit = new Edit(editId, alien, user, description, editDateTime);
            editDAO.addNewEdit(edit);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Notification> viewNotifications(long userId) throws ServiceException {
        try {
            return notificationDAO.getNotificationsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Movie> viewAllMoviesSorted(String sortedBy, String sortType) throws ServiceException {
        try {
            return movieDAO.getAllMoviesSorted(sortedBy, sortType);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    void setAlienDAO(AlienDAO alienDAO) {
        this.alienDAO = alienDAO;
    }

    void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    void setFeedbackDAO(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    void setEditDAO(EditDAO editDAO) {
        this.editDAO = editDAO;
    }

    void setNotificationDAO(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    void setKeeper(PasswordHashKeeper keeper) {
        this.keeper = keeper;
    }

    void setGenerator(IdGenerator generator) {
        this.generator = generator;
    }
}
