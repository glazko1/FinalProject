package command.impl;

import command.Command;
import command.exception.CommandException;
import entity.Notification;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

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
        Command command = new ViewNotificationsCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).viewNotifications(anyLong());
        command.execute();
        //then
        //expecting CommandException
    }

    @Test
    public void execute_validParameters_notifications() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        List<Notification> notifications = (List<Notification>) mock(List.class);
        Command command = new ViewNotificationsCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("userId")).thenReturn("1");
        when(service.viewNotifications(anyLong())).thenReturn(notifications);
        String result = command.execute();
        //then
        Assert.assertEquals(result, "notifications");
    }
}