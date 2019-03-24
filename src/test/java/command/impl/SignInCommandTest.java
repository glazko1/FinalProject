package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import entity.UserStatus;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.BannedUserException;
import service.exception.user.InvalidUserInformationException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SignInCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromSignIn_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new SignInCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("password")).thenReturn("Password");
        doThrow(ServiceException.class).when(service).signIn(anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_bannedUserExceptionFromSignIn_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        User user = mock(User.class);
        UserStatus userStatus = UserStatus.ADMIN;
        HttpSession session = mock(HttpSession.class);
        Command command = new SignInCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("password")).thenReturn("Password");
        when(mockRequest.getSession()).thenReturn(session);
        doReturn(user).when(service).signIn(anyString(), anyString());
        when(user.getStatus()).thenReturn(userStatus);
        doThrow(BannedUserException.class).when(service).signIn(anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }

    @Test
    public void execute_invalidUserInformationExceptionFromSignIn_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        User user = mock(User.class);
        UserStatus userStatus = UserStatus.ADMIN;
        HttpSession session = mock(HttpSession.class);
        Command command = new SignInCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("password")).thenReturn("Password");
        when(mockRequest.getSession()).thenReturn(session);
        doReturn(user).when(service).signIn(anyString(), anyString());
        when(user.getStatus()).thenReturn(userStatus);
        doThrow(InvalidUserInformationException.class).when(service).signIn(anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        User user = mock(User.class);
        UserStatus userStatus = UserStatus.ADMIN;
        HttpSession session = mock(HttpSession.class);
        Command command = new SignInCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("password")).thenReturn("Password");
        when(mockRequest.getSession()).thenReturn(session);
        when(service.signIn(anyString(), anyString())).thenReturn(user);
        when(user.getStatus()).thenReturn(userStatus);
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}