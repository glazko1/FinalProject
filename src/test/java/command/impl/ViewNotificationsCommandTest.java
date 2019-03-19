package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Notification;
import org.testng.Assert;
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

public class ViewNotificationsCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_exceptionFromService_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ViewNotificationsCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(checker.checkAccess(anyLong(), any())).thenReturn(true);
        doThrow(ServiceException.class).when(service).viewNotifications(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_noAccess_main() throws CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        Command command = new ViewNotificationsCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(checker.checkAccess(anyLong(), any())).thenReturn(false);
        String result = command.execute();
        //then
        Assert.assertEquals(result, "main");
    }

    @Test
    public void execute_validParameters_notifications() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        UserAccessChecker checker = mock(UserAccessChecker.class);
        List<Notification> notifications = (List<Notification>) mock(List.class);
        Command command = new ViewNotificationsCommand(service, mockRequest, mockResponse, checker);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(checker.checkAccess(anyLong(), any())).thenReturn(true);
        when(service.viewNotifications(anyLong())).thenReturn(notifications);
        String result = command.execute();
        //then
        Assert.assertEquals(result, "notifications");
    }
}