package service.impl;

import dao.AlienDAO;
import dao.EditDAO;
import dao.MovieDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import dao.exception.DAOException;
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

import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class AlienSpecialistTest {

    private AlienSpecialist service = AlienSpecialist.getInstance();
    private UserDAO userDAO = mock(UserSQL.class);
    private AlienDAO alienDAO = mock(AlienSQL.class);
    private MovieDAO movieDAO = mock(MovieSQL.class);
    private EditDAO editDAO = mock(EditSQL.class);
    private NotificationDAO notificationDAO = mock(NotificationSQL.class);

    @BeforeClass
    public void init() {
        service.setUserDAO(userDAO);
        service.setAlienDAO(alienDAO);
        service.setMovieDAO(movieDAO);
        service.setEditDAO(editDAO);
        service.setNotificationDAO(notificationDAO);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void addAlien_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(movieDAO).getMovieByTitle(anyString());
        service.addAlien(1, "", "", "", "", "");
        //then
        //expecting ServiceException
    }

    @Test
    public void addAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doNothing().when(alienDAO).addNewAlien(any(Alien.class));
        service.addAlien(1, "", "", "", "", "");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void editAlien_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(movieDAO).getMovieByTitle(anyString());
        service.editAlien(1, "", "", "");
        //then
        //expecting ServiceException
    }

    @Test
    public void editAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        Movie movie = mock(Movie.class);
        //when
        doReturn(movie).when(movieDAO).getMovieByTitle(anyString());
        doNothing().when(alienDAO).editAlien(anyLong(), anyLong(), anyString(), anyString());
        service.editAlien(1, "", "", "");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllSuggestedEdits_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
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
    public void viewSuggestedEdit_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).getSuggestedEdit(anyLong());
        service.viewSuggestedEdit(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void viewSuggestedEdit_validParameters_edit() throws DAOException, ServiceException {
        //given
        Edit edit = mock(Edit.class);
        //when
        doReturn(edit).when(editDAO).getSuggestedEdit(anyLong());
        Edit result = service.viewSuggestedEdit(anyLong());
        //then
        assertEquals(result, edit);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void acceptEdit_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).getSuggestedEdit(anyLong());
        service.acceptEdit(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void acceptEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        Edit edit = mock(Edit.class);
        Alien alien = mock(Alien.class);
        //when
        doReturn(edit).when(editDAO).getSuggestedEdit(anyLong());
        doReturn(alien).when(edit).getAlien();
        doNothing().when(alienDAO).updateDescription(anyLong(), anyString());
        service.acceptEdit(anyLong());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteEdit_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(editDAO).deleteEdit(anyLong());
        service.deleteEdit(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteEdit_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(editDAO).deleteEdit(anyLong());
        service.deleteEdit(anyLong());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void sendNotification_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getUser(anyLong());
        service.sendNotification(1, "text");
        //then
        //expecting ServiceException
    }

    @Test
    public void sendNotification_validParameters_void() throws DAOException, ServiceException {
        //given
        User user = mock(User.class);
        //when
        doReturn(user).when(userDAO).getUser(anyLong());
        doNothing().when(notificationDAO).addNewNotification(any(Notification.class));
        service.sendNotification(1, "text");
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void sendNotificationToAll_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
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

    @Test(expectedExceptions = ServiceException.class)
    public void deleteAlien_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(alienDAO).deleteAlien(anyLong());
        service.deleteAlien(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(alienDAO).deleteAlien(anyLong());
        service.deleteAlien(anyLong());
        //then
    }
}