package service.impl;

import dao.UserDAO;
import dao.exception.DAOException;
import dao.impl.UserSQL;
import entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.exception.ServiceException;

import java.util.List;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class AdminTest {

    private Admin service = Admin.getInstance();
    private UserDAO userDAO = mock(UserSQL.class);

    @BeforeClass
    public void init() {
        service.setUserDAO(userDAO);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void viewAllUsers_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
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
    public void changeBanStatus_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).changeBanStatus(anyLong());
        service.changeBanStatus(anyLong());
        //then
        //expecting ServiceException
    }

    @Test
    public void changeBanStatus_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).changeBanStatus(anyLong());
        service.changeBanStatus(anyLong());
        //then
    }

    @Test(expectedExceptions = ServiceException.class)
    public void changeUserStatus_exceptionFromDAO_ServiceException() throws DAOException, ServiceException {
        //given
        //when
        doThrow(DAOException.class).when(userDAO).changeUserStatus(anyLong(), anyInt());
        service.changeUserStatus(anyLong(), anyInt());
        //then
        //expecting ServiceException
    }

    @Test
    public void changeUserStatus_validParameters_void() throws DAOException, ServiceException {
        //given
        //when
        doNothing().when(userDAO).changeUserStatus(anyLong(), anyInt());
        service.changeUserStatus(anyLong(), anyInt());
        //then
    }
}