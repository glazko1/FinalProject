package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
import org.testng.annotations.Test;
import service.AdminService;
import service.exception.ServiceException;
import service.impl.Admin;
import util.checker.UserAccessChecker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ViewAllUsersCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service = mock(Admin.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ViewAllUsersCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        doThrow(ServiceException.class).when(service).viewAllUsers();
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_noAccess_main() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service = mock(Admin.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ViewAllUsersCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(false);
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }

    @Test
    public void execute_validParameters_userTable() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        AdminService service = mock(Admin.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        List<User> users = (List<User>) mock(List.class);
        Command command = new ViewAllUsersCommand(service, mockRequest, mockResponse, checker);
        //when
        when(checker.checkStatus(any(), any())).thenReturn(true);
        when(service.viewAllUsers()).thenReturn(users);
        String result = command.execute();
        //then
        assertEquals(result, "user-table");
    }
}