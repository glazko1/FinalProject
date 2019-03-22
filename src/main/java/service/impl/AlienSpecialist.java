package service.impl;

import dao.AlienDAO;
import dao.EditDAO;
import dao.MovieDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.exception.alien.UsedAlienNameException;
import dao.impl.AlienSQL;
import dao.impl.EditSQL;
import dao.impl.MovieSQL;
import dao.impl.NotificationSQL;
import dao.impl.UserSQL;
import entity.Alien;
import entity.Edit;
import entity.Movie;
import entity.Notification;
import entity.User;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.exception.alien.InvalidAlienNameException;
import util.builder.AlienBuilder;
import util.builder.NotificationBuilder;
import util.validator.AlienInformationValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlienSpecialist implements AlienSpecialistService {

    private static final AlienSpecialist INSTANCE = new AlienSpecialist();

    public static AlienSpecialist getInstance() {
        return INSTANCE;
    }

    private AlienSpecialist() {}

    private AlienInformationValidator validator = AlienInformationValidator.getInstance();
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private EditDAO editDAO = EditSQL.getInstance();
    private NotificationDAO notificationDAO = NotificationSQL.getInstance();

    /**
     * Performs operation of adding information about new alien. Checks correctness
     * of information by validator and calls appropriate method of alien-specified
     * DAO to put information to database.
     * @param alienName name of new alien.
     * @param planet planet of new alien.
     * @param description description of new alien.
     * @param movieTitle movie about new alien.
     * @param imagePath path to alien's image.
     * @throws InvalidAlienInformationException if information about new alien is
     * not correct.
     * @throws InvalidAlienNameException if alien's name is already in use.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void addAlien(String alienName, String planet, String description, String movieTitle, String imagePath) throws ServiceException {
        if (!validator.validate(alienName, planet, description)) {
            throw new InvalidAlienInformationException("Information is not valid!");
        }
        try {
            Movie movie = movieDAO.getMovieByTitle(movieTitle);
            AlienBuilder alienBuilder = new AlienBuilder();
            Alien alien = alienBuilder.withName(alienName)
                    .fromMovie(movie)
                    .fromPlanet(planet)
                    .hasDescription(description)
                    .withAverageRating(0.0)
                    .withPathToImage(imagePath)
                    .build();
            alienDAO.addNewAlien(alien);
        } catch (UsedAlienNameException e) {
            throw new InvalidAlienNameException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of editing information about specified alien. Calls
     * appropriate method of alien-specified DAO to put information to database.
     * @param alienId ID of alien to edit information about.
     * @param movieTitle movie about alien.
     * @param planet alien's home planet.
     * @param description new description of alien.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void editAlien(String alienId, String movieTitle, String planet, String description) throws ServiceException {
        if (!validator.validate(planet, description)) {
            throw new InvalidAlienInformationException("Information is not valid!");
        }
        try {
            Movie movie = movieDAO.getMovieByTitle(movieTitle);
            String movieId = movie.getMovieId();
            alienDAO.editAlien(alienId, movieId, planet, description);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about all suggested edits. Calls
     * appropriate method of edit-specified DAO and returns list of suggested edits.
     * @return list of suggested edits.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public List<Edit> viewAllSuggestedEdits() throws ServiceException {
        try {
            return editDAO.getAllSuggestedEdits();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of getting information about specified suggested edit
     * (by ID). Calls appropriate method of edit-specified DAO and returns
     * edit with given ID.
     * @param editId ID of edit to get.
     * @return edit with given ID.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public Edit viewSuggestedEdit(String editId) throws ServiceException {
        try {
            return editDAO.getSuggestedEdit(editId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of accepting specified suggested edit (by ID). Calls
     * appropriate method of edit-specified DAO and updates alien's description
     * (by calling method of alien-specified DAO).
     * @param editId ID of edit to accept.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void acceptEdit(String editId) throws ServiceException {
        try {
            Edit edit = editDAO.getSuggestedEdit(editId);
            Alien alien = edit.getAlien();
            alienDAO.updateDescription(alien.getAlienId(), edit.getEditText());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of deleting specified suggested edit (by ID). Calls
     * appropriate method of edit-specified DAO to delete information from
     * database.
     * @param editId ID of edit to delete.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void deleteEdit(String editId) throws ServiceException {
        try {
            editDAO.deleteEdit(editId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of sending notification to specified user (by ID). Calls
     * appropriate method of notification-specified DAO to put information into
     * database.
     * @param userId ID of user to send notification to.
     * @param notificationText text of notification.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void sendNotification(String userId, String notificationText) throws ServiceException {
        try {
            User user = userDAO.getUser(userId);
            Timestamp notificationDateTime = new Timestamp(System.currentTimeMillis());
            NotificationBuilder builder = new NotificationBuilder();
            Notification notification = builder.toUser(user)
                    .withText(notificationText)
                    .withNotificationDateTime(notificationDateTime)
                    .build();
            notificationDAO.addNewNotification(notification);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of sending notification to all users. Calls appropriate
     * method of notification-specified DAO to put information into database.
     * @param notificationText text of notification.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void sendNotificationToAll(String notificationText) throws ServiceException {
        try {
            List<User> users = userDAO.getAllUsers();
            List<Notification> notifications = new ArrayList<>();
            Timestamp notificationDateTime = new Timestamp(System.currentTimeMillis());
            users.forEach(u -> {
                NotificationBuilder builder = new NotificationBuilder();
                Notification notification = builder.toUser(u)
                        .withText(notificationText)
                        .withNotificationDateTime(notificationDateTime)
                        .build();
                notifications.add(notification);
            });
            for (Notification notification : notifications) {
                notificationDAO.addNewNotification(notification);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Performs operation of deleting specified alien (by ID). Calls appropriate
     * method of alien-specified DAO to delete information from database.
     * @param alienId ID of alien to delete.
     * @throws ServiceException if {@link DAOException} was caught.
     */
    @Override
    public void deleteAlien(String alienId) throws ServiceException {
        try {
            alienDAO.deleteAlien(alienId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void setValidator(AlienInformationValidator validator) {
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

    void setEditDAO(EditDAO editDAO) {
        this.editDAO = editDAO;
    }

    void setNotificationDAO(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }
}
