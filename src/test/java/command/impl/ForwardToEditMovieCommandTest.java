package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Movie;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ForwardToEditMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ForwardToEditMovieCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        when(checker.checkStatus(any(), any())).thenReturn(true);
        doThrow(ServiceException.class).when(service).viewMovie(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Movie movie = mock(Movie.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ForwardToEditMovieCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(service.viewMovie(anyLong())).thenReturn(movie);
        String result = command.execute();
        //then
        assertEquals(result, "edit-movie");
    }
}