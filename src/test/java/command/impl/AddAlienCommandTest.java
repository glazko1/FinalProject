package command.impl;

import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AddAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialistService.class);
        AddAlienCommand command = new AddAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienName")).thenReturn("AlienName");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        doThrow(ServiceException.class).when(service).addAlien(anyString(), anyString(), anyString(), anyString());
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
        AddAlienCommand command = new AddAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienName")).thenReturn("AlienName");
        when(mockRequest.getParameter("planet")).thenReturn("Planet");
        when(mockRequest.getParameter("description")).thenReturn("Description");
        when(mockRequest.getParameter("movie")).thenReturn("Movie");
        doNothing().when(service).addAlien(anyString(), anyString(), anyString(), anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}