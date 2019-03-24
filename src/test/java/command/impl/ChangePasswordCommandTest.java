package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.InvalidPasswordException;
import service.exception.user.InvalidUserInformationException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ChangePasswordCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromChangePassword_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ChangePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("currentPassword")).thenReturn("CurrentPassword");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        doThrow(ServiceException.class).when(service).changePassword(anyString(), anyString(), anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_invalidUserInformationExceptionFromChangePassword_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new ChangePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("currentPassword")).thenReturn("CurrentPassword");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidUserInformationException.class).when(service).changePassword(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_invalidPasswordExceptionFromChangePassword_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new ChangePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("currentPassword")).thenReturn("CurrentPassword");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidPasswordException.class).when(service).changePassword(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ChangePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("currentPassword")).thenReturn("CurrentPassword");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        doNothing().when(service).changePassword(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}