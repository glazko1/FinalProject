package service.impl;

import dao.AlienDAO;
import dao.MovieDAO;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.AlienSQL;
import dao.impl.MovieSQL;
import dao.impl.UserSQL;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class AdminTest {

    private Admin service = Admin.getInstance();
    private UserDAO userDAO = mock(UserSQL.class);
    private AlienDAO alienDAO = mock(AlienSQL.class);
    private MovieDAO movieDAO = mock(MovieSQL.class);

    @BeforeClass
    public void init() {
        service.setUserDAO(userDAO);
        service.setAlienDAO(alienDAO);
        service.setMovieDAO(movieDAO);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllUsers_DAOExceptionFromViewAllUsers_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getAllUsers();
        service.viewAllUsers();
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllUsers_validParameters_userList() throws DAOException, ServiceException {
        //given
        List<User> users = (List<User>) mock(List.class);
        //when
        doReturn(users).when(userDAO).getAllUsers();
        List<User> result = service.viewAllUsers();
        //then
        assertEquals(result, users);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changeBanStatus_DAOExceptionFromChangeBanStatus_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).changeBanStatus(anyString());
        service.changeBanStatus(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void changeBanStatus_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).changeBanStatus(anyString());
        service.changeBanStatus(anyString());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changeUserStatus_DAOExceptionFromChangeUserStatus_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).changeUserStatus(anyString(), anyInt());
        service.changeUserStatus(anyString(), anyInt());
        //then
        //expecting ServiceException
    }

    @Test
    public void changeUserStatus_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).changeUserStatus(anyString(), anyInt());
        service.changeUserStatus(anyString(), anyInt());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllUsersSorted_DAOExceptionFromGetAllUsersSorted_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).getAllUsersSorted(anyString(), anyString());
        service.viewAllUsersSorted("SortedBy", "SortType");
        //then
        //expecting ServiceException
    }

    @Test
    public void viewAllUsersSorted_validParameters_userList() throws DAOException, ServiceException {
        //given
        List<User> users = (List<User>) mock(List.class);
        //when
        doReturn(users).when(userDAO).getAllUsersSorted(anyString(), anyString());
        List<User> result = service.viewAllUsersSorted("SortedBy", "SortType");
        //then
        assertEquals(result, users);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteAlien_DAOExceptionFromDeleteAlien_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(alienDAO).deleteAlien(anyString());
        service.deleteAlien(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteAlien_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(alienDAO).deleteAlien(anyString());
        service.deleteAlien(anyString());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void deleteMovie_DAOExceptionFromDeleteMovie_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(movieDAO).deleteMovie(anyString());
        service.deleteMovie(anyString());
        //then
        //expecting ServiceException
    }

    @Test
    public void deleteMovie_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(movieDAO).deleteMovie(anyString());
        service.deleteMovie(anyString());
        //then
    }
}