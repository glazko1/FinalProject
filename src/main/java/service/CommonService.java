package service;

import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.Notification;
import entity.User;
import javafx.util.Pair;
import service.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface CommonService {

    /**
     * Performs operation of signing user into the system.
     * @param username entered username by user.
     * @param password entered password by user.
     * @return object with information about logged in user.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    User signIn(String username, String password) throws ServiceException;

    /**
     * Performs operation of signing user up.
     * @param username login of new user.
     * @param firstName first name of new user.
     * @param lastName last name of new user.
     * @param email e-mail of new user.
     * @param password new user's password.
     * @param confirmedPassword new user's confirmed password.
     * @param birthDate new user's birth date.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void signUp(String username, String firstName, String lastName, String email,
                String password, String confirmedPassword, Date birthDate) throws ServiceException;

    /**
     * Performs operation of getting information about all aliens.
     * @return list of aliens.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Alien> viewAllAliens() throws ServiceException;

    /**
     * Performs operation of getting information about all movies.
     * @return list of movies.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Movie> viewAllMovies() throws ServiceException;

    /**
     * Performs operation of getting information about specified (by ID) alien.
     * @param alienId ID of alien to get.
     * @return object with information about alien with given ID.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    Alien viewAlien(String alienId) throws ServiceException;

    /**
     * Performs operation of getting information about specified (by ID) alien
     * with all feedbacks about him.
     * @param alienId ID of alien to get information with feedbacks.
     * @return pair of alien and feedbacks.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    Pair<Alien, List<Feedback>> viewAlienWithFeedbacks(String alienId) throws ServiceException;

    /**
     * Performs operation of adding new feedback about specified (by ID) alien.
     * @param alienId ID of alien to add feedback about.
     * @param userId ID of user feedback is left by.
     * @param rating alien's rating set by user.
     * @param feedbackText text of feedback.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void addFeedback(String alienId, String userId, int rating, String feedbackText) throws ServiceException;

    /**
     * Performs operation of getting information about specified (by ID) movie.
     * @param movieId ID of movie to get.
     * @return object with information about movie with given ID.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    Movie viewMovie(String movieId) throws ServiceException;

    /**
     * Performs operation of getting information about specified (by ID) user.
     * @param userId ID of user to get.
     * @return object with information about user with given ID.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    User viewUser(String userId) throws ServiceException;

    /**
     * Performs operation of editing information about specified user.
     * @param userId ID of user to edit information about.
     * @param firstName new user's first name.
     * @param lastName new user's last name.
     * @param email new user's e-mail.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void editUser(String userId, String firstName, String lastName, String email) throws ServiceException;

    /**
     * Performs operation of changing user's password.
     * @param userId ID of user to change password.
     * @param currentPassword current password (information is entered by user).
     * @param newPassword new password.
     * @param confirmedPassword confirmed new password.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void changePassword(String userId, String currentPassword, String newPassword, String confirmedPassword) throws ServiceException;

    /**
     * Performs operation of restoring user's password.
     * @param username username entered by user.
     * @param firstName first name entered by user.
     * @param lastName last name entered by user.
     * @param email e-mail entered by user.
     * @param newPassword new user's password.
     * @param confirmedPassword confirmed password.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException;

    /**
     * Performs operation of recounting alien's average rating in accordance
     * with all feedbacks about him.
     * @param alienId ID of alien to update average rating.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void recountAverageRating(String alienId) throws ServiceException;

    /**
     * Performs operation of deleting specified feedback (by ID).
     * @param feedbackId ID of feedback to delete.
     * @throws ServiceException if error occurred during the process of deleting
     * information.
     */
    void deleteFeedback(String feedbackId) throws ServiceException;

    /**
     * Performs operation of getting information about all aliens in specified
     * by parameters order.
     * @param sortedBy sorting parameter (ID, name, movie title or planet).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of aliens.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Alien> viewAllAliensSorted(String sortedBy, String sortType) throws ServiceException;

    /**
     * Performs operation of adding new suggested edit.
     * @param userId ID of user that suggested edit.
     * @param alienId ID of alien edit is about.
     * @param description suggested description.
     * @throws ServiceException if error occurred during the process of adding
     * information.
     */
    void suggestEdit(String userId, String alienId, String description) throws ServiceException;

    /**
     * Performs operation of getting information about all notifications sent to
     * specified user.
     * @param userId ID of user to get notifications.
     * @return list of notifications to user with given ID.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Notification> viewNotifications(String userId) throws ServiceException;

    /**
     * Performs operation of getting information about all movies in specified by
     * parameters order.
     * @param sortedBy sorting parameter (ID, movie title or running time).
     * @param sortType type of sorting (ascending/descending).
     * @return sorted list of movies.
     * @throws ServiceException if error occurred during the process of getting
     * information.
     */
    List<Movie> viewAllMoviesSorted(String sortedBy, String sortType) throws ServiceException;

    /**
     * Performs operation of reviewing user's status.
     * @param userId ID of user to review status.
     * @throws ServiceException if error occurred during the process of changing
     * information.
     */
    void reviewUserStatus(String userId) throws ServiceException;

    /**
     * Performs operation of deleting specified notification (by ID).
     * @param notificationId ID of notification to delete.
     * @throws ServiceException if error occurred during the process of deleting
     * information.
     */
    void deleteNotification(String notificationId) throws ServiceException;
}
