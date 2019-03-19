package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.AlienSpecialistService;
import service.exception.ServiceException;
import service.impl.AlienSpecialist;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class DeleteAlienCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new DeleteAlienCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).deleteAlien(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_noAccess_main() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AlienSpecialistService service = mock(AlienSpecialist.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new DeleteAlienCommand(service, mockRequest, mockResponse, checker);
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
        AlienSpecialistService service = mock(AlienSpecialist.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new DeleteAlienCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(mockRequest.getParameter("alienId")).thenReturn("1");
        doNothing().when(service).deleteAlien(anyLong());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}