package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class RestorePasswordCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new RestorePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        doThrow(ServiceException.class).when(service).restorePassword(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_index() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new RestorePasswordCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(mockRequest.getParameter("newPassword")).thenReturn("NewPassword");
        when(mockRequest.getParameter("confirmedPassword")).thenReturn("ConfirmedPassword");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        doNothing().when(service).restorePassword(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "index");
    }
}