package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class RejectEditCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        Command command = new RejectEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("editId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).sendNotification(anyString(), anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        Command command = new RejectEditCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(mockRequest.getParameter("editId")).thenReturn("1");
        doNothing().when(service).sendNotification(anyString(), anyString());
        doNothing().when(service).deleteEdit(anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}