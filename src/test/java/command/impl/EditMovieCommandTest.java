package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.MovieFanService;
import service.exception.ServiceException;
import service.impl.MovieFan;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class EditMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new EditMovieCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        doThrow(ServiceException.class).when(service).editMovie(anyLong(), anyString(), anyString(), any());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_noAccess_main() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        MovieFanService service = mock(MovieFan.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new EditMovieCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(false);
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
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new EditMovieCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("runningTime")).thenReturn("100");
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        when(mockRequest.getParameter("releaseDate")).thenReturn("2000-01-01");
        when(mockRequest.getParameter("budget")).thenReturn("2000000");
        doNothing().when(service).editMovie(anyLong(), anyString(), anyString(), any());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}