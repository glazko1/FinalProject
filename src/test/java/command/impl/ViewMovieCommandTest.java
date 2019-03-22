package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ViewMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).viewMovie(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_moviePage() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Movie movie = mock(Movie.class);
        Command command = new ViewMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        when(service.viewMovie(anyString())).thenReturn(movie);
        String result = command.execute();
        //then
        assertEquals(result, "movie-page");
    }
}