package service.impl;

import dao.AlienDAO;
import dao.EditDAO;
import dao.FeedbackDAO;
import dao.MovieDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import dao.exception.DAOException;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;
import util.generator.IdGenerator;
import util.hasher.PasswordHashKeeper;

import java.sql.Date;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CommonTest {

    private Common service = Common.getInstance();
    private UserDAO userDAO = mock(UserSQL.class);
    private AlienDAO alienDAO = mock(AlienSQL.class);
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private FeedbackDAO feedbackDAO = mock(FeedbackSQL.class);
    private EditDAO editDAO = mock(EditSQL.class);
    private NotificationDAO notificationDAO = mock(NotificationSQL.class);
    private IdGenerator idGenerator = mock(IdGenerator.class);
    private PasswordHashKeeper keeper = mock(PasswordHashKeeper.class);

    @BeforeClass
    public void init() {
        service.setUserDAO(userDAO);
        service.setAlienDAO(alienDAO);
        service.setMovieDAO(movieDAO);
        service.setFeedbackDAO(feedbackDAO);
        service.setEditDAO(editDAO);
        service.setNotificationDAO(notificationDAO);
        service.setGenerator(idGenerator);
        service.setKeeper(keeper);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getUser(anyString(), anyString());
        service.signIn("Username", "Password");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_shortUsername_ServiceException() throws ServiceException {
        //given
        //when
        service.signIn("User", "Password");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_shortPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.signIn("Username", "Short");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_bannedUser_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        when(user.isBanned()).thenReturn(true);
        service.signIn("Username", "Password");
        //then
        //expecting ServiceException
    }

    @Test
    public void signIn_validParameters_user() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        when(user.isBanned()).thenReturn(false);
        User result = service.signIn("Username", "Password");
        //then
        assertEquals(result, user);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_shortUsername_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("User", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_shortFirstName_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("Username", "F", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_shortLastName_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("Username", "First", "L",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_invalidEmail_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("Username", "First", "Last",
                "invalid@email,ru", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_shortPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("Username", "First", "Last",
                "invalid@email,ru", "Short", "Short",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_passwordsDoNotMatch_ServiceException() throws ServiceException {
        //given
        //when
        service.signUp("Username", "First", "Last",
                "invalid@email,ru", "Password", "OtherPassword",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test
    public void signUp_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllAliens_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAllAliens();
        service.viewAllAliens();
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllAliens_validParameters_alienList() throws DAOException, ServiceException {
        //given
        List<Alien> aliens = (List<Alien>) mock(List.class);
        //when
        doReturn(aliens).when(alienDAO).getAllAliens();
        List<Alien> result = service.viewAllAliens();
        //then
        assertEquals(result, aliens);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllMovies_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).getAllMovies();
        service.viewAllMovies();
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllMovies_validParameters_movieList() throws DAOException, ServiceException {
        //given
        List<Movie> movies = (List<Movie>) mock(List.class);
        //when
        doReturn(movies).when(movieDAO).getAllMovies();
        List<Movie> result = service.viewAllMovies();
        //then
        assertEquals(result, movies);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAlien_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyLong());
        service.viewAlien(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAlien_validParameters_alien() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyLong());
        Alien result = service.viewAlien(anyLong());
        //then
        assertEquals(result, alien);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAlienWithFeedbacks_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyLong());
        service.viewAlienWithFeedbacks(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAlienWithFeedbacks_validParameters_alien() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Pair<Alien, List<Feedback>> pair = new Pair<>(alien, feedbacks);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyLong());
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByAlienId(anyLong());
        Pair<Alien, List<Feedback>> result = service.viewAlienWithFeedbacks(anyLong());
        //then
        assertEquals(result, pair);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addFeedback_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).getUser(anyString());
        service.addFeedback(1, "Username", 5, "Text");
        //then
        //expecting ServiceException
    }

    @Test
    public void addFeedback_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        Alien alien = mock(Alien.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString());
        doReturn(alien).when(alienDAO).getAlienById(anyLong());
        doNothing().when(feedbackDAO).addNewFeedback(any(Feedback.class));
        service.addFeedback(1, "Username", 5, "Text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewMovie_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).getMovieById(anyLong());
        service.viewMovie(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewMovie_validParameters_movie() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        doReturn(movie).when(movieDAO).getMovieById(anyLong());
        Movie result = service.viewMovie(anyLong());
        //then
        assertEquals(result, movie);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewUser_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).getUser(anyLong());
        service.viewUser(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewUser_validParameters_user() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyLong());
        User result = service.viewUser(anyLong());
        //then
        assertEquals(result, user);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editUser_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).editUser(anyLong(), anyString(), anyString(), anyString());
        service.editUser(1, "First", "Last", "email@gmail.com");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editUser_shortFirstName_ServiceException() throws ServiceException {
        //given
        //when
        service.editUser(1, "F", "Last", "email@gmail.com");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editUser_shortLastName_ServiceException() throws ServiceException {
        //given
        //when
        service.editUser(1, "First", "L", "email@gmail.com");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editUser_invalidEmail_ServiceException() throws ServiceException {
        //given
        //when
        service.editUser(1, "First", "Last", "email@gmail@com");
        //then
        //expecting ServiceException
    }

    @Test
    public void editUser_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).editUser(anyLong(), anyString(), anyString(), anyString());
        service.editUser(1, "First", "Last", "email@gmail.com");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).getUser(anyLong());
        service.changePassword(1, "currentP", "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_shortCurrentPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.changePassword(1, "short", "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_shortNewPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.changePassword(1, "currentP", "short", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_shortConfirmedPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.changePassword(1, "currentP", "newPassword", "short");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_passwordsDoNotMatch_ServiceException() throws ServiceException {
        //given
        //when
        service.changePassword(1, "currentP", "newPassword", "otherPassword");
        //then
        //expecting ServiceException
    }

    @Test
    public void changePassword_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyLong());
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        doReturn("encoded").when(keeper).generateHash(anyString(), anyString());
        doNothing().when(userDAO).changePassword(anyLong(), anyString());
        service.changePassword(1, "currentP", "newPassword", "newPassword");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).getUser(anyString(), anyString(), anyString(), anyString());
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_shortUsername_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("User", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_shortFirstName_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("Username", "F", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_shortLastName_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("Username", "First", "L", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_invalidEmail_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("Username", "First", "Last", "email@gmail=com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_shortNewPassword_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "new", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_passwordsDoNotMatch_ServiceException() throws ServiceException {
        //given
        //when
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "confirmed");
        //then
        //expecting ServiceException
    }

    @Test
    public void restorePassword_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString(), anyString(), anyString(), anyString());
        doReturn("encoded").when(keeper).generateHash(anyString(), anyString());
        doNothing().when(userDAO).changePassword(anyLong(), anyString());
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void recountAverageRating_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(feedbackDAO).getFeedbacksByAlienId(anyLong());
        service.recountAverageRating(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void recountAverageRating_validParameters_void() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByAlienId(anyLong());
        when(feedbacks.isEmpty()).thenReturn(true);
        doNothing().when(alienDAO).updateAverageRating(anyLong(), anyInt());
        service.recountAverageRating(anyLong());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteFeedback_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(feedbackDAO).deleteFeedback(anyLong());
        service.deleteFeedback(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteFeedback_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(feedbackDAO).deleteFeedback(anyLong());
        service.deleteFeedback(anyLong());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllAliensSorted_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAllAliensSorted(anyString(), anyString());
        service.viewAllAliensSorted("by", "type");
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllAliensSorted_validParameters_alienList() throws DAOException, ServiceException {
        //given
        List<Alien> aliens = (List<Alien>) mock(List.class);
        //when
        doReturn(aliens).when(alienDAO).getAllAliensSorted(anyString(), anyString());
        List<Alien> result = service.viewAllAliensSorted("by", "type");
        //then
        assertEquals(result, aliens);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void suggestEdit_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyLong());
        service.suggestEdit(1, 1, "text");
        //then
        //expecting ServiceException
    }

    @Test
    public void suggestEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        User user = mock(User.class);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyLong());
        doReturn(user).when(userDAO).getUser(anyLong());
        doNothing().when(editDAO).addNewEdit(any(Edit.class));
        service.suggestEdit(1, 1, "text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewNotifications_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(notificationDAO).getNotificationsByUserId(anyLong());
        service.viewNotifications(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewNotifications_validParameters_notificationList() throws DAOException, ServiceException {
        //given
        List<Notification> notifications = (List<Notification>) mock(List.class);
        //when
        doReturn(notifications).when(notificationDAO).getNotificationsByUserId(anyLong());
        List<Notification> result = service.viewNotifications(anyLong());
        //then
        assertEquals(result, notifications);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllMoviesSorted_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).getAllMoviesSorted(anyString(), anyString());
        service.viewAllMoviesSorted("by", "type");
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllMoviesSorted_validParameters_movieList() throws DAOException, ServiceException {
        //given
        List<Movie> movies = (List<Movie>) mock(List.class);
        //when
        doReturn(movies).when(movieDAO).getAllMoviesSorted(anyString(), anyString());
        List<Movie> result = service.viewAllMoviesSorted("by", "type");
        //then
        assertEquals(result, movies);
    }
}