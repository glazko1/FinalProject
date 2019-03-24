package command.impl;

import command.Command;
import command.exception.CommandException;
import org.testng.annotations.Test;
import service.CommonService;
import service.exception.ServiceException;
import service.impl.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class DeleteNotificationCommandTest {

    @Test(expectedExceptions = CommandException.class)
    public void execute_serviceExceptionFromDeleteNotification_CommandException() throws ServiceException, CommandException {
        //given
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        CommonService service = mock(Common.class);
        Command command = new DeleteNotificationCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("notificationId")).thenReturn("1");
        doThrow(ServiceException.class).when(service).deleteNotification(anyString());
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
        Command command = new DeleteNotificationCommand(service, mockRequest, mockResponse);
        //when
        when(mockRequest.getParameter("notificationId")).thenReturn("1");
        doNothing().when(service).deleteNotification(anyString());
        String result = command.execute();
        //then
        assertEquals(result, "main");
    }
}