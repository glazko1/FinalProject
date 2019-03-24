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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;
import service.exception.alien.InvalidAlienInformationException;
import service.exception.alien.InvalidAlienNameException;
import util.validator.AlienInformationValidator;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AlienSpecialistTest {

    private AlienSpecialist service = AlienSpecialist.getInstance();
    private AlienInformationValidator validator = mock(AlienInformationValidator.class);
    private UserDAO userDAO = mock(UserSQL.class);
    private AlienDAO alienDAO = mock(AlienSQL.class);
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private EditDAO editDAO = mock(EditSQL.class);
    private NotificationDAO notificationDAO = mock(NotificationSQL.class);

    @BeforeClass
    public void init() {
        service.setValidator(validator);
        service.setUserDAO(userDAO);
        service.setAlienDAO(alienDAO);
        service.setMovieDAO(movieDAO);
        service.setEditDAO(editDAO);
        service.setNotificationDAO(notificationDAO);
    }

    @Test(expectedExceptions = InvalidAlienInformationException.class)
    public void addAlien_invalidParameters_InvalidAlienInformationException() throws ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(false);
        service.addAlien("AlienName", "Planet",
                "Description", "Title", "Path");
        //then
        //expecting InvalidAlienInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addAlien_DAOExceptionFromGetMovieByTitle_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(movieDAO).getMovieByTitle(anyString());
        service.addAlien("AlienName", "Planet",
                "Description", "Title", "Path");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addAlien_DAOExceptionFromAddNewAlien_ServiceException() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doThrow(DAOException.class).when(alienDAO).addNewAlien(any(Alien.class));
        service.addAlien("AlienName", "Planet",
                "Description", "Title", "Path");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = InvalidAlienNameException.class)
    public void addAlien_UsedAlienNameExceptionFromAddNewAlien_InvalidAlienNameException() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doThrow(UsedAlienNameException.class).when(alienDAO).addNewAlien(any(Alien.class));
        service.addAlien("AlienName", "Planet",
                "Description", "Title", "Path");
        //then
        //expecting InvalidAlienNameException
    }

    @Test
    public void addAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        when(validator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doNothing().when(alienDAO).addNewAlien(any(Alien.class));
        service.addAlien("AlienName", "Planet", "Description", "Title", "Path");
        //then
    }

    @Test(expectedExceptions = InvalidAlienInformationException.class)
    public void editAlien_invalidParameters_InvalidAlienInformationException() throws ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(false);
        service.editAlien("1","MovieTitle", "Planet", "Description");
        //then
        //expecting InvalidAlienInformationException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editAlien_DAOExceptionFromGetMovieByTitle_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(true);
        doThrow(DAOException.class).when(movieDAO).getMovieByTitle(anyString());
        service.editAlien("1","MovieTitle", "Planet", "Description");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editAlien_DAOExceptionFromEditAlien_ServiceException() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(true);
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        when(movie.getMovieId()).thenReturn("1");
        doThrow(DAOException.class).when(alienDAO).editAlien(anyString(), anyString(), anyString(), anyString());
        service.editAlien("1","MovieTitle", "Planet", "Description");
        //then
        //expecting ServiceException
    }

    @Test
    public void editAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        when(validator.validate(anyString(), anyString())).thenReturn(true);
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doNothing().when(alienDAO).editAlien(anyString(), anyString(), anyString(), anyString());
        service.editAlien("1", "Title", "Planet", "Description");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllSuggestedEdits_DAOExceptionFromGetAllSuggestedEdits_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).getAllSuggestedEdits();
        service.viewAllSuggestedEdits();
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllSuggestedEdits_validParameters_editList() throws DAOException, ServiceException {
        //given
        List<Edit> edits = (List<Edit>) mock(List.class);
        //when
        doReturn(edits).when(editDAO).getAllSuggestedEdits();
        List<Edit> result = service.viewAllSuggestedEdits();
        //then
        assertEquals(result, edits);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewSuggestedEdit_DAOExceptionFromGetSuggestedEdit_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).getSuggestedEdit(anyString());
        service.viewSuggestedEdit(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewSuggestedEdit_validParameters_edit() throws DAOException, ServiceException {
        //given
        Edit edit = mock(Edit.class);
        //when
        doReturn(edit).when(editDAO).getSuggestedEdit(anyString());
        Edit result = service.viewSuggestedEdit(anyString());
        //then
        assertEquals(result, edit);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void acceptEdit_DAOExceptionFromGetSuggestedEdit_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).getSuggestedEdit(anyString());
        service.acceptEdit(anyString());
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void acceptEdit_DAOExceptionFromGetUpdateDescription_ServiceException() throws DAOException, ServiceException {
        //given
        Edit edit = mock(Edit.class);
        Alien alien = mock(Alien.class);
        //when
        doReturn(edit).when(editDAO).getSuggestedEdit(anyString());
        when(edit.getAlien()).thenReturn(alien);
        when(edit.getEditText()).thenReturn("Edit text");
        when(alien.getAlienId()).thenReturn("1");
        doThrow(DAOException.class).when(alienDAO).updateDescription(anyString(), anyString());
        service.acceptEdit(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void acceptEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        Edit edit = mock(Edit.class);
        Alien alien = mock(Alien.class);
        //when
        doReturn(edit).when(editDAO).getSuggestedEdit(anyString());
        when(edit.getAlien()).thenReturn(alien);
        doNothing().when(alienDAO).updateDescription(anyString(), anyString());
        service.acceptEdit(anyString());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteEdit_DAOExceptionFromDeleteEdit_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).deleteEdit(anyString());
        service.deleteEdit(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(editDAO).deleteEdit(anyString());
        service.deleteEdit(anyString());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void sendNotification_DAOExceptionFromGetUser_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getUser(anyString());
        service.sendNotification("1", "text");
        //then
        //expecting ServiceException
    }

    @Test(expectedExceptions = ServiceException.class)
    public void sendNotification_DAOExceptionFromAddNewNotification_ServiceException() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString());
        doThrow(DAOException.class).when(notificationDAO).addNewNotification(any(Notification.class));
        service.sendNotification("1", "text");
        //then
        //expecting ServiceException
    }

    @Test
    public void sendNotification_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyString());
        doNothing().when(notificationDAO).addNewNotification(any(Notification.class));
        service.sendNotification("1", "text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void sendNotificationToAll_DAOExceptionFromGetAllUsers_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getAllUsers();
        service.sendNotificationToAll("text");
        //then
        //expecting ServiceException
    }

    @Test
    public void sendNotificationToAll_validParameters_void() throws DAOException, ServiceException {
        //given
        List<User> users = (List<User>) mock(List.class);
        //when
        doReturn(users).when(userDAO).getAllUsers();
        doNothing().when(notificationDAO).addNewNotification(any(Notification.class));
        service.sendNotificationToAll("text");
        //then
    }
}