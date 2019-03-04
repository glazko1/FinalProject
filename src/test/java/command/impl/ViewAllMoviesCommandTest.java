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

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewAllMoviesCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new ViewAllMoviesCommand(service, mockRequest, mockResponse);
        //when
        doThrow(ServiceException.class).when(service).viewAllMovies();
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_movieTable() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        List<Movie> movies = (List<Movie>) mock(List.class);
        Command command = new ViewAllMoviesCommand(service, mockRequest, mockResponse);
        //when
        when(service.viewAllMovies()).thenReturn(movies);
        String result = command.execute();
        //then
        assertEquals(result, "movie-table");
    }
}