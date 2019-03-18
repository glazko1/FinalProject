package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Alien;
import entity.Movie;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ForwardToEditAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ForwardToEditAlienCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(checker.checkStatus(any(), any())).thenReturn(true);
        doThrow(ServiceException.class).when(service).viewAlien(anyLong());
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
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ForwardToEditAlienCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(service.viewAlien(anyLong())).thenReturn(alien);
        when(service.viewAllMovies()).thenReturn(movies);
        String result = command.execute();
        //then
        assertEquals(result, "edit-alien");
    }
}