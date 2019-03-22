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

    User signIn(String username, String password) throws ServiceException;
    void signUp(String username, String firstName, String lastName, String email,
                String password, String confirmedPassword, Date birthDate) throws ServiceException;
    List<Alien> viewAllAliens() throws ServiceException;
    List<Movie> viewAllMovies() throws ServiceException;
    Alien viewAlien(String alienId) throws ServiceException;
    Pair<Alien, List<Feedback>> viewAlienWithFeedbacks(String alienId) throws ServiceException;
    void addFeedback(String alienId, String userId, int rating, String feedbackText) throws ServiceException;
    Movie viewMovie(String movieId) throws ServiceException;
    User viewUser(String userId) throws ServiceException;
    void editUser(String userId, String firstName, String lastName, String email) throws ServiceException;
    void changePassword(String userId, String currentPassword, String newPassword, String confirmedPassword) throws ServiceException;
    void restorePassword(String username, String firstName, String lastName, String email, String newPassword, String confirmedPassword) throws ServiceException;
    void recountAverageRating(String alienId) throws ServiceException;
    void deleteFeedback(String feedbackId) throws ServiceException;
    List<Alien> viewAllAliensSorted(String sortedBy, String sortType) throws ServiceException;
    void suggestEdit(String userId, String alienId, String description) throws ServiceException;
    List<Notification> viewNotifications(String userId) throws ServiceException;
    List<Movie> viewAllMoviesSorted(String sortedBy, String sortType) throws ServiceException;
    void reviewUserStatus(String userId) throws ServiceException;
}
