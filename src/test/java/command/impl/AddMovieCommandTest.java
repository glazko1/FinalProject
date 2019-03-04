package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.MovieFanService;
import service.exception.ServiceException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AddMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        Command command = new AddMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("title")).thenReturn("Title");
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        when(mockRequest.getParameter("year")).thenReturn("2000");
        when(mockRequest.getParameter("month")).thenReturn("1");
        when(mockRequest.getParameter("day")).thenReturn("1");
        doThrow(ServiceException.class).when(service).addMovie(anyString(), anyInt(), anyInt(), any());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        Command command = new AddMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("title")).thenReturn("Title");
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        when(mockRequest.getParameter("year")).thenReturn("2000");
        when(mockRequest.getParameter("month")).thenReturn("1");
        when(mockRequest.getParameter("day")).thenReturn("1");
        doNothing().when(service).addMovie(anyString(), anyInt(), anyInt(), any());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}