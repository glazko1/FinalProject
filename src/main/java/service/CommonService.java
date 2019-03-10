package service;

import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.Notification;
import entity.User;
import javafx.util.Pair;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidPasswordException;
import service.exception.ServiceException;
import service.exception.user.InvalidUserInformationException;

import java.sql.Date;
import java.util.List;

public interface CommonService {

    User signIn(String username, String password) throws ServiceException;
    void signUp(String username, String firstName, String lastName, String email,
                String password, String confirmedPassword, Date birthDate) throws ServiceException;
    List<Alien> viewAllAliens() throws ServiceException;
    List<Movie> viewAllMovies() throws ServiceException;
    Alien viewAlien(long alienId) throws ServiceException;
    Pair<Alien, List<Feedback>> viewAlienWithFeedbacks(long alienId) throws ServiceException;
    void addFeedback(long alienId, String username, int rating, String feedbackText) throws ServiceException;
    Movie viewMovie(long movieId) throws ServiceException;
    User viewUser(long userId) throws ServiceException;
    void editUser(long userId, String firstName, String lastName, String email) throws ServiceException;
    void changePassword(long userId, String currentPassword, String newPassword, String confirmedPassword) throws ServiceException;
    void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException;
    void recountAverageRating(long alienId) throws ServiceException;
    void deleteFeedback(long feedbackId) throws ServiceException;
    List<Alien> viewAllAliensSorted(String sortedBy, String sortType) throws ServiceException;
    void suggestEdit(long userId, long alienId, String description) throws ServiceException;
    List<Notification> viewNotifications(long userId) throws ServiceException;
    List<Movie> viewAllMoviesSorted(String sortedBy, String sortType) throws ServiceException;
}
