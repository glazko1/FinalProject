package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.exception.user.InvalidEmailException;
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

public class EditUserCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new EditUserCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        doThrow(ServiceException.class).when(service).editUser(anyString(), anyString(), anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_invalidEmailExceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new EditUserCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidEmailException.class).when(service).editUser(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_invalidUserInformationExceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new EditUserCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidUserInformationException.class).when(service).editUser(anyString(), anyString(), anyString(), anyString());
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
        Command command = new EditUserCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("firstName")).thenReturn("FirstName");
        when(mockRequest.getParameter("lastName")).thenReturn("LastName");
        when(mockRequest.getParameter("email")).thenReturn("Email");
        doNothing().when(service).editUser(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}