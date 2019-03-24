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
import service.exception.edit.InvalidEditInformationException;
import service.exception.feedback.InvalidFeedbackInformationException;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidEmailException;
import service.exception.user.InvalidPasswordException;
import service.exception.user.InvalidUserInformationException;
import service.exception.user.InvalidUsernameException;
import util.builder.EditBuilder;
import util.builder.FeedbackBuilder;
import util.builder.UserBuilder;
import util.hasher.PasswordHashKeeper;
import util.validator.EditInformationValidator;
import util.validator.FeedbackInformationValidator;
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

    private UserInformationValidator userValidator = UserInformationValidator.getInstance();
    private FeedbackInformationValidator feedbackValidator = FeedbackInformationValidator.getInstance();
    private EditInformationValidator editValidator = EditInformationValidator.getInstance();
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private FeedbackDAO feedbackDAO = FeedbackSQL.getInstance();
    private EditDAO editDAO = EditSQL.getInstance();
    private NotificationDAO notificationDAO = NotificationSQL.getInstance();
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    /**
     * Performs operation of signing user into the system. Entered by user
     * information (username and password) is checked by validator, then
     * hasher encodes password and method of user-specified DAO is called to get
     * information about user and accept or reject his actions.
     * @param username entered username by user.
     * @param password entered password by user.
     * @return object with information about logged in user.
     * @throws InvalidUserInformationException if information entered by user is
     * not valid.
     * @throws BannedUserException if user is banned.
     * @throws InvalidUserInformationException if username or password is not
     * correct.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public User signIn(String username, String password) throws ServiceException {
        if (!userValidator.validate(username, password)) {
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

    /**
     * Performs operation of signing user up. Entered by user information
     * (username, first name, last name, e-mail, password and confirmed password)
     * is checked by validator then hasher encodes password and appropriate method
     * of user-specified DAO is called to put information into database.
     * @param username login of new user.
     * @param firstName first name of new user.
     * @param lastName last name of new user.
     * @param email e-mail of new user.
     * @param password new user's password.
     * @param confirmedPassword new user's confirmed password.
     * @param birthDate new user's birth date.
     * @throws InvalidUserInformationException if information entered by user is
     * not valid.
     * @throws InvalidUsernameException if username is already in use.
     * @throws InvalidEmailException if e-mail is already in use.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void signUp(String username, String firstName, String lastName, String email,
                       String password, String confirmedPassword, Date birthDate) throws ServiceException {
        if (!userValidator.validate(username, firstName, lastName, email, password, confirmedPassword)) {
            throw new InvalidUserInformationException("Information is not valid!");
        }
        UserBuilder builder = new UserBuilder();
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

    /**
     * Performs operation of getting information about all aliens. Calls
     * appropriate method of alien-specified DAO and returns list of aliens.
     * @return list of aliens existing in database.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Alien> viewAllAliens() throws ServiceException {
        try {
            return alienDAO.getAllAliens();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about all movies. Calls
     * appropriate method of movie-specified DAO and returns list of movies.
     * @return list of movies existing in database.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Movie> viewAllMovies() throws ServiceException {
        try {
            return movieDAO.getAllMovies();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about specified (by ID) alien.
     * Calls appropriate method of alien-specified DAO and returns object with
     * information about alien with given ID.
     * @param alienId ID of alien to get.
     * @return object with information about alien with given ID.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public Alien viewAlien(String alienId) throws ServiceException {
        try {
            return alienDAO.getAlienById(alienId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about specified (by ID) alien
     * with all feedbacks about him. Calls appropriate method of alien-specified
     * DAO and returns a pair of information about alien with given ID and all
     * feedbacks about him.
     * @param alienId ID of alien to get information with feedbacks.
     * @return pair of alien and feedbacks.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public Pair<Alien, List<Feedback>> viewAlienWithFeedbacks(String alienId) throws ServiceException {
        try {
            Alien alien = alienDAO.getAlienById(alienId);
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByAlienId(alienId);
            return new Pair<>(alien, feedbacks);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of adding new feedback about specified (by ID) alien.
     * Checks correctness of information by validator and calls appropriate method
     * of feedback-specified DAO to put information to database.
     * @param alienId ID of alien to add feedback about.
     * @param userId ID of user feedback is left by.
     * @param rating alien's rating set by user.
     * @param feedbackText text of feedback.
     * @throws InvalidFeedbackInformationException if information entered by user
     * is incorrect.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void addFeedback(String alienId, String userId, int rating, String feedbackText) throws ServiceException {
        if (!feedbackValidator.validate(feedbackText)) {
            throw new InvalidFeedbackInformationException("Information is not valid!");
        }
        try {
            User user = userDAO.getUser(userId);
            Alien alien = alienDAO.getAlienById(alienId);
            Timestamp feedbackDateTime = new Timestamp(System.currentTimeMillis());
            FeedbackBuilder builder = new FeedbackBuilder();
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

    /**
     * Performs operation of getting information about specified (by ID) movie.
     * Calls appropriate method of movie-specified DAO and returns object with
     * information about movie with given ID.
     * @param movieId ID of movie to get.
     * @return object with information about movie with given ID.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public Movie viewMovie(String movieId) throws ServiceException {
        try {
            return movieDAO.getMovieById(movieId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about specified (by ID) user.
     * Calls appropriate method of user-specified DAO and returns object with
     * information about user with given ID.
     * @param userId ID of user to get.
     * @return object with information about user with given ID.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public User viewUser(String userId) throws ServiceException {
        try {
            return userDAO.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of editing information about specified user. Checks
     * correctness of information by validator and calls appropriate method of
     * user-specified DAO to put information to database.
     * @param userId ID of user to edit information about.
     * @param firstName new user's first name.
     * @param lastName new user's last name.
     * @param email new user's e-mail.
     * @throws InvalidUserInformationException if information entered by user
     * is incorrect.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void editUser(String userId, String firstName, String lastName, String email) throws ServiceException {
        if (!userValidator.validate(firstName, lastName, email)) {
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

    /**
     * Performs operation of changing user's password. Checks correctness of
     * information by validator, then checks validity of current password
     * entered by user and calls appropriate method of user-specified DAO to
     * put information to database.
     * @param userId ID of user to change password.
     * @param currentPassword current password (information is entered by user).
     * @param newPassword new password.
     * @param confirmedPassword confirmed new password.
     * @throws InvalidUserInformationException if information entered by user is
     * incorrect.
     * @throws InvalidPasswordException if entered by user current password is
     * not valid.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void changePassword(String userId, String currentPassword, String newPassword,
                               String confirmedPassword) throws ServiceException {
        if (!userValidator.validatePasswords(currentPassword, newPassword, confirmedPassword)) {
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

    /**
     * Performs operation of restoring user's password. Checks correctness of
     * information by validator, then checks validity of entered information
     * (all information entered by user should be correct) and calls appropriate
     * method of user-specified DAO to put information to database.
     * @param username username entered by user.
     * @param firstName first name entered by user.
     * @param lastName last name entered by user.
     * @param email e-mail entered by user.
     * @param newPassword new user's password.
     * @param confirmedPassword confirmed password.
     * @throws InvalidUserInformationException if information entered by user is
     * incorrect.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException {
        if (!userValidator.validate(username, firstName, lastName, email, newPassword, confirmedPassword)) {
            throw new InvalidUserInformationException("Information is not valid!");
        }
        try {
            User user = userDAO.getUser(username, firstName, lastName, email);
            String userId = user.getUserId();
            String encoded = keeper.generateHash(username, newPassword);
            userDAO.changePassword(userId, encoded);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of recounting alien's average rating in accordance
     * with all feedbacks about him. Gets all feedbacks from feedback-specified
     * DAO, recounts average rating and calls appropriate method of alien-
     * specified DAO to put information to database.
     * @param alienId ID of alien to update average rating.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void recountAverageRating(String alienId) throws ServiceException {
        try {
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByAlienId(alienId);
            int ratingSum = feedbacks.stream()
                    .mapToInt(Feedback::getRating)
                    .sum();
            double averageRating = 0.0;
            if (!feedbacks.isEmpty()) {
                averageRating = (double) ratingSum / feedbacks.size();
            }
            alienDAO.updateAverageRating(alienId, averageRating);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of deleting specified feedback (by ID). Calls
     * appropriate method of feedback-specified DAO to delete information from
     * database.
     * @param feedbackId ID of feedback to delete.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void deleteFeedback(String feedbackId) throws ServiceException {
        try {
            feedbackDAO.deleteFeedback(feedbackId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about all aliens in specified
     * by parameters (sorted by and type of sorting) order. Calls appropriate
     * method of alien-specified DAO and returns sorted list of aliens.
     * @param sortedBy sorting parameter (ID, name, movie title or planet).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of aliens.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Alien> viewAllAliensSorted(String sortedBy, String sortType) throws ServiceException {
        try {
            return alienDAO.getAllAliensSorted(sortedBy, sortType);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of adding new suggested edit. Checks correctness of
     * information by validator and calls appropriate method of edit-specified
     * DAO to put information to database.
     * @param userId ID of user that suggested edit.
     * @param alienId ID of alien edit is about.
     * @param description suggested description.
     * @throws InvalidEditInformationException if information entered by user is
     * incorrect.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void suggestEdit(String userId, String alienId, String description) throws ServiceException {
        if (!editValidator.validate(description)) {
            throw new InvalidEditInformationException("Information is not valid!");
        }
        try {
            Alien alien = alienDAO.getAlienById(alienId);
            User user = userDAO.getUser(userId);
            Timestamp editDateTime = new Timestamp(System.currentTimeMillis());
            EditBuilder builder = new EditBuilder();
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

    /**
     * Performs operation of getting information about all notifications sent to
     * specified user. Calls appropriate method of notification-specified DAO
     * and returns object with information about notifications.
     * @param userId ID of user to get notifications.
     * @return list of notifications to user with given ID.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Notification> viewNotifications(String userId) throws ServiceException {
        try {
            return notificationDAO.getNotificationsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about all movies in specified by
     * parameters (sorted by and type of sorting) order. Calls appropriate method
     * of movie-specified DAO and returns list of movies.
     * @param sortedBy sorting parameter (ID, movie title or running time).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of movies.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Movie> viewAllMoviesSorted(String sortedBy, String sortType) throws ServiceException {
        try {
            return movieDAO.getAllMoviesSorted(sortedBy, sortType);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of reviewing user's status. User's status changes from
     * user to alien specialist if user left 10 or more feedbacks and all
     * ratings he left differ from alien's average ratings by not more than 0,2.
     * User's status changes from alien specialist to user if there're less than
     * 10 his feedbacks in database or number of incorrect feedbacks (his rating
     * to alien differs from alien's average rating by more than 0.2) is more or
     * equal to the fifth part of number of his feedbacks.
     * Calls appropriate method of user-specified DAO and puts information to
     * database.
     * @param userId ID of user to review status.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void reviewUserStatus(String userId) throws ServiceException {
        try {
            List<Feedback> feedbacks = feedbackDAO.getFeedbacksByUserId(userId);
            int allFeedbacks = feedbacks.size();
            int incorrectFeedbacks = (int) feedbacks.stream()
                    .filter(f -> Math.abs(f.getRating() - f.getAlien().getAverageRating()) > 0.2)
                    .count();
            User user = userDAO.getUser(userId);
            if (user.getStatus() == UserStatus.USER &&
                    allFeedbacks >= 10 &&
                    incorrectFeedbacks == 0) {
                userDAO.changeUserStatus(userId, 3);
            } else if (user.getStatus() == UserStatus.ALIEN_SPECIALIST &&
                    (allFeedbacks < 10 ||
                            incorrectFeedbacks >= allFeedbacks * 0.2)) {
                userDAO.changeUserStatus(userId, 4);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of deleting specified notification (by ID). Calls
     * appropriate method of notification-specified DAO to delete information
     * from database.
     * @param notificationId ID of notification to delete.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void deleteNotification(String notificationId) throws ServiceException {
        try {
            notificationDAO.deleteNotification(notificationId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    void setUserValidator(UserInformationValidator userValidator) {
        this.userValidator = userValidator;
    }

    void setFeedbackValidator(FeedbackInformationValidator feedbackValidator) {
        this.feedbackValidator = feedbackValidator;
    }

    void setEditValidator(EditInformationValidator editValidator) {
        this.editValidator = editValidator;
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
}
