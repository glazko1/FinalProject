package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.InvalidEmailException;
import service.exception.user.InvalidUserInformationException;
import service.exception.user.InvalidUsernameException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SignUpCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromSignUp_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new SignUpCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("newUsername")).thenReturn("NewUsername");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("birthDate")).thenReturn("2000-01-01");
        doThrow(ServiceException.class).when(service).signUp(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyObject());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_invalidUsernameExceptionFromSignUp_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new SignUpCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("newUsername")).thenReturn("NewUsername");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("birthDate")).thenReturn("2000-01-01");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidUsernameException.class).when(service).signUp(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyObject());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }

    @Test
    public void execute_invalidEmailExceptionFromSignUp_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new SignUpCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("newUsername")).thenReturn("NewUsername");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("birthDate")).thenReturn("2000-01-01");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidEmailException.class).when(service).signUp(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyObject());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }

    @Test
    public void execute_invalidUserInformationExceptionFromSignUp_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new SignUpCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("newUsername")).thenReturn("NewUsername");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("birthDate")).thenReturn("2000-01-01");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidUserInformationException.class).when(service).signUp(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyObject());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }

    @Test
    public void execute_validParameters_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new SignUpCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("newUsername")).thenReturn("NewUsername");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("birthDate")).thenReturn("2000-01-01");
        doNothing().when(service).signUp(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyObject());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }
}