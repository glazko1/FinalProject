package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class DeleteMovieCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromDeleteMovie_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service = mock(Admin.class);
        Command command = new DeleteMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).deleteMovie(anyString());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_main() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service =  mock(Admin.class);
        Command command = new DeleteMovieCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("movieId")).thenReturn("1");
        doNothing().when(service).deleteMovie(anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}