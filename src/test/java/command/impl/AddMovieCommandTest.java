package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.MovieFanService;
import service.exception.ServiceException;
import service.exception.movie.InvalidMovieInformationException;
import service.exception.movie.InvalidMovieTitleException;
import service.impl.MovieFan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AddMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromAddMovie_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        Command command = new AddMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("title")).thenReturn("Title");
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        doThrow(ServiceException.class).when(service).addMovie(anyString(), anyString(), anyString(), any());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_invalidMovieTitleExceptionFromAddMovie_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new AddMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("title")).thenReturn("Title");
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidMovieTitleException.class).when(service).addMovie(anyString(), anyString(), anyString(), any());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_invalidMovieInformationExceptionFromAddMovie_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        HttpSession session = mock(HttpSession.class);
        Command command = new AddMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("title")).thenReturn("Title");
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        when(mockRequest.getSession()).thenReturn(session);
        doThrow(InvalidMovieInformationException.class).when(service).addMovie(anyString(), anyString(), anyString(), any());
        String result = command.execute();
        //then
        assertEquals(result, "main");
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
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        doNothing().when(service).addMovie(anyString(), anyString(), anyString(), any());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}