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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;
import service.exception.edit.InvalidEditInformationException;
import service.exception.feedback.InvalidFeedbackInformationException;
import service.exception.user.InvalidEmailException;
import service.exception.user.InvalidPasswordException;
import service.exception.user.InvalidUserInformationException;
import service.exception.user.InvalidUsernameException;
import util.hasher.PasswordHashKeeper;
import util.validator.EditInformationValidator;
import util.validator.FeedbackInformationValidator;
import util.validator.UserInformationValidator;

import java.sql.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CommonTest {

    private Common service = Common.getInstance();
    private UserInformationValidator userValidator = mock(UserInformationValidator.class);
    private FeedbackInformationValidator feedbackValidator = mock(FeedbackInformationValidator.class);
    private EditInformationValidator editValidator = mock(EditInformationValidator.class);
    private UserDAO userDAO = mock(UserSQL.class);
    private AlienDAO alienDAO = mock(AlienSQL.class);
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private FeedbackDAO feedbackDAO = mock(FeedbackSQL.class);
    private EditDAO editDAO = mock(EditSQL.class);
    private NotificationDAO notificationDAO = mock(NotificationSQL.class);
    private PasswordHashKeeper keeper = mock(PasswordHashKeeper.class);

    @BeforeClass
    public void init() {
        service.setUserValidator(userValidator);
        service.setFeedbackValidator(feedbackValidator);
        service.setEditValidator(editValidator);
        service.setUserDAO(userDAO);
        service.setAlienDAO(alienDAO);
        service.setMovieDAO(movieDAO);
        service.setFeedbackDAO(feedbackDAO);
        service.setEditDAO(editDAO);
        service.setNotificationDAO(notificationDAO);
        service.setKeeper(keeper);
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void signIn_invalidParameters_InvalidUserInformationException() throws ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString())).thenReturn(false);
        service.signIn("Invalid", "Info");
        //then
        //expecting InvalidUserInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(userDAO).getUser(anyString(), anyString());
        service.signIn("Username", "Password");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signIn_bannedUser_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validate(anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        when(user.isBanned()).thenReturn(true);
        service.signIn("Username", "Password");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void signIn_invalidUsernameOrPasswordExceptionFromGetUser_InvalidUserInformationException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString())).thenReturn(true);
        doThrow(InvalidUsernameOrPasswordException.class).when(userDAO).getUser(anyString(), anyString());
        service.signIn("Username", "Password");
        //then
        //expecting InvalidUserInformationException
    }

    @Test
    public void signIn_validParameters_user() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validate(anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        when(user.isBanned()).thenReturn(false);
        User result = service.signIn("Username", "Password");
        //then
        assertEquals(result, user);
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void signUp_invalidParameters_InvalidUserInformationException() throws ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        service.signUp("Invalid", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidUserInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void signUp_DAOExceptionFromAddNewUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidUsernameException.class)
    public void signUp_usedUsernameExceptionFromDAO_InvalidUsernameException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(UsedUsernameException.class).when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidUsernameException
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void signUp_usedEmailExceptionFromDAO_InvalidEmailException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(UsedEmailException.class).when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
        //expecting InvalidEmailException
    }

    @Test
    public void signUp_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doNothing().when(userDAO).addNewUser(any(User.class), anyString());
        service.signUp("Username", "First", "Last",
                "email@gmail.com", "Password", "Password",
                Date.valueOf("2000-01-01"));
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllAliens_DAOExceptionFromGetAllAliens_ServiceException() throws DAOException, ServiceException {
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
    public void viewAllMovies_DAOExceptionFromGetAllMovies_ServiceException() throws DAOException, ServiceException {
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
    public void viewAlien_DAOExceptionFromGetAlienById_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyString());
        service.viewAlien(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAlien_validParameters_alien() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        Alien result = service.viewAlien(anyString());
        //then
        assertEquals(result, alien);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAlienWithFeedbacks_DAOExceptionFromGetAlienById_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyString());
        service.viewAlienWithFeedbacks(anyString());
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAlienWithFeedbacks_DAOExceptionFromGeFeedbacksByAlienId_ServiceException() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doThrow(DAOException.class).when(feedbackDAO).getFeedbacksByAlienId(anyString());
        service.viewAlienWithFeedbacks(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAlienWithFeedbacks_validParameters_alienWithFeedbacks() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Pair<Alien, List<Feedback>> pair = new Pair<>(alien, feedbacks);
        //when
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByAlienId(anyString());
        Pair<Alien, List<Feedback>> result = service.viewAlienWithFeedbacks(anyString());
        //then
        assertEquals(result, pair);
    }

    @Test(expectedExceptions = InvalidFeedbackInformationException.class)
    public void addFeedback_invalidParameters_InvalidFeedbackInformationException() throws ServiceException {
        //given
        //when
        when(feedbackValidator.validate(anyString())).thenReturn(false);
        service.addFeedback("1", "1", 5, "Text");
        //then
        //expecting InvalidFeedbackInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addFeedback_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(feedbackValidator.validate(anyString())).thenReturn(true);
        doThrow(ServiceException.class).when(userDAO).getUser(anyString());
        service.addFeedback("1", "1", 5, "Text");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addFeedback_DAOExceptionFromGetAlienById_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(feedbackValidator.validate(anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        doThrow(DAOException.class).when(alienDAO).getAlienById(anyString());
        service.addFeedback("1", "1", 5, "Text");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addFeedback_DAOExceptionFromAddNewFeedback_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        Alien alien = mock(Alien.class);
        //when
        when(feedbackValidator.validate(anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doThrow(DAOException.class).when(feedbackDAO).addNewFeedback(any(Feedback.class));
        service.addFeedback("1", "1", 5, "Text");
        //then
        //expecting ServiceException
    }

    @Test
    public void addFeedback_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        Alien alien = mock(Alien.class);
        //when
        when(feedbackValidator.validate(anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doNothing().when(feedbackDAO).addNewFeedback(any(Feedback.class));
        service.addFeedback("1", "1", 5, "Text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewMovie_DAOExceptionFromGetMovieById_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(movieDAO).getMovieById(anyString());
        service.viewMovie(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewMovie_validParameters_movie() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        doReturn(movie).when(movieDAO).getMovieById(anyString());
        Movie result = service.viewMovie(anyString());
        //then
        assertEquals(result, movie);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewUser_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(userDAO).getUser(anyString());
        service.viewUser(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewUser_validParameters_user() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString());
        User result = service.viewUser(anyString());
        //then
        assertEquals(result, user);
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void editUser_invalidParameters_InvalidUserInformationException() throws ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString())).thenReturn(false);
        service.editUser("1", "First", "Last", "email@gmail.com");
        //then
        //expecting InvalidUserInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editUser_DAOExceptionFromEditUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(ServiceException.class).when(userDAO).editUser(anyString(), anyString(), anyString(), anyString());
        service.editUser("1", "First", "Last", "email@gmail.com");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void editUser_usedEmailExceptionFromEditUser_InvalidEmailException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(UsedEmailException.class).when(userDAO).editUser(anyString(), anyString(), anyString(), anyString());
        service.editUser("1", "First", "Last", "email@gmail.com");
        //then
        //expecting InvalidEmailException
    }

    @Test
    public void editUser_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doNothing().when(userDAO).editUser(anyString(), anyString(), anyString(), anyString());
        service.editUser("1", "First", "Last", "email@gmail.com");
        //then
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void changePassword_invalidParameters_InvalidUserInformationException() throws ServiceException {
        //given
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(false);
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
        //expecting InvalidUserInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_DAOExceptionFromGetUser1_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(ServiceException.class).when(userDAO).getUser(anyString());
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_DAOExceptionFromGetUser2_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        when(user.getUsername()).thenReturn("Username");
        when(keeper.generateHash(anyString(), anyString())).thenReturn("hash");
        doThrow(DAOException.class).when(userDAO).getUser(anyString(), anyString());
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changePassword_DAOExceptionFromChangePassword_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        when(user.getUsername()).thenReturn("Username");
        when(keeper.generateHash(anyString(), anyString())).thenReturn("hash");
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        doThrow(DAOException.class).when(userDAO).changePassword(anyString(), anyString());
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidPasswordException.class)
    public void changePassword_invalidUsernameOrPasswordExceptionFromGetUser_InvalidPasswordException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(InvalidUsernameOrPasswordException.class).when(userDAO).getUser(anyString());
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
        //expecting InvalidPasswordException
    }

    @Test
    public void changePassword_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validatePasswords(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString());
        doReturn(user).when(userDAO).getUser(anyString(), anyString());
        doReturn("encoded").when(keeper).generateHash(anyString(), anyString());
        doNothing().when(userDAO).changePassword(anyString(), anyString());
        service.changePassword("1", "currentP", "newPassword", "newPassword");
        //then
    }

    @Test(expectedExceptions = InvalidUserInformationException.class)
    public void restorePassword_invalidParameters_InvalidUserInformationException() throws ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting InvalidUserInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(ServiceException.class).when(userDAO).getUser(anyString(), anyString(), anyString(), anyString());
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void restorePassword_DAOExceptionFromChangePassword_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString(), anyString(), anyString(), anyString());
        when(user.getUserId()).thenReturn("1");
        doThrow(DAOException.class).when(userDAO).changePassword(anyString(), anyString());
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
        //expecting ServiceException
    }

    @Test
    public void restorePassword_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        when(userValidator.validate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(user).when(userDAO).getUser(anyString(), anyString(), anyString(), anyString());
        doReturn("encoded").when(keeper).generateHash(anyString(), anyString());
        doNothing().when(userDAO).changePassword(anyString(), anyString());
        service.restorePassword("Username", "First", "Last", "email@gmail.com",
                "newPassword", "newPassword");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void recountAverageRating_DAOExceptionFromGetFeedbacksByAlienId_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(feedbackDAO).getFeedbacksByAlienId(anyString());
        service.recountAverageRating(anyString());
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void recountAverageRating_DAOExceptionFromUpdateAverageRating_ServiceException() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Stream<Feedback> stream = (Stream<Feedback>) mock(Stream.class);
        IntStream intStream = mock(IntStream.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByAlienId(anyString());
        when(feedbacks.stream()).thenReturn(stream);
        when(stream.mapToInt(any())).thenReturn(intStream);
        when(intStream.sum()).thenReturn(0);
        when(feedbacks.isEmpty()).thenReturn(true);
        doThrow(DAOException.class).when(alienDAO).updateAverageRating(anyString(), anyDouble());
        service.recountAverageRating(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void recountAverageRating_validParameters_void() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Stream<Feedback> stream = (Stream<Feedback>) mock(Stream.class);
        IntStream intStream = mock(IntStream.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByAlienId(anyString());
        when(feedbacks.stream()).thenReturn(stream);
        when(stream.mapToInt(any())).thenReturn(intStream);
        when(intStream.sum()).thenReturn(20);
        when(feedbacks.isEmpty()).thenReturn(false);
        when(feedbacks.size()).thenReturn(5);
        doNothing().when(alienDAO).updateAverageRating(anyString(), anyDouble());
        service.recountAverageRating("1");
        //then
        verify(alienDAO).updateAverageRating("1", 4.0);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteFeedback_DAOExceptionFromDeleteFeedback_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(feedbackDAO).deleteFeedback(anyString());
        service.deleteFeedback(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteFeedback_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(feedbackDAO).deleteFeedback(anyString());
        service.deleteFeedback(anyString());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllAliensSorted_DAOExceptionFromGetAllAliensSorted_ServiceException() throws DAOException, ServiceException {
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

    @Test(expectedExceptions = InvalidEditInformationException.class)
    public void suggestEdit_invalidParameters_InvalidEditInformationException() throws ServiceException {
        //given
        //when
        when(editValidator.validate(anyString())).thenReturn(false);
        service.suggestEdit("1", "1", "text");
        //then
        //expecting InvalidEditInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void suggestEdit_DAOExceptionFromGetAlienById_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(editValidator.validate(anyString())).thenReturn(true);
        doThrow(ServiceException.class).when(alienDAO).getAlienById(anyString());
        service.suggestEdit("1", "1", "text");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void suggestEdit_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        //when
        when(editValidator.validate(anyString())).thenReturn(true);
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doThrow(DAOException.class).when(userDAO).getUser(anyString());
        service.suggestEdit("1", "1", "text");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void suggestEdit_DAOExceptionFromAddNewEdit_ServiceException() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        User user = mock(User.class);
        //when
        when(editValidator.validate(anyString())).thenReturn(true);
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doReturn(user).when(userDAO).getUser(anyString());
        doThrow(DAOException.class).when(editDAO).addNewEdit(any(Edit.class));
        service.suggestEdit("1", "1", "text");
        //then
        //expecting ServiceException
    }

    @Test
    public void suggestEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        Alien alien = mock(Alien.class);
        User user = mock(User.class);
        //when
        when(editValidator.validate(anyString())).thenReturn(true);
        doReturn(alien).when(alienDAO).getAlienById(anyString());
        doReturn(user).when(userDAO).getUser(anyString());
        doNothing().when(editDAO).addNewEdit(any(Edit.class));
        service.suggestEdit("1", "1", "text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewNotifications_DAOExceptionFromGetNotificationsByUserId_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(ServiceException.class).when(notificationDAO).getNotificationsByUserId(anyString());
        service.viewNotifications(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewNotifications_validParameters_notificationList() throws DAOException, ServiceException {
        //given
        List<Notification> notifications = (List<Notification>) mock(List.class);
        //when
        doReturn(notifications).when(notificationDAO).getNotificationsByUserId(anyString());
        List<Notification> result = service.viewNotifications(anyString());
        //then
        assertEquals(result, notifications);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllMoviesSorted_DAOExceptionFromGetAllMoviesSorted_ServiceException() throws DAOException, ServiceException {
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

    @Test(expectedExceptions = ServiceException.class)
    public void reviewUserStatus_DAOExceptionFromGetFeedbacksByUserId_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(feedbackDAO).getFeedbacksByUserId(anyString());
        service.reviewUserStatus("1");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void reviewUserStatus_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Stream<Feedback> stream = (Stream<Feedback>) mock(Stream.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByUserId(anyString());
        when(feedbacks.stream()).thenReturn(stream);
        when(stream.filter(any())).thenReturn(stream);
        doThrow(DAOException.class).when(userDAO).getUser(anyString());
        service.reviewUserStatus("1");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void reviewUserStatus_DAOExceptionFromChangeUserStatus_ServiceException() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Stream<Feedback> stream = (Stream<Feedback>) mock(Stream.class);
        User user = mock(User.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByUserId(anyString());
        when(feedbacks.size()).thenReturn(10);
        when(feedbacks.stream()).thenReturn(stream);
        when(stream.filter(any())).thenReturn(stream);
        when(stream.count()).thenReturn(0L);
        doReturn(user).when(userDAO).getUser(anyString());
        when(user.getStatus()).thenReturn(UserStatus.USER);
        doThrow(DAOException.class).when(userDAO).changeUserStatus(anyString(), anyInt());
        service.reviewUserStatus("1");
        //then
        //expecting ServiceException
    }

    @Test
    public void reviewUserStatus_validParameters_statusDown() throws DAOException, ServiceException {
        //given
        List<Feedback> feedbacks = (List<Feedback>) mock(List.class);
        Stream<Feedback> stream = (Stream<Feedback>) mock(Stream.class);
        User user = mock(User.class);
        //when
        doReturn(feedbacks).when(feedbackDAO).getFeedbacksByUserId(anyString());
        when(feedbacks.size()).thenReturn(10);
        when(feedbacks.stream()).thenReturn(stream);
        when(stream.filter(any())).thenReturn(stream);
        when(stream.count()).thenReturn(3L);
        doReturn(user).when(userDAO).getUser(anyString());
        when(user.getStatus()).thenReturn(UserStatus.ALIEN_SPECIALIST);
        doNothing().when(userDAO).changeUserStatus(anyString(), anyInt());
        service.reviewUserStatus("1");
        //then
        verify(userDAO).changeUserStatus("1", 4);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteNotification_DAOExceptionFromDeleteNotification_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(notificationDAO).deleteNotification(anyString());
        service.deleteNotification("1");
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteNotification_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(notificationDAO).deleteNotification(anyString());
        service.deleteNotification("1");
        //then
    }
}