package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.User;
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

public class ForwardToChangePasswordCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ForwardToChangePasswordCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(checker.checkAccess(anyLong(), any())).thenReturn(true);
        when(mockRequest.getParameter("username")).thenReturn("Username");
        doThrow(ServiceException.class).when(service).viewUser(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_changePassword() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        User mockUser = mock(User.class);
        Command command = new ForwardToChangePasswordCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(checker.checkAccess(anyLong(), any())).thenReturn(true);
        when(mockRequest.getParameter("username")).thenReturn("Username");
        when(service.viewUser(anyLong())).thenReturn(mockUser);
        String result = command.execute();
        //then
        assertEquals(result, "change-password");
    }
}