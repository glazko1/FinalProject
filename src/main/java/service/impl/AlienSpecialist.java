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
import util.generator.IdGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlienSpecialist implements AlienSpecialistService {

    private static final AlienSpecialist INSTANCE = new AlienSpecialist();

    public static AlienSpecialist getInstance() {
        return INSTANCE;
    }

    private AlienSpecialist() {}

    private static final String ALIEN_NAME_FORMAT_REGEX = ".{2,30}";
    private static final String PLANET_FORMAT_REGEX = ".{4,20}";
    private static final String DESCRIPTION_FORMAT_REGEX = ".{1,255}";
    private UserDAO userDAO = UserSQL.getInstance();
    private AlienDAO alienDAO = AlienSQL.getInstance();
    private MovieDAO movieDAO = MovieSQL.getInstance();
    private EditDAO editDAO = EditSQL.getInstance();
    private NotificationDAO notificationDAO = NotificationSQL.getInstance();
    private IdGenerator generator = IdGenerator.getInstance();

    @Override
    public void addAlien(long id, String alienName, String planet, String description, String movieTitle, String imagePath) throws ServiceException {
        if (!alienName.matches(ALIEN_NAME_FORMAT_REGEX) ||
                !planet.matches(PLANET_FORMAT_REGEX) ||
                !description.matches(DESCRIPTION_FORMAT_REGEX)) {
            throw new InvalidAlienInformationException("Information is not valid!");
        }
        try {
            Movie movie = movieDAO.getMovieByTitle(movieTitle);
            AlienBuilder alienBuilder = new AlienBuilder(id);
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

    @Override
    public void editAlien(long alienId, String movieTitle, String planet, String description) throws ServiceException {
        if (!planet.matches(PLANET_FORMAT_REGEX) ||
                !description.matches(DESCRIPTION_FORMAT_REGEX)) {
            throw new InvalidAlienInformationException("Information is not valid!");
        }
        try {
            Movie movie = movieDAO.getMovieByTitle(movieTitle);
            long movieId = movie.getMovieId();
            alienDAO.editAlien(alienId, movieId, planet, description);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Edit> viewAllSuggestedEdits() throws ServiceException {
        try {
            return editDAO.getAllSuggestedEdits();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Edit viewSuggestedEdit(long editId) throws ServiceException {
        try {
            return editDAO.getSuggestedEdit(editId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void acceptEdit(long editId) throws ServiceException {
        try {
            Edit edit = editDAO.getSuggestedEdit(editId);
            Alien alien = edit.getAlien();
            alienDAO.updateDescription(alien.getAlienId(), edit.getEditText());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEdit(long editId) throws ServiceException {
        try {
            editDAO.deleteEdit(editId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendNotification(long userId, String notificationText) throws ServiceException {
        try {
            User user = userDAO.getUser(userId);
            long notificationId = generator.generateId();
            Timestamp notificationDateTime = new Timestamp(System.currentTimeMillis());
            Notification notification = new Notification(notificationId, user, notificationText, notificationDateTime);
            notificationDAO.addNewNotification(notification);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendNotificationToAll(String notificationText) throws ServiceException {
        try {
            List<User> users = userDAO.getAllUsers();
            List<Notification> notifications = new ArrayList<>();
            Timestamp notificationDateTime = new Timestamp(System.currentTimeMillis());
            users.forEach(u -> {
                long notificationId = generator.generateId();
                notifications.add(new Notification(notificationId, u, notificationText, notificationDateTime));
            });
            for (Notification notification : notifications) {
                notificationDAO.addNewNotification(notification);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAlien(long alienId) throws ServiceException {
        try {
            alienDAO.deleteAlien(alienId);
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

    void setEditDAO(EditDAO editDAO) {
        this.editDAO = editDAO;
    }

    void setNotificationDAO(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }
}
