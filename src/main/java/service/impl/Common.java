package service.impl;

import dao.AlienDAO;
import dao.EditDAO;
import dao.FeedbackDAO;
import dao.MovieDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.user.InvalidUsernameOrPasswordException;
import dao.exception.user.UsedEmailException;
import dao.exception.user.UsedUsernameException;
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
import entity.UserStatus;
import javafx.util.Pair;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidEmailException;
import service.exception.user.InvalidPasswordException;
import service.exception.user.InvalidUserInformationException;
import service.exception.user.InvalidUsernameException;
import util.builder.EditBuilder;
import util.builder.FeedbackBuilder;
import util.builder.UserBuilder;
import util.generator.IdGenerator;
import util.hasher.PasswordHashKeeper;
import util.validator.UserInformationValidator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Common implements CommonService {

    private static final Common INSTANCE = new Common();

    public static Common getInstance() {
        return INSTANCE;
    }

    private Common() {}

    private UserInformationValidator validator = UserInformationValidator.getInstance();
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
        if (!validator.validate(username, password)) {
            throw new InvalidUserInformationException("Information is not valid!");
        }
        String encoded = keeper.generateHash(username, password);
        try {
            User user = userDAO.getUser(username, encoded);
            if (user.isBanned()) {
                throw new BannedUserException("User is banned!");
            }
            return user;
        } catch (InvalidUsernameOrPasswordException e) {
            throw new InvalidUserInformationException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(String username, String firstName, String lastName, String email,
                       String password, String confirmedPassword, Date birthDate) throws ServiceException {
        if (!validator.validate(username, firstName, lastName, email, password, confirmedPassword)) {
            throw new InvalidUserInformationException("Information is not valid!");
        }
        long id = generator.generateId();
        UserBuilder builder = new UserBuilder(id);
        User user = builder.withUsername(username)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withStatus(UserStatus.USER)
                .hasEmail(email)
                .isBanned(false)
                .hasBirthDate(new Timestamp(birthDate.getTime() + 11000 * 1000))
                .build();
        String encoded = keeper.generateHash(username, password);
        try {
            userDAO.addNewUser(user, encoded);
        } catch (UsedUsernameException e) {
            throw new InvalidUsernameException(e);
        } catch (UsedEmailException e) {
            throw new InvalidEmailException(e);
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
        long id = generator.generateId();
        try {
            User user = userDAO.getUser(username);
            Alien alien = alienDAO.getAlienById(alienId);
            Timestamp feedbackDateTime = new Timestamp(System.currentTimeMillis());
            FeedbackBuilder builder = new FeedbackBuilder(id);
            Feedback feedback = builder.aboutAlien(alien)
                    .leftByUser(user)
                    .withRating(rating)
                    .withText(feedbackText)
                    .withFeedbackDateTime(feedbackDateTime)
                    .build();
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
        if (!validator.validate(firstName, lastName, email)) {
            throw new InvalidUserInformationException("Information is not valid!");
        }
        try {
            userDAO.editUser(userId, firstName, lastName, email);
        } catch (UsedEmailException e) {
            throw new InvalidEmailException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(long userId, String currentPassword, String newPassword,
                               String confirmedPassword) throws ServiceException {
        if (!validator.validatePasswords(currentPassword, newPassword, confirmedPassword)) {
            throw new InvalidUserInformationException("Information is not valid!");
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
        if (!validator.validate(username, firstName, lastName, email, newPassword, confirmedPassword)) {
            throw new InvalidUserInformationException("Information is not valid!");
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
            EditBuilder builder = new EditBuilder(editId);
            Edit edit = builder.aboutAlien(alien)
                    .suggestedBy(user)
                    .withText(description)
                    .withEditDateTime(editDateTime)
                    .build();
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

    public void setValidator(UserInformationValidator validator) {
        this.validator = validator;
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
