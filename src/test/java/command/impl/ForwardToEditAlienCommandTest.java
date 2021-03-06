package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import entity.Movie;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ForwardToEditAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromViewAlien_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ForwardToEditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).viewAlien(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromViewAllMovies_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Alien alien = mock(Alien.class);
        Command command = new ForwardToEditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doReturn(alien).when(service).viewAlien(anyString());
        doThrow(ServiceException.class).when(service).viewAllMovies();
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_editAlien() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Alien alien = mock(Alien.class);
        List<Movie> movies = (List<Movie>) mock(List.class);
        Command command = new ForwardToEditAlienCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(service.viewAlien(anyString())).thenReturn(alien);
        when(service.viewAllMovies()).thenReturn(movies);
        String result = command.execute();
        //then
        assertEquals(result, "edit-alien");
    }
}