package service;

import entity.Alien;
import entity.Feedback;
import entity.Movie;
import entity.User;
import javafx.util.Pair;
import service.exception.ServiceException;

import java.util.List;

public interface CommonService {

    User signIn(String username, String password) throws ServiceException;
    void signUp(String username, String firstName, String lastName, String email,
                String password, String confirmedPassword, String birthDate) throws ServiceException;
    List<Alien> viewAllAliens() throws ServiceException;
    List<Movie> viewAllMovies() throws ServiceException;
    Pair<Alien, List<Feedback>> viewAlien(long alienId) throws ServiceException;
    void addFeedback(long alienId, String username, int rating, String feedbackText) throws ServiceException;
    Movie viewMovie(long movieId) throws ServiceException;
    User viewUser(long userId) throws ServiceException;
}
